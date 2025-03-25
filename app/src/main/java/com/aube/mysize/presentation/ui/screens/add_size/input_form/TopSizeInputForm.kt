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
import com.aube.mysize.domain.model.TopSize
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.viewmodel.TopSizeViewModel
import java.time.LocalDate

@Composable
fun TopSizeInputForm(
    viewModel: TopSizeViewModel,
    onSaved: () -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var sizeLabel by remember { mutableStateOf("") }
    var shoulder by remember { mutableStateOf("") }
    var chest by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var sleeve by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column {
        Text("상의 정보 입력", style = MaterialTheme.typography.titleLarge)
        LabeledTextField(type, { type = it }, "옷 종류 (예: 셔츠)")
        LabeledTextField(brand, { brand = it }, "브랜드")
        LabeledTextField(sizeLabel, { sizeLabel = it }, "사이즈 라벨")
        LabeledTextField(shoulder, { shoulder = it }, "어깨 너비")
        LabeledTextField(chest, { chest = it }, "가슴 단면")
        LabeledTextField(length, { length = it }, "총장")
        LabeledTextField(sleeve, { sleeve = it }, "소매 길이")
        LabeledTextField(fit, { fit = it }, "핏 (예: 오버핏)")
        LabeledTextField(note, { note = it }, "비고")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.insert(
                TopSize(
                    type = type,
                    brand = brand,
                    sizeLabel = sizeLabel,
                    shoulder = shoulder.toFloatOrNull(),
                    chest = chest.toFloatOrNull(),
                    length = length.toFloatOrNull(),
                    sleeve = sleeve.toFloatOrNull(),
                    fit = fit,
                    note = note,
                    date = LocalDate.now(),
                )
            )
            onSaved()
        }) {
            Text("저장하기")
        }
    }
}
