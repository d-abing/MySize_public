package com.aube.mysize.presentation.ui.component

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.aube.mysize.presentation.ui.component.ocr.SizeChipInput
import com.aube.mysize.presentation.ui.component.ocr.SizeOcrButton
import com.aube.mysize.presentation.ui.component.ocr.SizeOcrGuide
import com.aube.mysize.presentation.ui.component.ocr.SizeOcrGuideDialog
import com.aube.mysize.utils.SizeExtractionResult
import com.aube.mysize.utils.SizeOcrManager
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch

@Composable
fun SizeOcrSelector(
    keyList: List<String>,
    keyMapping: (String) -> String,
    initialSizeLabel: String,
    snackbarHostState: SnackbarHostState,
    onExtracted: (Map<String, Map<String, String>>) -> Unit,
    onFailed: () -> Unit,
    onLabelSelected: (Map<String, Map<String, String>>, String) -> Unit
) {
    var showGuideDialog by remember { mutableStateOf(false) }

    var extractedImageUri by remember { mutableStateOf<Uri?>(null) }
    var extractedSizeMap by remember { mutableStateOf<Map<String, Map<String, String>>>(emptyMap()) }
    var extractedLabelList by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedExtractedLabel by remember { mutableStateOf(initialSizeLabel) }

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
                            onExtracted(ocrResult.sizeMap)
                        }
                        else -> {
                            Log.e("TopSizeInputFrom", "OCR 실패: ${result::class.simpleName}")
                            onFailed()
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
                    it,
                    CropImageOptions().apply { fixAspectRatio = false }
                )
            )
        }
    }

    Column {
        SizeOcrGuide(onClick = { showGuideDialog = true })

        SizeOcrButton(onClick = { galleryLauncher.launch("image/*") })

        PreviewImage(extractedImageUri)

        SizeChipInput(extractedSizeMap, extractedLabelList, selectedExtractedLabel) { label ->
            selectedExtractedLabel = label
            onLabelSelected(extractedSizeMap, selectedExtractedLabel)
        }
    }

    if (showGuideDialog) {
        SizeOcrGuideDialog(onDismiss = { showGuideDialog = false })
    }

}
