package com.aube.mysize.presentation.ui.screens.add_size.input_form

import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.BodySize
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.ui.component.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.BodySizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun BodySizeInputForm(
    viewModel: BodySizeViewModel,
    snackbarHostState: SnackbarHostState,
    onSaved: () -> Unit
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

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val heightFloat = height.toFloatOrNull()
    val weightFloat = weight.toFloatOrNull()
    val chestFloat = chest.toFloatOrNull()
    val waistFloat = waist.toFloatOrNull()
    val hipFloat = hip.toFloatOrNull()
    val neckFloat = neck.toFloatOrNull()
    val shoulderFloat = shoulder.toFloatOrNull()
    val armFloat = arm.toFloatOrNull()
    val legFloat = leg.toFloatOrNull()

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


    val isRequiredValid = isGenderValid && isHeightValid && isWeightValid
    val isFormValid = isRequiredValid && isChestValid && isWaistValid && isHipValid && isNeckValid && isShoulderValid && isArmValid && isLegValid

    val genderBorderColor = if (genderError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val genderLabelColor = if (genderError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, genderBorderColor, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "* 성별",
                style = MaterialTheme.typography.labelMedium,
                color = genderLabelColor
            )
            Spacer(Modifier.height(16.dp))
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
        LabeledTextField(leg, { leg = it }, "다리 안쪽 길이 (cm)", isError = legError, imeAction = ImeAction.Done) {
            coroutineScope.launch {
                delay(100) // 키보드 반응 대기 (중요!)
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            SaveButton(
                enabled = isFormValid,
                onClick = {
                    viewModel.insert(
                        BodySize(
                            gender = gender,
                            height = heightFloat,
                            weight = weightFloat,
                            chest = chestFloat,
                            waist = waistFloat,
                            hip = hipFloat,
                            neck = neckFloat,
                            shoulder = shoulderFloat,
                            arm = armFloat,
                            leg = legFloat,
                            date = LocalDate.now()
                        )
                    )
                    onSaved()
                },
            )
        }
    }
}
