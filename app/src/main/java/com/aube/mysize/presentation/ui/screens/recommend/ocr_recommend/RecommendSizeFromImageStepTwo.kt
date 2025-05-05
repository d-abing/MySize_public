package com.aube.mysize.presentation.ui.screens.recommend.ocr_recommend

import SizeExtractionResult
import SizeOcrManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.RecommendedSizeResult
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.model.SizeContentUiModel
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.presentation.ui.component.ocr.SizeOcrButton
import com.aube.mysize.presentation.ui.screens.my_size.component.SubListBlock
import com.aube.mysize.presentation.ui.screens.recommend.component.EmptyShoeSize
import com.aube.mysize.utils.recommend.findBestMatchedLabel
import com.aube.mysize.utils.size.bottomKeys
import com.aube.mysize.utils.size.normalizeBottomKey
import com.aube.mysize.utils.size.normalizeOnePieceKey
import com.aube.mysize.utils.size.normalizeOuterKey
import com.aube.mysize.utils.size.normalizeShoeKey
import com.aube.mysize.utils.size.normalizeTopKey
import com.aube.mysize.utils.size.onePieceKeys
import com.aube.mysize.utils.size.outerKeys
import com.aube.mysize.utils.size.shoeKeys
import com.aube.mysize.utils.size.topKeys
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch

@Composable
fun RecommendSizeFromImageStepTwo(
    selectedStep: Int?,
    snackbarHostState: SnackbarHostState,
    selectedCategory: SizeCategory?,
    recommendedResult: List<RecommendedSizeResult>,
    backHandler: () -> Unit,
    onEditBodySize: () -> Unit,
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

    val failureResult = recommendedResult.find { it is RecommendedSizeResult.Failure } as? RecommendedSizeResult.Failure
    val successResult = recommendedResult.filterIsInstance<RecommendedSizeResult.Success>()

    var hasLaunchedGallery by rememberSaveable { mutableStateOf(false) }

    var extractedImageUri by remember { mutableStateOf<Uri?>(null) }
    var extractedSizeMap by remember { mutableStateOf<Map<String, Map<String, String>>>(emptyMap()) }
    var extractedLabelList by remember { mutableStateOf<List<String>>(emptyList()) }
    var bestLabelsMap by remember{ mutableStateOf<Map<String, List<SizeContentUiModel>>>(emptyMap()) }

    var isExtractionSuccessful by remember { mutableStateOf(true) }

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
                            isExtractionSuccessful = true
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

    LaunchedEffect(Unit) {
        if (selectedStep == 2 && !hasLaunchedGallery) {
            val shouldLaunch = selectedCategory != SizeCategory.SHOE || failureResult == null
            if (shouldLaunch) {
                galleryLauncher.launch("image/*")
                hasLaunchedGallery = true
            }
        }
    }

    val listState = rememberLazyListState()

    if (selectedCategory == SizeCategory.SHOE && failureResult != null) {
        EmptyShoeSize(failureResult, onEditBodySize)
    } else {
        if (hasLaunchedGallery) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                state = listState
            ) {
                if (extractedSizeMap.isNotEmpty()){
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            Animation(
                                name = "star_effect.json",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )
                            Text(
                                text = "사용자님께 가장 잘 어울리는 사이즈에요!",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Animation(
                                name = "bottom_arrow.json",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                                    .height(50.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(1)
                                        }
                                    }
                            )
                        }
                    }

                    bestLabelsMap =
                        successResult
                            .filter { it.category == selectedCategory }
                            .groupBy { it.category.label }
                            .mapValues { (_, results) ->
                                results.flatMap { result ->
                                    result.typeToSizeMap.mapNotNull { (type, sizeDetail) ->
                                        val best = findBestMatchedLabel(sizeDetail.measurements, extractedSizeMap)
                                        best?.let { sizeLabel ->
                                            if (sizeLabel.contains("임시")) {
                                                isExtractionSuccessful = false
                                            }

                                            SizeContentUiModel(
                                                title = type,
                                                sizeLabel = sizeLabel,
                                                onClick = {}
                                            )
                                        }
                                    }
                                }
                            }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiary.copy(0.1f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                if (extractedSizeMap.isNotEmpty()) {
                                    if (isExtractionSuccessful) {
                                        bestLabelsMap.forEach { (category, contents) ->
                                            SubListBlock(
                                                typeName = category,
                                                contents = contents,
                                                maxColumnCount = 4
                                            )
                                        }
                                    } else {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "죄송합니다. 사이즈 추출에 실패했어요 😥",
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    SizeOcrButton(
                        text = "다시 추천받기"
                    ) {
                        galleryLauncher.launch("image/*")
                    }
                }
            }
        }
    }
}