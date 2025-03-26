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
import com.aube.mysize.domain.model.OuterSize
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.viewmodel.OuterSizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun OuterSizeInputForm(
    viewModel: OuterSizeViewModel,
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

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        LabeledTextField(type, { type = it }, "종류 (예: 자켓)")
        LabeledTextField(brand, { brand = it }, "브랜드")
        LabeledTextField(sizeLabel, { sizeLabel = it }, "사이즈 라벨")
        LabeledTextField(shoulder, { shoulder = it }, "어깨 너비")
        LabeledTextField(chest, { chest = it }, "가슴 단면")
        LabeledTextField(length, { length = it }, "총장")
        LabeledTextField(sleeve, { sleeve = it }, "소매 길이")
        LabeledTextField(fit, { fit = it }, "핏")
        LabeledTextField(note, { note = it }, "비고", imeAction = ImeAction.Done) {
            coroutineScope.launch {
                delay(100) // 키보드 반응 대기 (중요!)
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        SaveButton(
            onClick = {
                viewModel.insert(
                    OuterSize(
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
            },
        )
    }
}
