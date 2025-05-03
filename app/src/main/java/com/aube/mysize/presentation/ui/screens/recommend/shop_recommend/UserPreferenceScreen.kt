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
import com.aube.mysize.presentation.model.PriceRange
import com.aube.mysize.presentation.model.Style
import com.aube.mysize.presentation.model.UserPreference
import com.aube.mysize.presentation.ui.screens.add_size.component.MultiSelectableChipGroup
import com.aube.mysize.presentation.ui.screens.add_size.component.SingleSelectableChipGroup

@Composable
fun UserPreferenceScreen(
    initialPreference: UserPreference? = null,
    onSave: (UserPreference) -> Unit
) {
    var selectedStyles by rememberSaveable {
        mutableStateOf(initialPreference?.styles ?: emptyList())
    }
    var selectedAge by rememberSaveable {
        mutableStateOf(initialPreference?.ageGroup)
    }
    var selectedPrice by rememberSaveable {
        mutableStateOf(initialPreference?.priceRange)
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
        SingleSelectableChipGroup(
            options = AgeGroup.entries,
            selectedOption = selectedAge,
            onSelect = { selectedAge = it },
            optionTextSelector = { it.displayName }
        )

        Text("선호 금액대를 선택해주세요", style = MaterialTheme.typography.titleMedium)
        SingleSelectableChipGroup(
            options = PriceRange.entries,
            selectedOption = selectedPrice,
            onSelect = { selectedPrice = it },
            optionTextSelector = { it.displayName }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            enabled = (selectedStyles.isNotEmpty() && selectedAge != null && selectedPrice != null),
            onClick = {
                onSave(UserPreference(selectedStyles, selectedAge!!, selectedPrice!!))
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
