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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.OuterSize
import com.aube.mysize.presentation.ui.component.BrandChipInput
import com.aube.mysize.presentation.ui.component.InputBorderColumn
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.ui.component.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.OuterSizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun OuterSizeInputForm(
    viewModel: OuterSizeViewModel,
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

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

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

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        InputBorderColumn(typeBorderColor) {
            Text(
                text = "* 아우터 종류",
                style = MaterialTheme.typography.labelMedium,
                color = typeLabelColor,
            )
            Spacer(Modifier.height(16.dp))
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

        InputBorderColumn(brandBorderColor) {
            Text(
                text = "* 브랜드",
                style = MaterialTheme.typography.labelMedium,
                color = typeLabelColor,
            )
            Spacer(Modifier.height(16.dp))
            BrandChipInput(
                brandList = brandList + "기타 브랜드",
                selectedBrand = brand,
                onSelect = { brand = it },
                onDelete = { viewModel.deleteBrand(it) },
                onAddBrand = { viewModel.insertBrand(it, "아우터") }
            )
        }

        LabeledTextField(sizeLabel, { sizeLabel = it }, "* 사이즈 라벨 (예: S, M, L / 90, 95, 100)",
            isError = sizeLabelError, keyboardType = KeyboardType.Text)
        LabeledTextField(shoulder, { shoulder = it }, "어깨 너비 (cm)", isError = shoulderError)
        LabeledTextField(chest, { chest = it }, "가슴 단면 (cm)", isError = chestError)
        LabeledTextField(sleeve, { sleeve = it }, "소매 길이 (cm)", isError = sleeveError)
        LabeledTextField(length, { length = it }, "총장 (cm)", isError = lengthError)

        Spacer(Modifier.height(8.dp))
        InputBorderColumn(MaterialTheme.colorScheme.outline) {
            Text("핏", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(16.dp))
            val fits = listOf("슬림핏", "레귤러핏", "오버핏")
            SelectableChipGroup(
                options = fits,
                selectedOption = fit,
                onSelect = { fit = it }
            )
        }

        LabeledTextField(note, { note = it }, "참고 사항", keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done) {
            coroutineScope.launch {
                delay(100)
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            if (!isRequiredValid) {
                Text(
                    text = "종류, 브랜드, 사이즈 라벨은 필수 입력입니다.",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            if(!isFormValid && isRequiredValid) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.error,
                    text = "사이즈는 숫자로 입력해주세요.",
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }
            Spacer(Modifier.width(16.dp))
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

