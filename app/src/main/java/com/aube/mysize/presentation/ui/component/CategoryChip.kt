package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.SizeCategory

@Composable
fun CategoryChip(
    categories: List<SizeCategory>,
    selectedCategory: SizeCategory?,
    onClick: (SizeCategory) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        val isBodyIncluded = categories.contains(SizeCategory.BODY)
        val allCategories = SizeCategory.entries.filter {
            categories.contains(SizeCategory.BODY) || it != SizeCategory.BODY
        }

        items(allCategories) { category ->
            val enabled = categories.contains(category)
            FilterChip(
                enabled = enabled,
                selected = selectedCategory == category,
                onClick = { if (enabled) onClick(category) },
                label = {
                    Text(
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        text = category.label
                    )
                },
                shape = RoundedCornerShape(50),
                colors =
                    if (isBodyIncluded) {
                        FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.Black,
                            containerColor = MaterialTheme.colorScheme.secondary,
                            selectedLabelColor = Color.White,
                            labelColor = Color.Black)
                    } else {
                        FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.secondary,
                            containerColor = MaterialTheme.colorScheme.secondary,
                            selectedLabelColor = Color.Black,
                            labelColor = Color.Black
                        )
                   },
                border = null,
                modifier = Modifier.height(36.dp)
            )
        }
    }
}