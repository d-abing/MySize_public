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
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.presentation.constants.OTHER_BRAND
import com.aube.mysize.presentation.constants.shoeFits
import com.aube.mysize.presentation.constants.shoeTypes
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.chip_tap.SingleSelectableChipGroup
import com.aube.mysize.presentation.ui.screens.add_size.component.BrandChipInput
import com.aube.mysize.presentation.ui.screens.add_size.component.LabeledTextField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.LocalDateTime

@Composable
fun ShoeSizeInputForm(
    initialSize: ShoeSize?,
    brandList: List<String>,
    snackbarHostState: SnackbarHostState,
    onAddBrand: (String) -> Unit,
    onDeleteBrand: (String) -> Unit,
    onUpdateFormState: (isMandatoryFieldsFilled: Boolean, isAllFieldsValid: Boolean) -> Unit,
    onSaved: (ShoeSize) -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var sizeLabel by remember { mutableStateOf("") }
    var footLength by remember { mutableStateOf("") }
    var footWidth by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var entryType by remember { mutableStateOf(SizeEntryType.MY) }

    LaunchedEffect(initialSize) {
        initialSize?.let { size ->
            type = size.type
            brand = size.brand
            sizeLabel = size.sizeLabel
            footLength = size.footLength?.toString() ?: ""
            footWidth = size.footWidth?.toString() ?: ""
            fit = size.fit ?: ""
            note = size.note ?: ""
            entryType = size.entryType
        }
    }

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
        BorderColumn(stringResource(R.string.label_shoes_type), typeBorderColor, typeBackgroundColor) {
            SingleSelectableChipGroup(
                options = shoeTypes,
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
            label = stringResource(R.string.label_shoes_size_label),
            modifier = Modifier.focusRequester(focusRequester),
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )

        Spacer(Modifier.height(8.dp))

        LabeledTextField(footLength, { footLength = it }, stringResource(R.string.label_foot_length), isError = footLengthError)
        LabeledTextField(footWidth, { footWidth = it }, stringResource(R.string.label_foot_width), isError = widthError)

        Spacer(Modifier.height(8.dp))

        BorderColumn(stringResource(R.string.label_fit)) {
            SingleSelectableChipGroup(
                options = shoeFits,
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

        val currentShoeSize = ShoeSize(
            id = "",
            uid = Firebase.auth.currentUser?.uid ?: "",
            type = type,
            brand = brand,
            sizeLabel = sizeLabel,
            footLength = footLengthFloat,
            footWidth = widthFloat,
            fit = fit.ifBlank { null },
            note = note.ifBlank { null },
            date = LocalDateTime.now(),
            entryType = entryType
        )

        onSaved(currentShoeSize)
    }
}
