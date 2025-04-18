package com.aube.mysize.presentation.ui.screens.add_size.input_form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.ShoeSize
import com.aube.mysize.presentation.ui.component.SizeOcrSelector
import com.aube.mysize.presentation.ui.component.addsize.BorderColumn
import com.aube.mysize.presentation.ui.component.addsize.BrandChipInput
import com.aube.mysize.presentation.ui.component.addsize.LabeledTextField
import com.aube.mysize.presentation.ui.component.addsize.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import java.time.LocalDate

@Composable
fun ShoeSizeInputForm(
    viewModel: ShoeSizeViewModel,
    snackbarHostState: SnackbarHostState,
    onUpdateFormState: (isMandatoryFieldsFilled: Boolean, isAllFieldsValid: Boolean) -> Unit,
    onSaved: (ShoeSize) -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    val brandList by viewModel.brandList.collectAsState()
    var sizeLabel by remember { mutableStateOf("") }
    var footLength by remember { mutableStateOf("") }
    var footWidth by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

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

    val typeBorderColor = if (typeError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val typeBackgroundColor = if (typeError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent
    val brandBorderColor = if (brandError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val brandBackgroundColor = if (brandError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(type, brand, sizeLabel, footLength, footWidth) {
        onUpdateFormState(isRequiredValid, isFormValid)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(WindowInsets.ime.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        BorderColumn("* 신발 종류", typeBorderColor, typeBackgroundColor) {
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

        BorderColumn("* 브랜드", brandBorderColor, brandBackgroundColor) {
            BrandChipInput(
                brandList = brandList + "기타 브랜드",
                selectedBrand = brand,
                onSelect = { brand = it },
                onDelete = { viewModel.deleteBrand(it) },
                onAddBrand = { viewModel.insertBrand(it, "신발") }
            )
        }

        LabeledTextField(
            value = sizeLabel,
            onValueChange = {
                sizeLabel = it
            },
            label = "* 사이즈 라벨 (예: 250, 255)",
            modifier = Modifier.focusRequester(focusRequester),
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )

        Spacer(Modifier.height(8.dp))

        SizeOcrSelector(
            keyList = listOf(
                "길이", "너비", // 한글
                "LENGTH", "WIDTH" // 영어
            ),
            keyMapping = ::normalizeShoeKey,
            initialSizeLabel = sizeLabel.uppercase(),
            snackbarHostState = snackbarHostState,
            onExtracted = { extractedSizeMap  ->
                val sizeMap = extractedSizeMap
                val selectedSize = sizeLabel.uppercase()
                sizeLabel = selectedSize

                if (sizeMap[selectedSize] != null) {
                    sizeMap[selectedSize]?.let { values ->
                        footLength = values["FOOT LENGTH"] ?: ""
                        footWidth = values["FOOT WIDTH"] ?: ""
                    }
                } else {
                    sizeLabel = ""
                    footLength = ""
                    footWidth = ""
                }
            },
            onFailed = {
                sizeLabel = ""
                footLength = ""
                footWidth = ""
            },
            onLabelSelected = { extractedSizeMap, selectedExtractedLabel ->
                if (!selectedExtractedLabel.contains("알 수 없는 사이즈")) {
                    sizeLabel = selectedExtractedLabel
                } else {
                    focusRequester.requestFocus()
                }
                extractedSizeMap[selectedExtractedLabel]?.let {
                    footLength = it["FOOT LENGTH"] ?: ""
                    footWidth = it["FOOT WIDTH"] ?: ""
                }
            }
        )

        LabeledTextField(footLength, { footLength = it }, "발 길이 (cm)", isError = footLengthError)
        LabeledTextField(footWidth, { footWidth = it }, "발볼 너비 (cm)", isError = widthError)

        Spacer(Modifier.height(8.dp))

        BorderColumn("핏") {
            val fits = listOf("작음", "딱 맞음", "큼")
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
        )

        val currentShoeSize = ShoeSize(
            type = type,
            brand = brand,
            sizeLabel = sizeLabel,
            footLength = footLengthFloat,
            footWidth = widthFloat,
            fit = fit.ifBlank { null },
            note = note.ifBlank { null },
            date = LocalDate.now()
        )

        onSaved(currentShoeSize)
    }
}

private fun normalizeShoeKey(original: String): String {
    val upper = original.uppercase()

    return when {
        "WIDTH" in upper || "너비" in original || "발볼" in original -> "FOOT WIDTH"
        "LENGTH" in upper || "길이" in original -> "FOOT LENGTH"
        else -> upper
    }
}

