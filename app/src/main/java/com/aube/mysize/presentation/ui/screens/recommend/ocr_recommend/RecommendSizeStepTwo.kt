package com.aube.mysize.presentation.ui.screens.recommend.ocr_recommend

import SizeExtractionResult
import SizeOcrManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.aube.mysize.presentation.model.RecommendedSizeResult
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.utils.bottomKeys
import com.aube.mysize.utils.findBestMatchedLabel
import com.aube.mysize.utils.normalizeBottomKey
import com.aube.mysize.utils.normalizeOnePieceKey
import com.aube.mysize.utils.normalizeOuterKey
import com.aube.mysize.utils.normalizeShoeKey
import com.aube.mysize.utils.normalizeTopKey
import com.aube.mysize.utils.onePieceKeys
import com.aube.mysize.utils.outerKeys
import com.aube.mysize.utils.shoeKeys
import com.aube.mysize.utils.topKeys
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch

@Composable
fun RecommendSizeStepTwo(
    selectedStep: Int?,
    snackbarHostState: SnackbarHostState,
    selectedCategory: SizeCategory?,
    recommendedResult: List<RecommendedSizeResult.Success>,
    backHandler: () -> Unit,
) {
    val keyList = when(selectedCategory) {
        SizeCategory.TOP -> topKeys
        SizeCategory.BOTTOM -> bottomKeys
        SizeCategory.OUTER -> outerKeys
        SizeCategory.ONE_PIECE -> onePieceKeys
        SizeCategory.SHOE -> shoeKeys
        else -> emptyList()
    }

    val keyMapping = when(selectedCategory) {
        SizeCategory.TOP -> ::normalizeTopKey
        SizeCategory.BOTTOM -> ::normalizeBottomKey
        SizeCategory.OUTER -> ::normalizeOuterKey
        SizeCategory.ONE_PIECE -> ::normalizeOnePieceKey
        SizeCategory.SHOE -> ::normalizeShoeKey
        else -> { _ -> "" }
    }
    var hasLaunchedGallery by rememberSaveable { mutableStateOf(false) }

    var extractedImageUri by remember { mutableStateOf<Uri?>(null) }
    var extractedSizeMap by remember { mutableStateOf<Map<String, Map<String, String>>>(emptyMap()) }
    var extractedLabelList by remember { mutableStateOf<List<String>>(emptyList()) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val cropLauncher = rememberLauncherForActivityResult(
        CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            result.uriContent?.let { croppedUri ->
                val image = InputImage.fromFilePath(context, croppedUri)
                extractedImageUri = croppedUri

                SizeOcrManager(keyList, keyMapping).recognize(image) { ocrResult ->
                    when (ocrResult) {
                        is SizeExtractionResult.Success -> {
                            extractedLabelList = ocrResult.sizeLabels
                            extractedSizeMap = ocrResult.sizeMap
                        }
                        else -> {
                            Log.e("TopSizeInputFrom", "OCR 실패: ${result::class.simpleName}")
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("사이즈 추출 실패. 다시 시도하거나 수동으로 입력해주세요.")
                            }
                        }
                    }
                }
            }
        } else {
            Log.e("TopSizeInputFrom", "Crop 실패: ${result.error}")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            cropLauncher.launch(
                CropImageContractOptions(
                    uri = it,
                    cropImageOptions = CropImageOptions().apply {
                        fixAspectRatio = false
                    }
                )
            )
        }
    }

    BackHandler {
        backHandler()
    }

    LaunchedEffect(selectedStep) {
        if (selectedStep == 2 && !hasLaunchedGallery) {
            galleryLauncher.launch("image/*")
            hasLaunchedGallery = true
        }
    }

    val bestLabels = recommendedResult.filter { it.category == selectedCategory }.map { result ->
        val bestLabel = result.typeToSizeMap.mapNotNull { (type, sizeDetail) ->
            val best = findBestMatchedLabel(sizeDetail.measurements, extractedSizeMap)
            best?.let {
                Triple(result.category.label, type, it)
            }
        }
        bestLabel
    }.flatten()

    bestLabels.forEach { (category, type, label) ->
        Text("$category - $type: $label 추천됨")
    }
}