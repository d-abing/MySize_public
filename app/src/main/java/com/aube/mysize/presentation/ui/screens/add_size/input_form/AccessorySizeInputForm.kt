package com.aube.mysize.presentation.ui.screens.add_size.input_form

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.AccessorySize
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.BrandChipInput
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.ui.component.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.AccessorySizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun AccessorySizeInputForm(
    viewModel: AccessorySizeViewModel,
    onSaved: () -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    val brandList by viewModel.brandList.collectAsState()
    var sizeLabel by remember { mutableStateOf("") }
    var bodyPart by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var typeError by remember { mutableStateOf(false) }
    var brandError by remember { mutableStateOf(false) }
    var sizeLabelError by remember { mutableStateOf(false) }

    val isTypeValid = type.isNotBlank()
    val isBrandValid = brand.isNotBlank()
    val isSizeLabelValid = sizeLabel.isNotBlank()

    typeError = !isTypeValid
    brandError = !isBrandValid
    sizeLabelError = !isSizeLabelValid

    val isRequiredValid = isTypeValid && isBrandValid && isSizeLabelValid

    val typeBorderColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val typeLabelColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
    val brandBorderColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val brandLabelColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        BorderColumn("* 악세사리 종류", typeBorderColor, typeLabelColor) {
            val accessoryTypes = listOf("반지", "팔찌", "목걸이", "모자", "벨트", "시계", "가방", "기타")
            SelectableChipGroup(
                options = accessoryTypes,
                selectedOption = type,
                onSelect = { type = it }
            )
        }

        Spacer(Modifier.height(8.dp))

        BorderColumn("* 브랜드", brandBorderColor, brandLabelColor) {
            BrandChipInput(
                brandList = brandList + "기타 브랜드",
                selectedBrand = brand,
                onSelect = { brand = it },
                onDelete = { viewModel.deleteBrand(it) },
                onAddBrand = { viewModel.insertBrand(it) }
            )
        }

        LabeledTextField(sizeLabel, { sizeLabel = it }, "* 사이즈 정보 (예: 9호, M, Free 등)", isError = sizeLabelError, keyboardType = KeyboardType.Text)
        LabeledTextField(bodyPart, { bodyPart = it }, "부위 (예: 약지 손가락)", keyboardType = KeyboardType.Text)

        Spacer(Modifier.height(8.dp))
        BorderColumn("핏") {
            val fits = listOf("작음", "딱 맞음", "큼")
            SelectableChipGroup(
                options = fits,
                selectedOption = fit,
                onSelect = { fit = it }
            )
        }

        LabeledTextField(note, { note = it }, "참고 사항", keyboardType = KeyboardType.Text, imeAction = ImeAction.Done) {
            coroutineScope.launch {
                delay(100)
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            SaveButton(
                enabled = isRequiredValid,
                onClick = {
                    viewModel.insert(
                        AccessorySize(
                            type = type,
                            brand = brand,
                            sizeLabel = sizeLabel,
                            bodyPart = bodyPart,
                            fit = fit.ifBlank { null },
                            note = note.ifBlank { null },
                            date = LocalDate.now()
                        )
                    )
                    onSaved()
                }
            )
        }
    }
}
