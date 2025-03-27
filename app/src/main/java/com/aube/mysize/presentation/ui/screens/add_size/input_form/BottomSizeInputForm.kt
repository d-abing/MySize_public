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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.BottomSize
import com.aube.mysize.presentation.ui.component.BrandChipInput
import com.aube.mysize.presentation.ui.component.InputBorderColumn
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.ui.component.SelectableChipGroup
import com.aube.mysize.presentation.viewmodel.BottomSizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun BottomSizeInputForm(
    viewModel: BottomSizeViewModel,
    onSaved: () -> Unit
) {
    var type by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    val brandList by viewModel.brandList.collectAsState()
    var sizeLabel by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var rise by remember { mutableStateOf("") }
    var hip by remember { mutableStateOf("") }
    var thigh by remember { mutableStateOf("") }
    var hem by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var fit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Float 변환
    val waistFloat = waist.toFloatOrNull()
    val riseFloat = rise.toFloatOrNull()
    val hipFloat = hip.toFloatOrNull()
    val thighFloat = thigh.toFloatOrNull()
    val hemFloat = hem.toFloatOrNull()
    val lengthFloat = length.toFloatOrNull()

    // 유효성 검사
    var typeError by remember { mutableStateOf(false) }
    var brandError by remember { mutableStateOf(false) }
    var sizeLabelError by remember { mutableStateOf(false) }
    var waistError by remember { mutableStateOf(false) }
    var riseError by remember { mutableStateOf(false) }
    var hipError by remember { mutableStateOf(false) }
    var thighError by remember { mutableStateOf(false) }
    var hemError by remember { mutableStateOf(false) }
    var lengthError by remember { mutableStateOf(false) }

    val isTypeValid = type.isNotBlank()
    val isBrandValid = brand.isNotBlank()
    val isSizeLabelValid = sizeLabel.isNotBlank()
    val isWaistValid = waist.isBlank() || waistFloat != null
    val isRiseValid = rise.isBlank() || riseFloat != null
    val isHipValid = hip.isBlank() || hipFloat != null
    val isThighValid = thigh.isBlank() || thighFloat != null
    val isHemValid = hem.isBlank() || hemFloat != null
    val isLengthValid = length.isBlank() || lengthFloat != null

    typeError = !isTypeValid
    brandError = !isBrandValid
    sizeLabelError = !isSizeLabelValid
    waistError = !isWaistValid
    riseError = !isRiseValid
    hipError = !isHipValid
    thighError = !isThighValid
    hemError = !isHemValid
    lengthError = !isLengthValid

    val isRequiredValid = isTypeValid && isBrandValid && isSizeLabelValid
    val isFormValid = isRequiredValid && isWaistValid && isRiseValid && isHipValid && isThighValid && isHemValid && isLengthValid

    val typeBorderColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val typeLabelColor = if (typeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
    val brandBorderColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val brandLabelColor = if (brandError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        InputBorderColumn(typeBorderColor) {
            Text(
                text = "* 하의 종류",
                style = MaterialTheme.typography.labelMedium,
                color = typeLabelColor
            )
            Spacer(Modifier.height(16.dp))
            val bottomTypes = listOf(
                "청바지", "슬랙스", "면바지", "반바지", "트레이닝팬츠", "조거팬츠", "레깅스", "미니스커트", "미디스커트", "롱스커트", "기타 하의"
            )
            SelectableChipGroup(
                options = bottomTypes,
                selectedOption = type,
                onSelect = { type = it }
            )
        }

        Spacer(Modifier.height(8.dp))

        InputBorderColumn(brandBorderColor) {
            Text(
                text = "* 브랜드",
                style = MaterialTheme.typography.labelMedium,
                color = brandLabelColor,
            )
            Spacer(Modifier.height(16.dp))
            BrandChipInput(
                brandList = brandList + "기타 브랜드",
                selectedBrand = brand,
                onSelect = { brand = it },
                onDelete = { viewModel.deleteBrand(it) },
                onAddBrand = { viewModel.insertBrand(it, "하의") }
            )
        }

        LabeledTextField(sizeLabel, { sizeLabel = it }, "* 사이즈 라벨 (예: S, M, L / 90, 95, 100)",
            isError = sizeLabelError, keyboardType = KeyboardType.Text)
        LabeledTextField(waist, { waist = it }, "허리 단면 (cm)", isError = waistError)
        LabeledTextField(rise, { rise = it }, "밑위 (cm)", isError = riseError)
        LabeledTextField(hip, { hip = it }, "엉덩이 단면 (cm)", isError = hipError)
        LabeledTextField(thigh, { thigh = it }, "허벅지 단면 (cm)", isError = thighError)
        LabeledTextField(hem, { hem = it }, "밑단 단면 (cm)", isError = hemError)
        LabeledTextField(length, { length = it }, "총장 (cm)", isError = lengthError)

        Spacer(Modifier.height(8.dp))

        InputBorderColumn(MaterialTheme.colorScheme.outline) {
            Text("핏", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(16.dp))
            val bottomFits = listOf("슬림핏", "레귤러핏", "루즈핏", "테이퍼드핏", "기타")
            SelectableChipGroup(
                options = bottomFits,
                selectedOption = fit,
                onSelect = { fit = it }
            )
        }

        LabeledTextField(note, { note = it }, "참고 사항", imeAction = ImeAction.Done, keyboardType = KeyboardType.Text) {
            coroutineScope.launch {
                delay(100)
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (!isRequiredValid) {
                Text(
                    text = "종류, 브랜드, 사이즈 라벨은 필수 입력입니다.",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            if(!isFormValid && isRequiredValid) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.error,
                    text = "사이즈는 숫자로 입력해주세요.",
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }
            Spacer(Modifier.width(16.dp))
            SaveButton(
                enabled = isFormValid,
                onClick = {
                    viewModel.insert(
                        BottomSize(
                            type = type,
                            brand = brand,
                            sizeLabel = sizeLabel,
                            waist = waistFloat,
                            rise = riseFloat,
                            hip = hipFloat,
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
