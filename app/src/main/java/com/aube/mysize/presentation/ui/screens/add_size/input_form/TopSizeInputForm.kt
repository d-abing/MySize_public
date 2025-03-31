package com.aube.mysize.presentation.ui.screens.add_size.input_form

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.TopSize
import com.aube.mysize.presentation.ui.component.BrandChipInput
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.ui.component.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.TopSizeViewModel
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.aube.mysize.utils.recognizeSizeFromImage
import java.time.LocalDate

@Composable
fun TopSizeInputForm(
    viewModel: TopSizeViewModel,
    onSaved: () -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    val brandList by viewModel.brandList.collectAsState()
    var sizeLabel by remember { mutableStateOf("") }
    var shoulder by remember { mutableStateOf("") }
    var chest by remember { mutableStateOf("") }
    var sleeve by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val shoulderFloat = shoulder.toFloatOrNull()
    val chestFloat = chest.toFloatOrNull()
    val sleeveFloat = sleeve.toFloatOrNull()
    val lengthFloat = length.toFloatOrNull()

    var typeError by remember { mutableStateOf(false) }
    var brandError by remember { mutableStateOf(false) }
    var sizeLabelError by remember { mutableStateOf(false) }
    var shoulderError by remember { mutableStateOf(false) }
    var chestError by remember { mutableStateOf(false) }
    var sleeveError by remember { mutableStateOf(false) }
    var lengthError by remember { mutableStateOf(false) }

    val isTypeValid = type.isNotBlank()
    val isBrandValid = brand.isNotBlank()
    val isSizeLabelValid = sizeLabel.isNotBlank()
    val isShoulderValid = shoulder.isBlank() || shoulderFloat != null
    val isChestValid = chest.isBlank() || chestFloat != null
    val isSleeveValid = sleeve.isBlank() || sleeveFloat != null
    val isLengthValid = length.isBlank() || lengthFloat != null

    typeError = !isTypeValid
    brandError = !isBrandValid
    sizeLabelError = !isSizeLabelValid
    shoulderError = !isShoulderValid
    chestError = !isChestValid
    sleeveError = !isSleeveValid
    lengthError = !isLengthValid

    val isRequiredValid = isTypeValid && isBrandValid && isSizeLabelValid
    val isFormValid = isRequiredValid && isShoulderValid && isChestValid && isLengthValid && isSleeveValid

    val typeBorderColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val typeLabelColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
    val brandBorderColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val brandLabelColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val cropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { result ->
            if (result.isSuccessful) {
                Log.e("TopSizeInputFrom", "Success")
                result.uriContent?.let { croppedUri ->
                    val image = InputImage.fromFilePath(context, croppedUri)
                    recognizeSizeFromImage(
                        image = image,
                        sizeLabel = sizeLabel,
                        keyList = listOf("총장", "어깨너비", "가슴단면", "소매길이")
                    ) { parsed ->
                        length = parsed["총장"] ?: ""
                        shoulder = parsed["어깨너비"] ?: ""
                        chest = parsed["가슴단면"] ?: ""
                        sleeve = parsed["소매길이"] ?: ""
                    }
                }
            } else {
                Log.e("TopSizeInputFrom", "Error: ${result.error}")
            }
        }
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            cropLauncher.launch(
                CropImageContractOptions(
                    uri,
                    CropImageOptions().apply {
                        // 필요한 옵션만 설정
                        fixAspectRatio = false
                    }
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        BorderColumn("* 상의 종류", typeBorderColor, typeLabelColor) {
            val topTypes = listOf(
                "맨투맨", "니트", "후드티", "셔츠/블라우스", "베스트/뷔스티에",
                "긴소매 티", "반소매 티", "카라 티", "민소매 티", "기타 상의"
            )
            SelectableChipGroup(
                options = topTypes,
                selectedOption = type,
                onSelect = { type = it }
            )
        }

        Spacer(Modifier.height(8.dp))

        BorderColumn("* 브랜드", brandBorderColor, brandLabelColor) {
            BrandChipInput(
                brandList = brandList + "기타 브랜드",
                selectedBrand = brand,
                onSelect = { brand = it },
                onDelete = { viewModel.deleteBrand(it) },
                onAddBrand = { viewModel.insertBrand(it) }
            )
        }

        LabeledTextField(sizeLabel, { sizeLabel = it }, "* 사이즈 라벨 (예: S, M, L / 90, 95, 100)",
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )

        Spacer(Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            shape = MaterialTheme.shapes.small,
            enabled = isSizeLabelValid,
            onClick = { galleryLauncher.launch("image/*") }
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.Straighten,
                        modifier = Modifier
                            .rotate(45f),
                        contentDescription = "자동 추출",
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("상세 사이즈 캡쳐화면으로 자동 입력하기")
                }
                if(sizeLabelError) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text =  "사이즈 라벨을 정확히 입력해 주세요.",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }
            }
        }

        LabeledTextField(shoulder, { shoulder = it }, "어깨 너비 (cm)", isError = shoulderError)
        LabeledTextField(chest, { chest = it }, "가슴 단면 (cm)", isError = chestError)
        LabeledTextField(sleeve, { sleeve = it }, "소매 길이 (cm)", isError = sleeveError)
        LabeledTextField(length, { length = it }, "총장 (cm)", isError = lengthError)

        Spacer(Modifier.height(8.dp))

        BorderColumn("핏") {
            val topFits = listOf("슬림핏", "레귤러핏", "오버핏")
            SelectableChipGroup(
                options = topFits,
                selectedOption = fit,
                onSelect = { fit = it }
            )
        }

        LabeledTextField(
            value = note,
            onValueChange = { note = it },
            label = "참고 사항",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            onDone = {
                coroutineScope.launch {
                    delay(100)
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            }
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            SaveButton(
                enabled = isFormValid,
                onClick = {
                    viewModel.insert(
                        TopSize(
                            type = type,
                            brand = brand,
                            sizeLabel = sizeLabel,
                            shoulder = shoulderFloat,
                            chest = chestFloat,
                            sleeve = sleeveFloat,
                            length = lengthFloat,
                            fit = fit.ifBlank { null },
                            note = note.ifBlank { null },
                            date = LocalDate.now()
                        )
                    )
                    onSaved()
                }
            )
        }
    }
}