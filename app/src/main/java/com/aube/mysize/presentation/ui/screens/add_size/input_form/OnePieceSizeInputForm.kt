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
import androidx.compose.material3.Text
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
import com.aube.mysize.domain.model.OnePieceSize
import com.aube.mysize.presentation.ui.component.BrandChipInput
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.ui.component.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.OnePieceSizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun OnePieceSizeInputForm(
    viewModel: OnePieceSizeViewModel,
    onSaved: () -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    val brandList by viewModel.brandList.collectAsState()
    var sizeLabel by remember { mutableStateOf("") }
    var shoulder by remember { mutableStateOf("") }
    var chest by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var hip by remember { mutableStateOf("") }
    var sleeve by remember { mutableStateOf("") }
    var rise by remember { mutableStateOf("") }
    var thigh by remember { mutableStateOf("") }
    var hem by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val shoulderFloat = shoulder.toFloatOrNull()
    val chestFloat = chest.toFloatOrNull()
    val waistFloat = waist.toFloatOrNull()
    val hipFloat = hip.toFloatOrNull()
    val sleeveFloat = sleeve.toFloatOrNull()
    val riseFloat = rise.toFloatOrNull()
    val thighFloat = thigh.toFloatOrNull()
    val hemFloat = hem.toFloatOrNull()
    val lengthFloat = length.toFloatOrNull()

    var typeError by remember { mutableStateOf(false) }
    var brandError by remember { mutableStateOf(false) }
    var sizeLabelError by remember { mutableStateOf(false) }
    var shoulderError by remember { mutableStateOf(false) }
    var chestError by remember { mutableStateOf(false) }
    var waistError by remember { mutableStateOf(false) }
    var hipError by remember { mutableStateOf(false) }
    var sleeveError by remember { mutableStateOf(false) }
    var riseError by remember { mutableStateOf(false) }
    var thighError by remember { mutableStateOf(false) }
    var hemError by remember { mutableStateOf(false) }
    var lengthError by remember { mutableStateOf(false) }

    val isTypeValid = type.isNotBlank()
    val isBrandValid = brand.isNotBlank()
    val isSizeLabelValid = sizeLabel.isNotBlank()
    val isShoulderValid = shoulder.isBlank() || shoulderFloat != null
    val isChestValid = chest.isBlank() || chestFloat != null
    val isWaistValid = waist.isBlank() || waistFloat != null
    val isHipValid = hip.isBlank() || hipFloat != null
    val isSleeveValid = sleeve.isBlank() || sleeveFloat != null
    val isRiseValid = rise.isBlank() || riseFloat != null
    val isThighValid = thigh.isBlank() || thighFloat != null
    val isHemValid = hem.isBlank() || hemFloat != null
    val isLengthValid = length.isBlank() || lengthFloat != null

    typeError = !isTypeValid
    brandError = !isBrandValid
    sizeLabelError = !isSizeLabelValid
    shoulderError = !isShoulderValid
    chestError = !isChestValid
    waistError = !isWaistValid
    hipError = !isHipValid
    sleeveError = !isSleeveValid
    riseError = !isRiseValid
    thighError = !isThighValid
    hemError = !isHemValid
    lengthError = !isLengthValid

    val isRequiredValid = !typeError && !brandError && !sizeLabelError
    val isFormValid = isRequiredValid && !shoulderError && !chestError && !waistError && !hipError
            && !sleeveError && !riseError && !thighError && !hemError && !lengthError

    val typeBorderColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val typeLabelColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
    val brandBorderColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val brandLabelColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        BorderColumn("* 일체형 종류", typeBorderColor, typeLabelColor) {
            val types = listOf("원피스", "점프수트", "멜빵바지", "기타")
            SelectableChipGroup(
                options = types,
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
                onAddBrand = { viewModel.insertBrand(it, "일체형") }
            )
        }

        LabeledTextField(sizeLabel, { sizeLabel = it }, "* 사이즈 라벨",
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )
        LabeledTextField(shoulder, { shoulder = it }, "어깨 너비 (cm)")
        LabeledTextField(chest, { chest = it }, "가슴 단면 (cm)")
        LabeledTextField(waist, { waist = it }, "허리 단면 (cm)")
        LabeledTextField(hip, { hip = it }, "엉덩이 단면 (cm)")
        LabeledTextField(sleeve, { sleeve = it }, "소매 길이 (cm)")
        LabeledTextField(rise, { rise = it }, "밑위 길이 (cm)")
        LabeledTextField(thigh, { thigh = it }, "허벅지 단면 (cm)")
        LabeledTextField(hem, { hem = it }, "밑단 단면 (cm)")
        LabeledTextField(length, { length = it }, "총장 (cm)")

        Spacer(Modifier.height(8.dp))
        BorderColumn("핏") {
            val fits = listOf("슬림핏", "레귤러핏", "오버핏")
            SelectableChipGroup(options = fits, selectedOption = fit, onSelect = { fit = it })
        }

        LabeledTextField(note, { note = it }, "참고사항", imeAction = ImeAction.Done) {
            coroutineScope.launch {
                delay(100)
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            SaveButton(
                enabled = isFormValid,
                onClick = {
                    viewModel.insert(
                        OnePieceSize(
                            type = type,
                            brand = brand,
                            sizeLabel = sizeLabel,
                            shoulder = shoulderFloat,
                            chest = chestFloat,
                            waist = waistFloat,
                            hip = hipFloat,
                            sleeve = sleeveFloat,
                            rise = riseFloat,
                            thigh = thighFloat,
                            hem = hemFloat,
                            length = lengthFloat,
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
