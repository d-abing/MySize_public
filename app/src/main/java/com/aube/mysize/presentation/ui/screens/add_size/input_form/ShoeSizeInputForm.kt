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
import com.aube.mysize.domain.model.ShoeSize
import com.aube.mysize.presentation.ui.component.BrandChipInput
import com.aube.mysize.presentation.ui.component.InputBorderColumn
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.ui.component.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.ShoeSizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun ShoeSizeInputForm(
    viewModel: ShoeSizeViewModel,
    onSaved: () -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    val brandList by viewModel.brandList.collectAsState()
    var sizeLabel by remember { mutableStateOf("") }
    var footLength by remember { mutableStateOf("") }
    var footWidth by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val footLengthFloat = footLength.toFloatOrNull()
    val widthFloat = footWidth.toFloatOrNull()

    var typeError by remember { mutableStateOf(false) }
    var brandError by remember { mutableStateOf(false) }
    var sizeLabelError by remember { mutableStateOf(false) }
    var footLengthError by remember { mutableStateOf(false) }
    var widthError by remember { mutableStateOf(false) }

    val isTypeValid = type.isNotBlank()
    val isBrandValid = brand.isNotBlank()
    val isSizeLabelValid = sizeLabel.isNotBlank()
    val isFootLengthValid = footLength.isBlank() || footLengthFloat != null
    val isWidthValid = footWidth.isBlank() || widthFloat != null

    typeError = !isTypeValid
    brandError = !isBrandValid
    sizeLabelError = !isSizeLabelValid
    footLengthError = !isFootLengthValid
    widthError = !isWidthValid

    val isRequiredValid = isTypeValid && isBrandValid && isSizeLabelValid
    val isFormValid = isRequiredValid && isFootLengthValid && isWidthValid

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
                text = "* 신발 종류",
                style = MaterialTheme.typography.labelMedium,
                color = typeLabelColor
            )
            Spacer(Modifier.height(16.dp))
            val shoeTypes = listOf(
                "운동화", "구두", "부츠", "슬리퍼", "샌들", "로퍼", "플랫슈즈", "기타"
            )
            SelectableChipGroup(
                options = shoeTypes,
                selectedOption = type,
                onSelect = { type = it }
            )
        }

        Spacer(Modifier.height(8.dp))

        InputBorderColumn(brandBorderColor) {
            Text(
                text = "* 브랜드",
                style = MaterialTheme.typography.labelMedium,
                color = brandLabelColor,
            )
            Spacer(Modifier.height(16.dp))
            BrandChipInput(
                brandList = brandList + "기타 브랜드",
                selectedBrand = brand,
                onSelect = { brand = it },
                onDelete = { viewModel.deleteBrand(it) },
                onAddBrand = { viewModel.insertBrand(it, "신발") }
            )
        }

        LabeledTextField(sizeLabel, { sizeLabel = it }, "* 사이즈 라벨 (예: 250, 255)", isError = sizeLabelError)
        LabeledTextField(footLength, { footLength = it }, "발 길이 (cm)", isError = footLengthError)
        LabeledTextField(footWidth, { footWidth = it }, "발볼 너비 (cm)", isError = widthError)

        Spacer(Modifier.height(8.dp))
        InputBorderColumn(MaterialTheme.colorScheme.outline) {
            Text("핏", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(16.dp))
            val fits = listOf("작음", "딱 맞음", "큼")
            SelectableChipGroup(
                options = fits,
                selectedOption = fit,
                onSelect = { fit = it }
            )
        }

        LabeledTextField(note, { note = it }, "참고 사항", keyboardType = KeyboardType.Text, imeAction = ImeAction.Done) {
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
            if (!isFormValid && isRequiredValid) {
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
                        ShoeSize(
                            type = type,
                            brand = brand,
                            sizeLabel = sizeLabel,
                            footLength = footLengthFloat,
                            footWidth = widthFloat,
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

