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
import com.aube.mysize.domain.model.BottomSize
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.viewmodel.BottomSizeViewModel
import java.time.LocalDate

@Composable
fun BottomSizeInputForm(
    viewModel: BottomSizeViewModel,
    onSaved: () -> Unit
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
    var note by remember { mutableStateOf("") }

    Column {
        Text("하의 정보 입력", style = MaterialTheme.typography.titleLarge)
        LabeledTextField(type, { type = it }, "옷 종류 (예: 청바지)")
        LabeledTextField(brand, { brand = it }, "브랜드")
        LabeledTextField(sizeLabel, { sizeLabel = it }, "사이즈 라벨")
        LabeledTextField(waist, { waist = it }, "허리 단면")
        LabeledTextField(rise, { rise = it }, "밑위")
        LabeledTextField(hip, { hip = it }, "엉덩이 단면")
        LabeledTextField(thigh, { thigh = it }, "허벅지 단면")
        LabeledTextField(hem, { hem = it }, "밑단 단면")
        LabeledTextField(length, { length = it }, "총장")
        LabeledTextField(note, { note = it }, "비고")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.insert(
                BottomSize(
                    type = type,
                    brand = brand,
                    sizeLabel = sizeLabel,
                    waist = waist.toFloatOrNull(),
                    rise = rise.toFloatOrNull(),
                    hip = hip.toFloatOrNull(),
                    thigh = thigh.toFloatOrNull(),
                    hem = hem.toFloatOrNull(),
                    length = length.toFloatOrNull(),
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
