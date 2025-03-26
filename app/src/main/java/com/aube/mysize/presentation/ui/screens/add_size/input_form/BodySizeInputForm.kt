package com.aube.mysize.presentation.ui.screens.add_size.input_form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    onSaved: () -> Unit
) {
    var gender by remember { mutableStateOf("남성") }
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

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        Spacer(Modifier.height(16.dp))
        val options = listOf("남성", "여성")
        SelectableChipGroup(
            options = options,
            selectedOption = gender,
            onSelect = { gender = it }
        )
        Spacer(Modifier.height(16.dp))

        LabeledTextField(height, { height = it }, "키 (cm)")
        LabeledTextField(weight, { weight = it }, "몸무게 (kg)")
        LabeledTextField(chest, { chest = it }, "가슴 둘레 (cm)")
        LabeledTextField(waist, { waist = it }, "허리 둘레 (cm)")
        LabeledTextField(hip, { hip = it }, "엉덩이 둘레 (cm)")
        LabeledTextField(neck, { neck = it }, "목 둘레 (cm)")
        LabeledTextField(shoulder, { shoulder = it }, "어깨 너비 (cm)")
        LabeledTextField(arm, { arm = it }, "팔 길이 (cm)")
        LabeledTextField(leg, { leg = it }, "다리 안쪽 길이 (cm)", imeAction = ImeAction.Done) {
            coroutineScope.launch {
                delay(100) // 키보드 반응 대기 (중요!)
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }

        Spacer(Modifier.height(16.dp))
        SaveButton(
            onClick = {
                viewModel.insert(
                    BodySize(
                        gender = gender,
                        height = height.toFloatOrNull(),
                        weight = weight.toFloatOrNull(),
                        chest = chest.toFloatOrNull(),
                        waist = waist.toFloatOrNull(),
                        hip = hip.toFloatOrNull(),
                        neck = neck.toFloatOrNull(),
                        shoulder = shoulder.toFloatOrNull(),
                        arm = arm.toFloatOrNull(),
                        leg = leg.toFloatOrNull(),
                        date = LocalDate.now()
                    )
                )
                onSaved()
            },
        )
    }
}
