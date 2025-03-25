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
import com.aube.mysize.domain.model.ShoeSize
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.viewmodel.ShoeSizeViewModel
import java.time.LocalDate

@Composable
fun ShoeSizeInputForm(
    viewModel: ShoeSizeViewModel,
    onSaved: () -> Unit
) {
    var brand by remember { mutableStateOf("") }
    var sizeLabel by remember { mutableStateOf("") }
    var footLength by remember { mutableStateOf("") }
    var footWidth by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column {
        Text("신발 정보 입력", style = MaterialTheme.typography.titleLarge)
        LabeledTextField(brand, { brand = it }, "브랜드")
        LabeledTextField(sizeLabel, { sizeLabel = it }, "사이즈 라벨")
        LabeledTextField(footLength, { footLength = it }, "발 길이 (mm)")
        LabeledTextField(footWidth, { footWidth = it }, "발볼 너비 (mm)")
        LabeledTextField(fit, { fit = it }, "핏 (예: 작게 나옴)")
        LabeledTextField(note, { note = it }, "비고")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.insert(
                ShoeSize(
                    brand = brand,
                    sizeLabel = sizeLabel,
                    footLength = footLength.toFloatOrNull(),
                    footWidth = footWidth.toFloatOrNull(),
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
