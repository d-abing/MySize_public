package com.aube.mysize.presentation.ui.component

import SizeExtractionResult
import SizeOcrManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.component.button.MSGuideButton
import com.aube.mysize.presentation.ui.component.dialog.CropGuideDialog
import com.aube.mysize.presentation.ui.component.ocr.PreviewImage
import com.aube.mysize.presentation.ui.component.ocr.SizeLabelChip
import com.aube.mysize.presentation.ui.component.ocr.SizeLabelOcrManager
import com.aube.mysize.presentation.ui.component.ocr.SizeOcrButton
import com.aube.mysize.ui.theme.MySizeTheme
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun SizeOcrSelector(
    keyList: List<String>,
    keyMapping: (String) -> String,
    initialSizeLabel: String,
    snackbarHostState: SnackbarHostState,
    onExtracted: (Map<String, Map<String, String>>) -> Unit,
    onFailed: () -> Unit,
    onLabelSelected: (Map<String, Map<String, String>>, String) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var showGuideDialog by remember { mutableStateOf(false) }

    var extractedImageUri by remember { mutableStateOf<Uri?>(null) }
    var extractedSizeMap by remember { mutableStateOf<Map<String, Map<String, String>>>(emptyMap()) }
    var extractedLabelList by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedLabel by remember { mutableStateOf(initialSizeLabel) }

    var pendingFullImage by remember { mutableStateOf<InputImage?>(null) }
    var labelOcrCompleted by remember { mutableStateOf(false) }
    var mapOcrCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(labelOcrCompleted) {
        if (labelOcrCompleted && extractedLabelList.isNotEmpty() && pendingFullImage != null) {
            pendingFullImage?.let {
                SizeOcrManager(keyList, keyMapping, extractedLabelList)
                    .recognize(it) { ocrResult ->
                        when (ocrResult) {
                            is SizeExtractionResult.Success -> {
                                mapOcrCompleted = true
                                extractedSizeMap = ocrResult.sizeMap
                                onExtracted(extractedSizeMap)
                            }
                            else -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(context.getString(R.string.ocr_extraction_failed))
                                }
                                onFailed()
                            }
                        }
                    }
            }
        }
    }

    val sizeLabelCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            result.uriContent?.let { sizeLabelUri ->
                SizeLabelOcrManager(context).recognize(
                    image = InputImage.fromFilePath(context, sizeLabelUri),
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

    val sizeTableCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        mapOcrCompleted = false
        labelOcrCompleted = false
        extractedLabelList = emptyList()

        if (result.isSuccessful) {
            result.uriContent?.let { sizeTableUri ->
                pendingFullImage = InputImage.fromFilePath(context, sizeTableUri)
                extractedImageUri = sizeTableUri

                sizeLabelCropLauncher.launch(
                    CropImageContractOptions(
                        uri = sizeTableUri,
                        cropImageOptions = CropImageOptions()
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
        if (uri != null) {
            sizeTableCropLauncher.launch(
                CropImageContractOptions(
                    uri = uri,
                    cropImageOptions = CropImageOptions()
                )
            )
        } else {
            Timber.tag("OCR").w("갤러리에서 이미지 선택이 취소되었거나 실패했습니다.")
        }
    }

    Column {
        MSGuideButton(text = stringResource(R.string.ocr_capture_guide_view), onClick = { showGuideDialog = true })

        SizeOcrButton(
            text = stringResource(R.string.ocr_auto_fill_from_capture),
            onClick = { galleryLauncher.launch(context.getString(R.string.image_mime_type)) }
        )

        PreviewImage(extractedImageUri)

        SizeLabelChip(mapOcrCompleted, extractedLabelList, selectedLabel) { label ->
            selectedLabel = label
            onLabelSelected(extractedSizeMap, selectedLabel)
        }
    }

    if (showGuideDialog) {
        CropGuideDialog { showGuideDialog = false }
    }
}

@Preview(showBackground = true)
@Composable
fun SizeOcrSelectorPreview() {
    MySizeTheme {
        SizeOcrSelector(
            keyList = listOf("총장", "어깨", "가슴", "소매"),
            keyMapping = { it },
            initialSizeLabel = "L",
            snackbarHostState = remember { SnackbarHostState() },
            onExtracted = {},
            onFailed = {},
            onLabelSelected = { _, _ -> }
        )
    }
}