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
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.presentation.ui.component.addsize.BorderColumn
import com.aube.mysize.presentation.ui.component.addsize.BrandChipInput
import com.aube.mysize.presentation.ui.component.addsize.LabeledTextField
import com.aube.mysize.presentation.ui.component.addsize.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import java.time.LocalDate

@Composable
fun AccessorySizeInputForm(
    viewModel: AccessorySizeViewModel,
    onUpdateFormState: (isMandatoryFieldsFilled: Boolean, isAllFieldsValid: Boolean) -> Unit,
    onSaved: (AccessorySize) -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    val brandList by viewModel.brandList.collectAsState()
    var sizeLabel by remember { mutableStateOf("") }
    var bodyPart by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
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
        BorderColumn("* 악세사리 종류", typeBorderColor, typeBackgroundColor) {
            val accessoryTypes = listOf("반지", "팔찌", "목걸이", "모자", "벨트", "시계", "가방", "기타")
            SelectableChipGroup(
                options = accessoryTypes,
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
            label = "* 사이즈 정보 (예: 9호, M, Free 등)",
            modifier = Modifier.focusRequester(focusRequester),
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )
        LabeledTextField(bodyPart, { bodyPart = it }, "부위 (예: 약지 손가락)", keyboardType = KeyboardType.Text)

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

        val currentAccessorySize = AccessorySize(
            type = type,
            brand = brand,
            sizeLabel = sizeLabel,
            bodyPart = bodyPart.ifBlank { null },
            fit = fit.ifBlank { null },
            note = note.ifBlank { null },
            date = LocalDate.now()
        )

        onSaved(currentAccessorySize)
    }
}
