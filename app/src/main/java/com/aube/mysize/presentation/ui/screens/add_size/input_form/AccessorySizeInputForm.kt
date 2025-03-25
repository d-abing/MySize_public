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
import com.aube.mysize.domain.model.AccessorySize
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.viewmodel.AccessorySizeViewModel
import java.time.LocalDate

@Composable
fun AccessorySizeInputForm(
    viewModel: AccessorySizeViewModel,
    onSaved: () -> Unit
) {
    var type by remember { mutableStateOf("") }
    var bodyPart by remember { mutableStateOf("") }
    var sizeLabel by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column {
        Text("악세사리 정보 입력", style = MaterialTheme.typography.titleLarge)
        LabeledTextField(type, { type = it }, "종류 (예: 반지, 팔찌)")
        LabeledTextField(bodyPart, { bodyPart = it }, "착용 부위 (예: 손목, 손가락)")
        LabeledTextField(sizeLabel, { sizeLabel = it }, "사이즈 라벨")
        LabeledTextField(brand, { brand = it }, "브랜드")
        LabeledTextField(note, { note = it }, "비고")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.insert(
                AccessorySize(
                    type = type,
                    bodyPart = bodyPart,
                    sizeLabel = sizeLabel,
                    brand = brand,
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
