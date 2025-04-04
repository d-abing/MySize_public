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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.BottomSize
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.BrandChipInput
import com.aube.mysize.presentation.ui.component.LabeledTextField
import com.aube.mysize.presentation.ui.component.SaveButton
import com.aube.mysize.presentation.ui.component.SelectableChipGroup
import com.aube.mysize.presentation.ui.component.SizeOcrSelector
import com.aube.mysize.presentation.viewmodel.BottomSizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun BottomSizeInputForm(
    viewModel: BottomSizeViewModel,
    snackbarHostState: SnackbarHostState,
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

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        BorderColumn("* 하의 종류", typeBorderColor, typeLabelColor) {
            val bottomTypes = listOf(
                "청바지", "슬랙스", "면바지", "반바지", "트레이닝팬츠", "조거팬츠", "레깅스",
                "미니스커트", "미디스커트", "롱스커트", "기타 하의"
            )
            SelectableChipGroup(
                options = bottomTypes,
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
                onAddBrand = { viewModel.insertBrand(it, "하의") }
            )
        }

        LabeledTextField(sizeLabel, { sizeLabel = it }, "* 사이즈 라벨 (예: S, M, L / 90, 95, 100)",
            modifier = Modifier.focusRequester(focusRequester),
            isError = sizeLabelError,
            keyboardType = KeyboardType.Text
        )

        Spacer(Modifier.height(8.dp))

        SizeOcrSelector(
            keyList = listOf(
                "허리", "밑위", "엉덩이", "허벅지", "밑단", "총장", // 한글
                "WAIST", "RISE", "HIP", "THIGH", "HEM", "LENGTH"  // 영어
            ),
            keyMapping = ::normalizeBottomKey,
            initialSizeLabel = sizeLabel.uppercase(),
            snackbarHostState = snackbarHostState,
            onExtracted = { extractedSizeMap  ->
                val sizeMap = extractedSizeMap
                val selectedSize = sizeLabel.uppercase()
                sizeLabel = selectedSize

                if (sizeMap[selectedSize] != null) {
                    sizeMap[selectedSize]?.let { values ->
                        waist = values["WAIST"] ?: ""
                        rise = values["RISE"] ?: ""
                        hip = values["HIP"] ?: ""
                        thigh = values["THIGH"] ?: ""
                        hem = values["HEM"] ?: ""
                        length = values["LENGTH"] ?: ""
                    }
                } else {
                    sizeLabel = ""
                    waist = ""
                    rise = ""
                    hip = ""
                    thigh = ""
                    hem = ""
                    length = ""
                }
            },
            onLabelSelected = { extractedSizeMap, selectedExtractedLabel ->
                if (!selectedExtractedLabel.contains("알 수 없는 사이즈")) {
                    sizeLabel = selectedExtractedLabel
                } else {
                    focusRequester.requestFocus()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("정확한 사이즈 라벨이 기입되었는지 확인해주세요.")
                    }
                }
                extractedSizeMap[selectedExtractedLabel]?.let {
                    waist = it["WAIST"] ?: ""
                    rise = it["RISE"] ?: ""
                    hip = it["HIP"] ?: ""
                    thigh = it["THIGH"] ?: ""
                    hem = it["HEM"] ?: ""
                    length = it["LENGTH"] ?: ""
                }
            }
        )

        LabeledTextField(waist, { waist = it }, "허리 단면 (cm)", isError = waistError)
        LabeledTextField(rise, { rise = it }, "밑위 (cm)", isError = riseError)
        LabeledTextField(hip, { hip = it }, "엉덩이 단면 (cm)", isError = hipError)
        LabeledTextField(thigh, { thigh = it }, "허벅지 단면 (cm)", isError = thighError)
        LabeledTextField(hem, { hem = it }, "밑단 단면 (cm)", isError = hemError)
        LabeledTextField(length, { length = it }, "총장 (cm)", isError = lengthError)

        Spacer(Modifier.height(8.dp))

        BorderColumn("핏") {
            val bottomFits = listOf("슬림핏", "레귤러핏", "루즈핏", "테이퍼드핏")
            SelectableChipGroup(
                options = bottomFits,
                selectedOption = fit,
                onSelect = { fit = it }
            )
        }

        LabeledTextField(
            value = note,
            onValueChange = { note = it },
            label = "참고 사항",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            onDone = {
                coroutineScope.launch {
                    delay(100)
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            }
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
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

private fun normalizeBottomKey(original: String): String {
    val upper = original.uppercase()
    return when {
        "WAIST" in upper || "허리" in original -> "WAIST"
        "RISE" in upper || "밑위" in original -> "RISE"
        "HIP" in upper || "엉덩이" in original -> "HIP"
        "THIGH" in upper || "허벅지" in original -> "THIGH"
        "HEM" in upper || "밑단" in original -> "HEM"
        "LENGTH" in upper || "총장" in original -> "LENGTH"
        else -> upper
    }
}