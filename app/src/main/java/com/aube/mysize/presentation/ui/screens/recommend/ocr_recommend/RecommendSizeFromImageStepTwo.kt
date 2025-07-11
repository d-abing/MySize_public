package com.aube.mysize.presentation.ui.screens.recommend.ocr_recommend

import SizeExtractionResult
import SizeOcrManager
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.constants.AnimationConstants
import com.aube.mysize.presentation.constants.bottomKeys
import com.aube.mysize.presentation.constants.normalizeBottomKey
import com.aube.mysize.presentation.constants.normalizeOnePieceKey
import com.aube.mysize.presentation.constants.normalizeOuterKey
import com.aube.mysize.presentation.constants.normalizeShoeKey
import com.aube.mysize.presentation.constants.normalizeTopKey
import com.aube.mysize.presentation.constants.onePieceKeys
import com.aube.mysize.presentation.constants.outerKeys
import com.aube.mysize.presentation.constants.shoeKeys
import com.aube.mysize.presentation.constants.topKeys
import com.aube.mysize.presentation.model.recommend.RecommendedSizeResult
import com.aube.mysize.presentation.model.size.SizeContentUiModel
import com.aube.mysize.presentation.model.size.toUi
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.presentation.ui.component.ocr.PreviewImage
import com.aube.mysize.presentation.ui.component.ocr.SizeLabelOcrManager
import com.aube.mysize.presentation.ui.component.ocr.SizeOcrButton
import com.aube.mysize.presentation.ui.screens.my_size.component.SubListBlock
import com.aube.mysize.presentation.ui.screens.recommend.component.EmptyShoeSize
import com.aube.mysize.utils.recommend.findBestMatchedLabel
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch
import timber.log.Timber

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

    var uri by remember { mutableStateOf<Uri?>(null) }

    var extractedImageUri by remember { mutableStateOf<Uri?>(null) }
    var extractedSizeMap by remember { mutableStateOf<Map<String, Map<String, String>>>(emptyMap()) }
    var extractedLabelList by remember { mutableStateOf<List<String>>(emptyList()) }
    var bestLabelsMap by remember{ mutableStateOf<Map<String, List<SizeContentUiModel>>>(emptyMap()) }

    var pendingFullImage by remember { mutableStateOf<InputImage?>(null) }
    var labelOcrCompleted by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val cropLauncher2 = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            result.uriContent?.let { labelUri ->
                val labelImage = InputImage.fromFilePath(context, labelUri)

                SizeLabelOcrManager(context).recognize(
                    image = labelImage,
                    onResult = { labels ->
                        extractedLabelList = labels
                        labelOcrCompleted = true
                    },
                    onFailure = { errorMessage ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(errorMessage)
                        }
                    }
                )
            }
        } else {
            Timber.tag("OCR").e("라벨 크롭 실패: ${result.error}")
        }
    }

    val cropLauncher1 = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            result.uriContent?.let { croppedUri ->
                uri = croppedUri
                val fullImage = InputImage.fromFilePath(context, croppedUri)
                pendingFullImage = fullImage
                extractedImageUri = croppedUri
                labelOcrCompleted = false
                extractedLabelList = emptyList()

                cropLauncher2.launch(
                    CropImageContractOptions(
                        uri = croppedUri,
                        cropImageOptions = CropImageOptions().apply {
                            fixAspectRatio = false
                        }
                    )
                )
            }
        } else {
            Timber.tag("OCR").e("전체 크롭 실패: ${result.error}")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            cropLauncher1.launch(
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
                galleryLauncher.launch(context.getString(R.string.image_mime_type))
                hasLaunchedGallery = true
            }
        }
    }

    LaunchedEffect(labelOcrCompleted) {
        if (labelOcrCompleted && extractedLabelList.isNotEmpty() && pendingFullImage != null) {
            SizeOcrManager(keyList, keyMapping, extractedLabelList)
                .recognizeWithRetry(pendingFullImage!!) { ocrResult ->
                    when (ocrResult) {
                        is SizeExtractionResult.Success -> {
                            extractedSizeMap = ocrResult.sizeMap
                        }
                        else -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(context.getString(R.string.error_ocr_extract_failed))
                            }
                        }
                    }
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
                                name = AnimationConstants.STAR_EFFECT_ANIMATION,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )
                            Text(
                                text = stringResource(R.string.text_best_fitting_size),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Animation(
                                name = AnimationConstants.BOTTOM_ARROW_ANIMATION,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                                    .height(50.dp)
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(1)
                                        }
                                    }
                            )
                        }
                    }

                    item {
                        uri?.let {
                            PreviewImage(uri)
                        }
                    }

                    bestLabelsMap =
                        successResult
                            .filter { it.category == selectedCategory }
                            .groupBy { it.category.toUi().label }
                            .mapValues { (_, results) ->
                                results.flatMap { result ->
                                    result.typeToSizeMap.mapNotNull { (type, sizeDetail) ->
                                        val best = findBestMatchedLabel(sizeDetail.measurements, extractedSizeMap)
                                        best?.let { sizeLabel ->
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
                                    bestLabelsMap.forEach { (_, contents) ->
                                        SubListBlock(
                                            contents = contents,
                                            maxColumnCount = 4
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    SizeOcrButton(
                        text = stringResource(R.string.action_retry_recommendation),
                    ) {
                        galleryLauncher.launch(context.getString(R.string.image_mime_type))
                    }
                }
            }
        }
    }
}