package com.aube.mysize.presentation.ui.component.chip_tap

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.model.size.toUi
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun MSCategoryChip(
    addSavedSizeChip: Boolean = false,
    showSavedSizeOnly: Boolean = false,
    onSavedSizeChipClick: () -> Unit = {},
    categories: List<SizeCategory>,
    selectedCategory: SizeCategory?,
    enableColorHighlight: Boolean = true,
    onClick: (SizeCategory) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .then(
                if(addSavedSizeChip)
                    Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                else
                    Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            ),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if(addSavedSizeChip) {
            item {
                MSRainbowBorderChip(
                    label = stringResource(R.string.text_heart),
                    modifier = Modifier
                        .background(
                            if (showSavedSizeOnly) MaterialTheme.colorScheme.secondaryContainer
                            else Color.Transparent
                            , RoundedCornerShape(50)
                        )
                        .clip(RoundedCornerShape(50))
                        .clickable {
                            onSavedSizeChipClick()
                        }
                )
            }
        }

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
                        style = MaterialTheme.typography.bodySmall,
                        text = category.toUi().label
                    )
                },
                shape = RoundedCornerShape(50),
                colors =
                    if (enableColorHighlight) {
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

@Preview(showBackground = true)
@Composable
fun MSCategoryChipPreview() {
    MySizeTheme {
        MSCategoryChip(
            addSavedSizeChip = true,
            showSavedSizeOnly = false,
            onSavedSizeChipClick = {},
            categories = listOf(SizeCategory.TOP, SizeCategory.BOTTOM, SizeCategory.OUTER),
            selectedCategory = SizeCategory.TOP,
            onClick = {}
        )
    }
}