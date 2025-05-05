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
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.presentation.ui.component.SizeOcrSelector
import com.aube.mysize.presentation.ui.screens.add_size.component.BorderColumn
import com.aube.mysize.presentation.ui.screens.add_size.component.BrandChipInput
import com.aube.mysize.presentation.ui.screens.add_size.component.LabeledTextField
import com.aube.mysize.presentation.ui.screens.add_size.component.SingleSelectableChipGroup
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.utils.size.normalizeOnePieceKey
import com.aube.mysize.utils.size.onePieceFits
import com.aube.mysize.utils.size.onePieceKeys
import com.aube.mysize.utils.size.onePieceTypes
import java.time.LocalDate

@Composable
fun OnePieceSizeInputForm(
    oldSizeId: Int?,
    viewModel: OnePieceSizeViewModel,
    snackbarHostState: SnackbarHostState,
    onUpdateFormState: (isMandatoryFieldsFilled: Boolean, isAllFieldsValid: Boolean) -> Unit,
    onSaved: (OnePieceSize) -> Unit
) {
    val brandList by viewModel.brandList.collectAsState()

    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var sizeLabel by remember { mutableStateOf("") }
    var shoulder by remember { mutableStateOf("") }
    var chest by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var hip by remember { mutableStateOf("") }
    var sleeve by remember { mutableStateOf("") }
    var rise by remember { mutableStateOf("") }
    var thigh by remember { mutableStateOf("") }
    var hem by remember { mutableStateOf("") }
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
            waist = size.waist?.toString() ?: ""
            hip = size.hip?.toString() ?: ""
            sleeve = size.sleeve?.toString() ?: ""
            rise = size.rise?.toString() ?: ""
            thigh = size.thigh?.toString() ?: ""
            hem = size.hem?.toString() ?: ""
            length = size.length?.toString() ?: ""
            fit = size.fit ?: ""
            note = size.note ?: ""
        }
    }

    val shoulderFloat = shoulder.toFloatOrNull()
    val chestFloat = chest.toFloatOrNull()
    val waistFloat = waist.toFloatOrNull()
    val hipFloat = hip.toFloatOrNull()
    val sleeveFloat = sleeve.toFloatOrNull()
    val riseFloat = rise.toFloatOrNull()
    val thighFloat = thigh.toFloatOrNull()
    val hemFloat = hem.toFloatOrNull()
    val lengthFloat = length.toFloatOrNull()

    var typeError by remember { mutableStateOf(false) }
    var brandError by remember { mutableStateOf(false) }
    var sizeLabelError by remember { mutableStateOf(false) }
    var shoulderError by remember { mutableStateOf(false) }
    var chestError by remember { mutableStateOf(false) }
    var waistError by remember { mutableStateOf(false) }
    var hipError by remember { mutableStateOf(false) }
    var sleeveError by remember { mutableStateOf(false) }
    var riseError by remember { mutableStateOf(false) }
    var thighError by remember { mutableStateOf(false) }
    var hemError by remember { mutableStateOf(false) }
    var lengthError by remember { mutableStateOf(false) }

    val isTypeValid = type.isNotBlank()
    val isBrandValid = brand.isNotBlank()
    val isSizeLabelValid = sizeLabel.isNotBlank()
    val isShoulderValid = shoulder.isBlank() || shoulderFloat != null
    val isChestValid = chest.isBlank() || chestFloat != null
    val isWaistValid = waist.isBlank() || waistFloat != null
    val isHipValid = hip.isBlank() || hipFloat != null
    val isSleeveValid = sleeve.isBlank() || sleeveFloat != null
    val isRiseValid = rise.isBlank() || riseFloat != null
    val isThighValid = thigh.isBlank() || thighFloat != null
    val isHemValid = hem.isBlank() || hemFloat != null
    val isLengthValid = length.isBlank() || lengthFloat != null

    typeError = !isTypeValid
    brandError = !isBrandValid
    sizeLabelError = !isSizeLabelValid
    shoulderError = !isShoulderValid
    chestError = !isChestValid
    waistError = !isWaistValid
    hipError = !isHipValid
    sleeveError = !isSleeveValid
    riseError = !isRiseValid
    thighError = !isThighValid
    hemError = !isHemValid
    lengthError = !isLengthValid

    val isRequiredValid = !typeError && !brandError && !sizeLabelError
    val isFormValid = isRequiredValid && !shoulderError && !chestError && !waistError && !hipError
            && !sleeveError && !riseError && !thighError && !hemError && !lengthError

    val typeBorderColor = if (typeError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val typeBackgroundColor = if (typeError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent
    val brandBorderColor = if (brandError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val brandBackgroundColor = if (brandError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(type, brand, sizeLabel, shoulder, chest, waist, hip, sleeve, rise, thigh, hem, length) {
        onUpdateFormState(isRequiredValid, isFormValid)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(WindowInsets.ime.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        BorderColumn("* 일체형 종류", typeBorderColor, typeBackgroundColor) {
            SingleSelectableChipGroup(
                options = onePieceTypes,
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
            keyList = onePieceKeys,
            keyMapping = ::normalizeOnePieceKey,
            initialSizeLabel = sizeLabel.uppercase(),
            snackbarHostState = snackbarHostState,
            onExtracted = { extractedSizeMap  ->
                val sizeMap = extractedSizeMap
                val selectedSize = sizeLabel.uppercase()
                sizeLabel = selectedSize

                if (sizeMap[selectedSize] != null) {
                    sizeMap[selectedSize]?.let { values ->
                        shoulder = values["SHOULDER"] ?: ""
                        chest = values["CHEST"] ?: ""
                        waist = values["WAIST"] ?: ""
                        hip = values["HIP"] ?: ""
                        sleeve = values["SLEEVE"] ?: ""
                        rise = values["RISE"] ?: ""
                        thigh = values["THIGH"] ?: ""
                        hem = values["HEM"] ?: ""
                        length = values["LENGTH"] ?: ""
                    }
                } else {
                    sizeLabel = ""
                    shoulder = ""
                    chest = ""
                    waist = ""
                    hip = ""
                    sleeve = ""
                    rise = ""
                    thigh = ""
                    hem = ""
                    length = ""
                }
            },
            onFailed = {
                sizeLabel = ""
                shoulder = ""
                chest = ""
                waist = ""
                hip = ""
                sleeve = ""
                rise = ""
                thigh = ""
                hem = ""
                length = ""
            },
            onLabelSelected = { extractedSizeMap, selectedExtractedLabel ->
                sizeLabel = selectedExtractedLabel
                extractedSizeMap[selectedExtractedLabel]?.let {
                    shoulder = it["SHOULDER"] ?: ""
                    chest = it["CHEST"] ?: ""
                    waist = it["WAIST"] ?: ""
                    hip = it["HIP"] ?: ""
                    sleeve = it["SLEEVE"] ?: ""
                    rise = it["RISE"] ?: ""
                    thigh = it["THIGH"] ?: ""
                    hem = it["HEM"] ?: ""
                    length = it["LENGTH"] ?: ""
                }
            }
        )

        LabeledTextField(shoulder, { shoulder = it }, "어깨 너비 (cm)")
        LabeledTextField(chest, { chest = it }, "가슴 단면 (cm)")
        LabeledTextField(waist, { waist = it }, "허리 단면 (cm)")
        LabeledTextField(hip, { hip = it }, "엉덩이 단면 (cm)")
        LabeledTextField(sleeve, { sleeve = it }, "소매 길이 (cm)")
        LabeledTextField(rise, { rise = it }, "밑위 길이 (cm)")
        LabeledTextField(thigh, { thigh = it }, "허벅지 단면 (cm)")
        LabeledTextField(hem, { hem = it }, "밑단 단면 (cm)")
        LabeledTextField(length, { length = it }, "총장 (cm)")

        Spacer(Modifier.height(8.dp))

        BorderColumn("핏") {
            SingleSelectableChipGroup(
                options = onePieceFits,
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

        Spacer(Modifier.height(16.dp))

        val currentOnePieceSize = OnePieceSize(
            type = type,
            brand = brand,
            sizeLabel = sizeLabel,
            shoulder = shoulderFloat,
            chest = chestFloat,
            waist = waistFloat,
            hip = hipFloat,
            sleeve = sleeveFloat,
            rise = riseFloat,
            thigh = thighFloat,
            hem = hemFloat,
            length = lengthFloat,
            fit = fit.ifBlank { null },
            note = note.ifBlank { null },
            date = LocalDate.now()
        )

        onSaved(currentOnePieceSize)
    }
}
