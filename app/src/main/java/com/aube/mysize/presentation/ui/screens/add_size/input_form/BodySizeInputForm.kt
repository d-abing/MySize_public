package com.aube.mysize.presentation.ui.screens.add_size.input_form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.ui.screens.add_size.component.BorderColumn
import com.aube.mysize.presentation.ui.screens.add_size.component.LabeledTextField
import com.aube.mysize.presentation.ui.screens.add_size.component.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel
import java.time.LocalDate

@Composable
fun BodySizeInputForm(
    oldSizeId: Int?,
    viewModel: BodySizeViewModel,
    onUpdateFormState: (isMandatoryFieldsFilled: Boolean, isAllFieldsValid: Boolean) -> Unit,
    onSaved: (BodySize) -> Unit
) {
    var gender by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var chest by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var hip by remember { mutableStateOf("") }
    var neck by remember { mutableStateOf("") }
    var shoulder by remember { mutableStateOf("") }
    var arm by remember { mutableStateOf("") }
    var leg by remember { mutableStateOf("") }
    var footLength by remember { mutableStateOf("") }
    var footWidth by remember { mutableStateOf("") }

    LaunchedEffect(oldSizeId) {
        val oldSize = oldSizeId?.let { viewModel.getSizeById(it) }

        oldSize?.let { size ->
            gender = size.gender
            height = size.height.toString()
            weight = size.weight.toString()
            chest = size.chest?.toString() ?: ""
            waist = size.waist?.toString() ?: ""
            hip = size.hip?.toString() ?: ""
            neck = size.neck?.toString() ?: ""
            shoulder = size.shoulder?.toString() ?: ""
            arm = size.arm?.toString() ?: ""
            leg = size.leg?.toString() ?: ""
            footLength = size.footLength?.toString() ?: ""
            footWidth = size.footWidth?.toString() ?: ""
        }
    }

    val heightFloat = height.toFloatOrNull()
    val weightFloat = weight.toFloatOrNull()
    val chestFloat = chest.toFloatOrNull()
    val waistFloat = waist.toFloatOrNull()
    val hipFloat = hip.toFloatOrNull()
    val neckFloat = neck.toFloatOrNull()
    val shoulderFloat = shoulder.toFloatOrNull()
    val armFloat = arm.toFloatOrNull()
    val legFloat = leg.toFloatOrNull()
    val footLengthFloat = footLength.toFloatOrNull()
    val footWidthFloat = footWidth.toFloatOrNull()

    var genderError by remember { mutableStateOf(false) }
    var heightError by remember { mutableStateOf(false) }
    var weightError by remember { mutableStateOf(false) }
    var chestError by remember { mutableStateOf(false) }
    var waistError by remember { mutableStateOf(false) }
    var hipError by remember { mutableStateOf(false) }
    var neckError by remember { mutableStateOf(false) }
    var shoulderError by remember { mutableStateOf(false) }
    var armError by remember { mutableStateOf(false) }
    var legError by remember { mutableStateOf(false) }
    var footLengthError by remember { mutableStateOf(false) }
    var footWidthError by remember { mutableStateOf(false) }

    val isGenderValid = gender.isNotBlank()
    val isHeightValid = height.isNotBlank() && heightFloat != null
    val isWeightValid = weight.isNotBlank() && weightFloat != null
    val isChestValid = chest.isBlank() || chestFloat != null
    val isWaistValid = waist.isBlank() || waistFloat != null
    val isHipValid = hip.isBlank() || hipFloat != null
    val isNeckValid = neck.isBlank() || neckFloat != null
    val isShoulderValid = shoulder.isBlank() || shoulderFloat != null
    val isArmValid = arm.isBlank() || armFloat != null
    val isLegValid = leg.isBlank() || legFloat != null
    val isFootLengthValid = footLength.isBlank() || footLengthFloat != null
    val isFootWidthValid = footWidth.isBlank() || footWidthFloat != null

    genderError = !isGenderValid
    heightError = !isHeightValid
    weightError = !isWeightValid
    chestError = !isChestValid
    waistError = !isWaistValid
    hipError = !isHipValid
    neckError = !isNeckValid
    shoulderError = !isShoulderValid
    armError = !isArmValid
    legError = !isLegValid
    footLengthError = !isFootLengthValid
    footWidthError = !isFootWidthValid

    val isRequiredValid = isGenderValid && isHeightValid && isWeightValid
    val isFormValid = isRequiredValid && isChestValid && isWaistValid && isHipValid
            && isNeckValid && isShoulderValid && isArmValid && isLegValid
            && isFootLengthValid && isFootWidthValid

    val genderBackgroundColor = if (genderError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent
    val genderBorderColor = if (genderError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant

    LaunchedEffect(gender, height, weight, chest, waist, hip, neck, shoulder, arm, leg, footLength, footWidth) {
        onUpdateFormState(isRequiredValid, isFormValid)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(WindowInsets.ime.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        BorderColumn("* 성별", genderBorderColor, genderBackgroundColor) {
            val options = listOf("남성", "여성")
            SelectableChipGroup(
                options = options,
                selectedOption = gender,
                onSelect = { gender = it }
            )
        }

        LabeledTextField(height, { height = it }, "* 키 (cm)", isError = heightError)
        LabeledTextField(weight, { weight = it }, "* 몸무게 (kg)", isError = weightError)
        LabeledTextField(chest, { chest = it }, "가슴 둘레 (cm)", isError = chestError)
        LabeledTextField(waist, { waist = it }, "허리 둘레 (cm)", isError = waistError)
        LabeledTextField(hip, { hip = it }, "엉덩이 둘레 (cm)", isError = hipError)
        LabeledTextField(neck, { neck = it }, "목 둘레 (cm)", isError = neckError)
        LabeledTextField(shoulder, { shoulder = it }, "어깨 너비 (cm)", isError = shoulderError)
        LabeledTextField(arm, { arm = it }, "팔 길이 (cm)", isError = armError)
        LabeledTextField(leg, { leg = it }, "다리 안쪽 길이 (cm)", isError = legError)
        LabeledTextField(footLength, { footLength = it }, "발 길이 (mm)", isError = footLengthError)
        LabeledTextField(footWidth, { footWidth = it }, "발 너비 (mm)", isError = footWidthError, imeAction = ImeAction.Done)

        val currentBodySize = BodySize(
            gender = gender,
            height = heightFloat ?: 0f,
            weight = weightFloat ?: 0f,
            chest = chestFloat,
            waist = waistFloat,
            hip = hipFloat,
            neck = neckFloat,
            shoulder = shoulderFloat,
            arm = armFloat,
            leg = legFloat,
            footLength = footLengthFloat,
            footWidth = footWidthFloat,
            date = LocalDate.now()
        )

        onSaved(currentBodySize)
    }
}
