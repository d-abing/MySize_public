package com.aube.mysize.presentation.ui.screens.recommend.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.AgeGroup
import com.aube.mysize.presentation.model.PriceRange
import com.aube.mysize.presentation.model.Style
import com.aube.mysize.presentation.model.UserPreference
import com.aube.mysize.presentation.ui.screens.add_size.component.MultiSelectableChipGroup
import com.aube.mysize.presentation.ui.screens.add_size.component.SingleSelectableChipGroup

@Composable
fun UserPreferenceBottomSheet(
    currentPreference: UserPreference,
    preferenceType: String,
    onSave: (UserPreference) -> Unit,
    onCancel: () -> Unit
) {
    var selectedStyles by remember { mutableStateOf(currentPreference.styles) }
    var selectedAge by remember { mutableStateOf(currentPreference.ageGroup) }
    var selectedPrice by remember { mutableStateOf(currentPreference.priceRange) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp, end = 24.dp)
    ) {
        Text(
            text = when (preferenceType) {
                "Styles" -> "선호하는 스타일을 선택해주세요"
                "AgeGroup" -> "연령대를 선택해주세요"
                "PriceRange" -> "선호하는 가격대를 선택해주세요"
                else -> "설정"
            },
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(16.dp))

        when (preferenceType) {
            "Styles" -> {
                MultiSelectableChipGroup(
                    options = Style.entries,
                    selectedOptions = selectedStyles,
                    onSelectToggle = { style ->
                        selectedStyles = if (selectedStyles.contains(style)) {
                            selectedStyles - style
                        } else {
                            selectedStyles + style
                        }
                    },
                    optionTextSelector = { it.displayName } // 스타일에 displayName 프로퍼티가 있다고 가정
                )
            }

            "AgeGroup" -> {
                SingleSelectableChipGroup(
                    options = AgeGroup.entries,
                    selectedOption = selectedAge,
                    onSelect = { selectedAge = it },
                    optionTextSelector = { it.displayName }
                )
            }

            "PriceRange" -> {
                SingleSelectableChipGroup(
                    options = PriceRange.entries,
                    selectedOption = selectedPrice,
                    onSelect = { selectedPrice = it },
                    optionTextSelector = { it.displayName }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = {
                onSave(
                    when (preferenceType) {
                        "Styles" -> currentPreference.copy(styles = selectedStyles.toList())
                        "AgeGroup" -> currentPreference.copy(ageGroup = selectedAge)
                        "PriceRange" -> currentPreference.copy(priceRange = selectedPrice)
                        else -> currentPreference
                    }
                )
            }) {
                Text("저장")
            }
        }
    }
}
