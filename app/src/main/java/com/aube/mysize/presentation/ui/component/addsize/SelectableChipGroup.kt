package com.aube.mysize.presentation.ui.component.addsize

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
fun SelectableChipGroup(
    options: List<String>,
    selectedOption: String,
    onSelect: (String) -> Unit,
    onDelete: ((String) -> Unit)? = null,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            FilterChip(
                selected = selectedOption == option,
                onClick = { onSelect(option) },
                label = {
                    Text(
                        text = option,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE0E0E0),
                    selectedLabelColor = Color.DarkGray,
                    containerColor = Color.White,
                    labelColor = Color.DarkGray
                ),
                modifier = Modifier.height(28.dp),
                trailingIcon = {
                    if (onDelete != null && option != "기타 브랜드") {
                        Spacer(Modifier.width(20.dp))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "삭제",
                            tint = Color.DarkGray,
                            modifier = Modifier.clickable { onDelete(option) }
                                .size(10.dp)
                        )
                    }
                }
            )
        }
    }
}
