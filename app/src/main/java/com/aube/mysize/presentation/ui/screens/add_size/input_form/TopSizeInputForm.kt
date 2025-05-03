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
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.presentation.ui.component.SizeOcrSelector
import com.aube.mysize.presentation.ui.screens.add_size.component.BorderColumn
import com.aube.mysize.presentation.ui.screens.add_size.component.BrandChipInput
import com.aube.mysize.presentation.ui.screens.add_size.component.LabeledTextField
import com.aube.mysize.presentation.ui.screens.add_size.component.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel
import com.aube.mysize.utils.normalizeTopKey
import com.aube.mysize.utils.topFits
import com.aube.mysize.utils.topKeys
import com.aube.mysize.utils.topTypes
import java.time.LocalDate

@Composable
fun TopSizeInputForm(
    oldSizeId: Int?,
    viewModel: TopSizeViewModel,
    snackbarHostState: SnackbarHostState,
    onUpdateFormState: (isMandatoryFieldsFilled: Boolean, isAllFieldsValid: Boolean) -> Unit,
    onSaved: (TopSize) -> Unit
) {
    val brandList by viewModel.brandList.collectAsState()

    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var sizeLabel by remember { mutableStateOf("") }
    var shoulder by remember { mutableStateOf("") }
    var chest by remember { mutableStateOf("") }
    var sleeve by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    LaunchedEffect(oldSizeId) {
        val oldSize = oldSizeId?.let { viewModel.getSizeById(it) }

        oldSize?.let { size ->
            type = size.type
            brand = size.brand
            sizeLabel = size.sizeLabel
            shoulder = size.shoulder?.toString() ?: ""
            chest = size.chest?.toString() ?: ""
            sleeve = size.sleeve?.toString() ?: ""
            length = size.length?.toString() ?: ""
            fit = size.fit ?: ""
            note = size.note ?: ""
        }
    }

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
    val isFormValid =
        isRequiredValid && isShoulderValid && isChestValid && isLengthValid && isSleeveValid

    val typeBorderColor =
        if (typeError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val typeBackgroundColor =
        if (typeError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent
    val brandBorderColor =
        if (brandError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val brandBackgroundColor =
        if (brandError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(type, brand, sizeLabel, shoulder, chest, sleeve, length) {
        onUpdateFormState(isRequiredValid, isFormValid)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(WindowInsets.ime.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        BorderColumn("* 상의 종류", typeBorderColor, typeBackgroundColor) {
            SelectableChipGroup(
                options = topTypes,
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
                onAddBrand = { viewModel.insertBrand(it) }
            )
        }

        LabeledTextField(
            value = sizeLabel,
            onValueChange = {
                sizeLabel = it
            },
            label = "* 사이즈 라벨 (예: S, M, L / 90, 95, 100)",
            modifier = Modifier.focusRequester(focusRequester),
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )

        Spacer(Modifier.height(8.dp))

        SizeOcrSelector(
            keyList = topKeys,
            keyMapping = ::normalizeTopKey,
            initialSizeLabel = sizeLabel.uppercase(),
            snackbarHostState = snackbarHostState,
            onExtracted = { extractedSizeMap ->
                val sizeMap = extractedSizeMap
                val selectedSize = sizeLabel.uppercase()
                sizeLabel = selectedSize

                if (sizeMap[selectedSize] != null) {
                    sizeMap[selectedSize]?.let { values ->
                        shoulder = values["SHOULDER"] ?: ""
                        chest = values["CHEST"] ?: ""
                        sleeve = values["SLEEVE"] ?: ""
                        length = values["LENGTH"] ?: ""
                    }
                } else {
                    sizeLabel = ""
                    shoulder = ""
                    chest = ""
                    sleeve = ""
                    length = ""
                }
            },
            onFailed = {
                sizeLabel = ""
                shoulder = ""
                chest = ""
                sleeve = ""
                length = ""
            },
            onLabelSelected = { extractedSizeMap, selectedExtractedLabel ->
                if (!selectedExtractedLabel.contains("알 수 없는 사이즈")) {
                    sizeLabel = selectedExtractedLabel
                } else {
                    focusRequester.requestFocus()
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
            SelectableChipGroup(
                options = topFits,
                selectedOption = fit,
                onSelect = { fit = it }
            )
        }

        LabeledTextField(
            value = note,
            onValueChange = { note = it },
            label = "메모",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        )

        val currentTopSize = TopSize(
            type = type,
            brand = brand,
            sizeLabel = sizeLabel,
            shoulder = shoulderFloat,
            chest = chestFloat,
            sleeve = sleeveFloat,
            length = lengthFloat,
            fit = fit.ifBlank { null },
            note = note.ifEmpty { null },
            date = LocalDate.now()
        )

        onSaved(currentTopSize)
    }
}