package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SelectableChipGroup(
    options: List<String>,
    selectedOption: String,
    onSelect: (String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                    selectedContainerColor = Color(0xFFE0E0E0),             // 선택 시 회색 배경
                    selectedLabelColor = Color.DarkGray,               // 선택 시 텍스트
                    containerColor = Color.White,              // 비선택 배경 (밝은 회색)
                    labelColor = Color.DarkGray                      // 비선택 텍스트
                ),
                modifier = Modifier.height(28.dp),

            )
        }
    }
}
