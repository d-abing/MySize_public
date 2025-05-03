package com.aube.mysize.presentation.ui.screens.add_size.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T : Any> MultiSelectableChipGroup(
    options: List<T>,
    selectedOptions: List<T>,
    onSelectToggle: (T) -> Unit,
    optionTextSelector: (T) -> String = { it.toString() },
    onDelete: ((T) -> Unit)? = null,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            val label = optionTextSelector(option)
            val isSelected = selectedOptions.contains(option)

            FilterChip(
                selected = isSelected,
                onClick = { onSelectToggle(option) },
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
                trailingIcon = {
                    if (onDelete != null) {
                        Spacer(Modifier.width(20.dp))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "삭제",
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .clickable { onDelete(option) }
                                .size(10.dp)
                        )
                    }
                }
            )
        }
    }
}
