package com.aube.mysize.presentation.ui.screens.add_size.input_form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.BodySize
import com.aube.mysize.presentation.ui.component.GenderSelector
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.viewmodel.BodySizeViewModel
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
    var thigh by remember { mutableStateOf("") }
    var calf by remember { mutableStateOf("") }

    Column {
        Text("신체 정보 입력", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        GenderSelector(gender) { gender = it }

        LabeledTextField(height, { height = it }, "키 (cm)")
        LabeledTextField(weight, { weight = it }, "몸무게 (kg)")
        LabeledTextField(chest, { chest = it }, "가슴둘레 (cm)")
        LabeledTextField(waist, { waist = it }, "허리둘레 (cm)")
        LabeledTextField(hip, { hip = it }, "엉덩이둘레 (cm)")
        LabeledTextField(neck, { neck = it }, "목둘레 (cm)")
        LabeledTextField(shoulder, { shoulder = it }, "어깨너비 (cm)")
        LabeledTextField(thigh, { thigh = it }, "허벅지둘레 (cm)")
        LabeledTextField(calf, { calf = it }, "종아리둘레 (cm)")

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
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
                    thigh = thigh.toFloatOrNull(),
                    calf = calf.toFloatOrNull(),
                    date = LocalDate.now()
                )
            )
            onSaved()
        }) {
            Text("저장하기")
        }
    }
}
