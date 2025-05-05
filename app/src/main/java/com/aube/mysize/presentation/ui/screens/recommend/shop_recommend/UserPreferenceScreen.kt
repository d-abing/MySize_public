package com.aube.mysize.presentation.ui.screens.recommend.shop_recommend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.AgeGroup
import com.aube.mysize.presentation.model.Gender
import com.aube.mysize.presentation.model.PriceRange
import com.aube.mysize.presentation.model.Style
import com.aube.mysize.presentation.model.UserPreference
import com.aube.mysize.presentation.ui.screens.add_size.component.MultiSelectableChipGroup

@Composable
fun UserPreferenceScreen(
    gender: Gender,
    initialPreference: UserPreference? = null,
    onSave: (UserPreference) -> Unit
) {
    var selectedStyles by rememberSaveable {
        mutableStateOf(initialPreference?.styles ?: emptyList())
    }
    var selectedAges by rememberSaveable {
        mutableStateOf(initialPreference?.ageGroups ?: emptyList())
    }
    var selectedPrices by rememberSaveable {
        mutableStateOf(initialPreference?.priceRanges ?: emptyList())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("스타일을 선택해주세요", style = MaterialTheme.typography.titleMedium)
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
            optionTextSelector = { it.displayName }
        )

        Text("나이대를 선택해주세요", style = MaterialTheme.typography.titleMedium)
        MultiSelectableChipGroup(
            options = AgeGroup.entries,
            selectedOptions = selectedAges,
            onSelectToggle = { age ->
                selectedAges = if (selectedAges.contains(age)) {
                    selectedAges - age
                } else {
                    selectedAges + age
                }
            },
            optionTextSelector = { it.displayName }
        )

        Text("선호 금액대를 선택해주세요", style = MaterialTheme.typography.titleMedium)
        MultiSelectableChipGroup(
            options = PriceRange.entries,
            selectedOptions = selectedPrices,
            onSelectToggle = { price ->
                selectedPrices = if (selectedPrices.contains(price)) {
                    selectedPrices - price
                } else {
                    selectedPrices + price
                }
            },
            optionTextSelector = { it.displayName }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            enabled = (selectedStyles.isNotEmpty() && selectedAges.isNotEmpty() && selectedPrices.isNotEmpty()),
            onClick = {
                onSave(UserPreference(gender, selectedStyles, selectedAges, selectedPrices))
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .heightIn(min = 50.dp),
        ) {
            Text("저장")
        }
    }
}
