package com.aube.mysize.presentation.ui.screens.add_size.input_form

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.OuterSize
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.BrandChipInput
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.ui.component.SelectableChipGroup
import com.aube.mysize.presentation.ui.component.SizeOcrSelector
import com.aube.mysize.presentation.viewmodel.OuterSizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun OuterSizeInputForm(
    viewModel: OuterSizeViewModel,
    snackbarHostState: SnackbarHostState,
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
    val isFormValid = isRequiredValid && isShoulderValid && isChestValid && isSleeveValid && isLengthValid

    val typeBorderColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val typeLabelColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
    val brandBorderColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val brandLabelColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        BorderColumn("* 아우터 종류", typeBorderColor, typeLabelColor) {
            val outerTypes = listOf("환절기 코트", "겨울 코트", "롱 패딩", "숏 패딩", "패딩 베스트",
                "카디건", "폴리스", "후드 집업", "블루종", "무스탕", "퍼 재킷", "아노락 재킷",
                "트레이닝 재킷", "사파리 재킷", "스타디움 재킷", "레더 재킷", "트러커 재킷", "블레이저 재킷", "기타 아우터")
            SelectableChipGroup(
                options = outerTypes,
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
                onAddBrand = { viewModel.insertBrand(it, "아우터") }
            )
        }

        LabeledTextField(sizeLabel, { sizeLabel = it }, "* 사이즈 라벨 (예: S, M, L / 90, 95, 100)",
            modifier = Modifier.focusRequester(focusRequester),
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )

        Spacer(Modifier.height(8.dp))

        SizeOcrSelector(
            keyList = listOf(
                "어깨", "가슴", "소매길이", "총장", // 한글
                "SHOULDER", "CHEST", "SLEEVE", "LENGTH"  // 영어
            ),
            keyMapping = ::normalizeOuterKey,
            initialSizeLabel = sizeLabel.uppercase(),
            snackbarHostState = snackbarHostState,
            onExtracted = { extractedSizeMap  ->
                val sizeMap = extractedSizeMap
                val selectedSize = sizeLabel.uppercase()
                sizeLabel = selectedSize

                if (sizeMap[selectedSize] != null) {
                    sizeMap[selectedSize]?.let { values ->
                        length = values["LENGTH"] ?: ""
                        shoulder = values["SHOULDER"] ?: ""
                        chest = values["CHEST"] ?: ""
                        sleeve = values["SLEEVE"] ?: ""
                    }
                } else {
                    sizeLabel = ""
                    shoulder = ""
                    chest = ""
                    sleeve = ""
                    length = ""
                }
            },
            onLabelSelected = { extractedSizeMap, selectedExtractedLabel ->
                if (!selectedExtractedLabel.contains("알 수 없는 사이즈")) {
                    sizeLabel = selectedExtractedLabel
                } else {
                    focusRequester.requestFocus()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("정확한 사이즈 라벨이 기입되었는지 확인해주세요.")
                    }
                }
                extractedSizeMap[selectedExtractedLabel]?.let {
                    shoulder = it["SHOULDER"] ?: ""
                    chest = it["CHEST"] ?: ""
                    sleeve = it["SLEEVE"] ?: ""
                    length = it["LENGTH"] ?: ""
                }
            }
        )

        LabeledTextField(shoulder, { shoulder = it }, "어깨 너비 (cm)", isError = shoulderError)
        LabeledTextField(chest, { chest = it }, "가슴 단면 (cm)", isError = chestError)
        LabeledTextField(sleeve, { sleeve = it }, "소매 길이 (cm)", isError = sleeveError)
        LabeledTextField(length, { length = it }, "총장 (cm)", isError = lengthError)

        Spacer(Modifier.height(8.dp))

        BorderColumn("핏") {
            val fits = listOf("슬림핏", "레귤러핏", "오버핏")
            SelectableChipGroup(
                options = fits,
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
                        OuterSize(
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

private fun normalizeOuterKey(original: String): String {
    val upper = original.uppercase()

    return when {
        "SHOULDER" in upper || "어깨" in original -> "SHOULDER"
        "CHEST" in upper || "BUST" in upper || "가슴" in original -> "CHEST"
        "SLEEVE" in upper || "소매길이" in original -> "SLEEVE"
        "LENGTH" in upper || "총장" in original -> "LENGTH"
        else -> upper
    }
}
