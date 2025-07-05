package com.aube.mysize.presentation.ui.component.chip_tap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.ui.theme.MySizeTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T : Any> MultiSelectableChipGroup(
    options: List<T>,
    selectedOptions: List<T>,
    onSelectToggle: (T) -> Unit,
    optionTextSelector: (T) -> String = { it.toString() },
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            val label = optionTextSelector(option)
            val isSelected = selectedOptions.contains(option)

            val canDeselect = selectedOptions.size > 1 || !isSelected

            FilterChip(
                selected = isSelected,
                onClick = {
                    if (canDeselect) onSelectToggle(option)
                },
                label = {
                    Text(
                        text = label,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE0E0E0),
                    selectedLabelColor = Color.DarkGray,
                    containerColor = Color.White,
                    labelColor = Color.DarkGray
                ),
                modifier = Modifier
                    .heightIn(min = 28.dp, max = 36.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultiSelectableChipGroupPreview() {
    MySizeTheme {
        var selected by remember { mutableStateOf(listOf("M", "L")) }

        MultiSelectableChipGroup(
            options = listOf("S", "M", "L", "XL", "XXL"),
            selectedOptions = selected,
            onSelectToggle = { option ->
                selected = if (selected.contains(option)) {
                    selected - option
                } else {
                    selected + option
                }
            },
        )
    }
}

