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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.presentation.constants.OTHER_BRAND
import com.aube.mysize.presentation.constants.bottomFits
import com.aube.mysize.presentation.constants.bottomKeys
import com.aube.mysize.presentation.constants.bottomTypes
import com.aube.mysize.presentation.constants.normalizeBottomKey
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.SizeOcrSelector
import com.aube.mysize.presentation.ui.component.chip_tap.SingleSelectableChipGroup
import com.aube.mysize.presentation.ui.screens.add_size.component.BrandChipInput
import com.aube.mysize.presentation.ui.screens.add_size.component.LabeledTextField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.LocalDateTime

@Composable
fun BottomSizeInputForm(
    initialSize: BottomSize?,
    brandList: List<String>,
    snackbarHostState: SnackbarHostState,
    onAddBrand: (String) -> Unit,
    onDeleteBrand: (String) -> Unit,
    onUpdateFormState: (isMandatoryFieldsFilled: Boolean, isAllFieldsValid: Boolean) -> Unit,
    onSaved: (BottomSize) -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var sizeLabel by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var rise by remember { mutableStateOf("") }
    var hip by remember { mutableStateOf("") }
    var thigh by remember { mutableStateOf("") }
    var hem by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var entryType by remember { mutableStateOf(SizeEntryType.MY) }

    LaunchedEffect(initialSize) {
        initialSize?.let { size ->
            type = size.type
            brand = size.brand
            sizeLabel = size.sizeLabel
            waist = size.waist?.toString() ?: ""
            rise = size.rise?.toString() ?: ""
            hip = size.hip?.toString() ?: ""
            thigh = size.thigh?.toString() ?: ""
            hem = size.hem?.toString() ?: ""
            length = size.length?.toString() ?: ""
            fit = size.fit ?: ""
            note = size.note ?: ""
            entryType = size.entryType
        }
    }

    // Float 변환
    val waistFloat = waist.toFloatOrNull()
    val riseFloat = rise.toFloatOrNull()
    val hipFloat = hip.toFloatOrNull()
    val thighFloat = thigh.toFloatOrNull()
    val hemFloat = hem.toFloatOrNull()
    val lengthFloat = length.toFloatOrNull()

    // 유효성 검사
    var typeError by remember { mutableStateOf(false) }
    var brandError by remember { mutableStateOf(false) }
    var sizeLabelError by remember { mutableStateOf(false) }
    var waistError by remember { mutableStateOf(false) }
    var riseError by remember { mutableStateOf(false) }
    var hipError by remember { mutableStateOf(false) }
    var thighError by remember { mutableStateOf(false) }
    var hemError by remember { mutableStateOf(false) }
    var lengthError by remember { mutableStateOf(false) }

    val isTypeValid = type.isNotBlank()
    val isBrandValid = brand.isNotBlank()
    val isSizeLabelValid = sizeLabel.isNotBlank()
    val isWaistValid = waist.isBlank() || waistFloat != null
    val isRiseValid = rise.isBlank() || riseFloat != null
    val isHipValid = hip.isBlank() || hipFloat != null
    val isThighValid = thigh.isBlank() || thighFloat != null
    val isHemValid = hem.isBlank() || hemFloat != null
    val isLengthValid = length.isBlank() || lengthFloat != null

    typeError = !isTypeValid
    brandError = !isBrandValid
    sizeLabelError = !isSizeLabelValid
    waistError = !isWaistValid
    riseError = !isRiseValid
    hipError = !isHipValid
    thighError = !isThighValid
    hemError = !isHemValid
    lengthError = !isLengthValid

    val isRequiredValid = isTypeValid && isBrandValid && isSizeLabelValid
    val isFormValid = isRequiredValid && isWaistValid && isRiseValid && isHipValid && isThighValid && isHemValid && isLengthValid

    val typeBorderColor = if (typeError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val typeBackgroundColor = if (typeError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent
    val brandBorderColor = if (brandError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val brandBackgroundColor = if (brandError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(type, brand, sizeLabel, waist, rise, hip, thigh, hem, length) {
        onUpdateFormState(isRequiredValid, isFormValid)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(WindowInsets.ime.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        BorderColumn(stringResource(R.string.label_bottom_type), typeBorderColor, typeBackgroundColor) {
            SingleSelectableChipGroup(
                options = bottomTypes,
                selectedOption = type,
                onSelect = { type = it }
            )
        }

        Spacer(Modifier.height(8.dp))

        BorderColumn(stringResource(R.string.label_brand), brandBorderColor, brandBackgroundColor) {
            BrandChipInput(
                brandList = brandList + OTHER_BRAND,
                selectedBrand = brand,
                onSelect = { brand = it },
                onDelete = onDeleteBrand,
                onAddBrand = onAddBrand
            )
        }

        LabeledTextField(
            value = sizeLabel,
            onValueChange = {
                sizeLabel = it
            },
            label = stringResource(R.string.label_size_label),
            modifier = Modifier.focusRequester(focusRequester),
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )

        Spacer(Modifier.height(8.dp))

        SizeOcrSelector(
            keyList = bottomKeys,
            keyMapping = ::normalizeBottomKey,
            initialSizeLabel = sizeLabel.uppercase(),
            snackbarHostState = snackbarHostState,
            onExtracted = { extractedSizeMap  ->
                val selectedSize = sizeLabel.uppercase()
                sizeLabel = selectedSize

                if (extractedSizeMap[selectedSize] != null) {
                    extractedSizeMap[selectedSize]?.let { values ->
                        waist = values["WAIST"] ?: ""
                        rise = values["RISE"] ?: ""
                        hip = values["HIP"] ?: ""
                        thigh = values["THIGH"] ?: ""
                        hem = values["HEM"] ?: ""
                        length = values["LENGTH"] ?: ""
                    }
                } else {
                    sizeLabel = ""
                    waist = ""
                    rise = ""
                    hip = ""
                    thigh = ""
                    hem = ""
                    length = ""
                }
            },
            onFailed = {
                sizeLabel = ""
                waist = ""
                rise = ""
                hip = ""
                thigh = ""
                hem = ""
                length = ""
            },
            onLabelSelected = { extractedSizeMap, selectedExtractedLabel ->
                sizeLabel = selectedExtractedLabel
                extractedSizeMap[selectedExtractedLabel]?.let {
                    waist = it["WAIST"] ?: ""
                    rise = it["RISE"] ?: ""
                    hip = it["HIP"] ?: ""
                    thigh = it["THIGH"] ?: ""
                    hem = it["HEM"] ?: ""
                    length = it["LENGTH"] ?: ""
                }
            }
        )

        LabeledTextField(waist, { waist = it }, stringResource(R.string.label_waist_width), isError = waistError)
        LabeledTextField(rise, { rise = it }, stringResource(R.string.label_rise_length), isError = riseError)
        LabeledTextField(hip, { hip = it }, stringResource(R.string.label_hip_width), isError = hipError)
        LabeledTextField(thigh, { thigh = it }, stringResource(R.string.label_thigh_width), isError = thighError)
        LabeledTextField(hem, { hem = it }, stringResource(R.string.label_hem_width), isError = hemError)
        LabeledTextField(length, { length = it }, stringResource(R.string.label_total_length), isError = lengthError)

        Spacer(Modifier.height(8.dp))

        BorderColumn(stringResource(R.string.label_fit)) {
            SingleSelectableChipGroup(
                options = bottomFits,
                selectedOption = fit,
                onSelect = { fit = it }
            )
        }

        LabeledTextField(
            value = note,
            onValueChange = { note = it },
            label = stringResource(R.string.label_memo),
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        )

        Spacer(Modifier.height(16.dp))

        val currentBottomSize = BottomSize(
            id = "",
            uid = Firebase.auth.currentUser?.uid ?: "",
            type = type,
            brand = brand,
            sizeLabel = sizeLabel,
            waist = waistFloat,
            rise = riseFloat,
            hip = hipFloat,
            thigh = thighFloat,
            hem = hemFloat,
            length = lengthFloat,
            fit = fit.ifBlank { null },
            note = note.ifBlank { null },
            date = LocalDateTime.now(),
            entryType = entryType
        )

        onSaved(currentBottomSize)
    }
}
