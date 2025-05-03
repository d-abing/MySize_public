package com.aube.mysize.presentation.ui.component

import SizeExtractionResult
import SizeOcrManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.component.guide.GuideButton
import com.aube.mysize.presentation.ui.component.guide.GuideDialog
import com.aube.mysize.presentation.ui.component.ocr.PreviewImage
import com.aube.mysize.presentation.ui.component.ocr.SizeLabelChip
import com.aube.mysize.presentation.ui.component.ocr.SizeOcrButton
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
                    uri = it,
                    cropImageOptions = CropImageOptions().apply {
                        fixAspectRatio = false
                    }
                )
            )
        }
    }

    Column {
        GuideButton(text = "캡쳐화면 가이드 보기", onClick = { showGuideDialog = true })

        SizeOcrButton(onClick = { galleryLauncher.launch("image/*") })

        PreviewImage(extractedImageUri)

        SizeLabelChip(extractedSizeMap, extractedLabelList, selectedExtractedLabel) { label ->
            selectedExtractedLabel = label
            onLabelSelected(extractedSizeMap, selectedExtractedLabel)
        }

        /* TODO 나중에 삭제할 것 */
        Text(
            text = extractedSizeMap.toString()
        )
    }

    if (showGuideDialog) {
        GuideDialog(
            onDismiss = { showGuideDialog = false },
            title = "사이즈 표 자르기 가이드",
            content = {
                Text(
                    text = "표 영역만 자르면 사이즈 추출 정확도가 올라갑니다!😎\n" +
                            "제목이나 단위, 부가설명 등은 제외해주세요. 🙅‍♂️🙅",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 12.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.size_guide),
                    contentDescription = "사이즈표 크롭 가이드",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}
