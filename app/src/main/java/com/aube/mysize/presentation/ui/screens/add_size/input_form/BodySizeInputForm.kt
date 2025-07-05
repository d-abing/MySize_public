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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.constants.genderTypes
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.chip_tap.SingleSelectableChipGroup
import com.aube.mysize.presentation.ui.screens.add_size.component.LabeledTextField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.LocalDateTime

@Composable
fun BodySizeInputForm(
    initialSize: BodySize?,
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

    LaunchedEffect(initialSize) {
        initialSize?.let { size ->
            gender = size.gender.displayName
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
        BorderColumn(stringResource(R.string.label_gender), genderBorderColor, genderBackgroundColor) {
            SingleSelectableChipGroup(
                options = genderTypes,
                selectedOption = gender,
                onSelect = { gender = it }
            )
        }

        LabeledTextField(height, { height = it }, stringResource(R.string.label_height), isError = heightError)
        LabeledTextField(weight, { weight = it }, stringResource(R.string.label_weight), isError = weightError)
        LabeledTextField(chest, { chest = it }, stringResource(R.string.label_chest), isError = chestError)
        LabeledTextField(waist, { waist = it }, stringResource(R.string.label_waist), isError = waistError)
        LabeledTextField(hip, { hip = it }, stringResource(R.string.label_hip), isError = hipError)
        LabeledTextField(neck, { neck = it }, stringResource(R.string.label_neck), isError = neckError)
        LabeledTextField(shoulder, { shoulder = it }, stringResource(R.string.label_shoulder), isError = shoulderError)
        LabeledTextField(arm, { arm = it }, stringResource(R.string.label_arm), isError = armError)
        LabeledTextField(leg, { leg = it }, stringResource(R.string.label_leg), isError = legError)
        LabeledTextField(footLength, { footLength = it }, stringResource(R.string.label_foot_length), isError = footLengthError)
        LabeledTextField(footWidth, { footWidth = it }, stringResource(R.string.label_foot_width), isError = footWidthError, imeAction = ImeAction.Done)

        val currentBodySize = BodySize(
            id = "",
            uid = Firebase.auth.currentUser?.uid ?: "",
            gender = when(gender) {
                "여성" -> Gender.FEMALE
                else -> Gender.MALE
            },
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
            date = LocalDateTime.now()
        )

        onSaved(currentBodySize)
    }
}
