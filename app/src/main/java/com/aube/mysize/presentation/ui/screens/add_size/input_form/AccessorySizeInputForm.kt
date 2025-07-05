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
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.presentation.constants.OTHER_BRAND
import com.aube.mysize.presentation.constants.accessoryFits
import com.aube.mysize.presentation.constants.accessoryTypes
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.chip_tap.SingleSelectableChipGroup
import com.aube.mysize.presentation.ui.screens.add_size.component.BrandChipInput
import com.aube.mysize.presentation.ui.screens.add_size.component.LabeledTextField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.LocalDateTime

@Composable
fun AccessorySizeInputForm(
    initialSize: AccessorySize?,
    brandList: List<String>,
    onAddBrand: (String) -> Unit,
    onDeleteBrand: (String) -> Unit,
    onUpdateFormState: (isMandatoryFieldsFilled: Boolean, isAllFieldsValid: Boolean) -> Unit,
    onSaved: (AccessorySize) -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var sizeLabel by remember { mutableStateOf("") }
    var bodyPart by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var entryType by remember { mutableStateOf(SizeEntryType.MY) }

    LaunchedEffect(initialSize) {
        initialSize?.let { size ->
            type = size.type
            brand = size.brand
            sizeLabel = size.sizeLabel
            bodyPart = size.bodyPart ?: ""
            fit = size.fit ?: ""
            note = size.note ?: ""
            entryType = size.entryType
        }
    }

    var typeError by remember { mutableStateOf(false) }
    var brandError by remember { mutableStateOf(false) }
    var sizeLabelError by remember { mutableStateOf(false) }

    val isTypeValid = type.isNotBlank()
    val isBrandValid = brand.isNotBlank()
    val isSizeLabelValid = sizeLabel.isNotBlank()

    typeError = !isTypeValid
    brandError = !isBrandValid
    sizeLabelError = !isSizeLabelValid

    val isRequiredValid = isTypeValid && isBrandValid && isSizeLabelValid

    val typeBorderColor = if (typeError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val typeBackgroundColor = if (typeError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent
    val brandBorderColor = if (brandError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
    val brandBackgroundColor = if (brandError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(type, brand, sizeLabel) {
        onUpdateFormState(isRequiredValid, isRequiredValid)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(WindowInsets.ime.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        BorderColumn(stringResource(R.string.label_accessory_type), typeBorderColor, typeBackgroundColor) {
            SingleSelectableChipGroup(
                options = accessoryTypes,
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
            label = stringResource(R.string.label_size_info),
            modifier = Modifier.focusRequester(focusRequester),
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )
        LabeledTextField(bodyPart, { bodyPart = it }, stringResource(R.string.label_body_part), keyboardType = KeyboardType.Text)

        Spacer(Modifier.height(8.dp))

        BorderColumn(stringResource(R.string.label_fit)) {
            SingleSelectableChipGroup(
                options = accessoryFits,
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

        val currentAccessorySize = AccessorySize(
            id = "",
            uid = Firebase.auth.currentUser?.uid ?: "",
            type = type,
            brand = brand,
            sizeLabel = sizeLabel,
            bodyPart = bodyPart.ifBlank { null },
            fit = fit.ifBlank { null },
            note = note.ifBlank { null },
            date = LocalDateTime.now(),
            entryType = entryType
        )

        onSaved(currentAccessorySize)
    }
}
