package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.model.size.SizeSummaryItem
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun SizeSummaryCardList(
    items: List<SizeSummaryItem>,
    dominantColor: Int,
    isOwner: Boolean,
    savedIds: Map<SizeCategory, List<String>>,
    onSave: (SizeCategory, String) -> Unit,
    onUnsave: (SizeCategory, String) -> Unit
) {
    items.forEach { item ->
        val summary = item.summary
        val category = item.category
        val sizeId = item.sizeId
        val isSaved = savedIds[category]?.contains(sizeId) == true

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(dominantColor).copy(alpha = 0.4f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = summary.trimEnd(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onTertiary
                    ),
                    modifier = Modifier.weight(1f)
                )

                if (!isOwner) {
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.label_size_save),
                        tint = if (isSaved) Color(0xFFF22959) else Color.Gray,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                if (isSaved) onUnsave(category, sizeId)
                                else onSave(category, sizeId)
                            }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SizeSummaryCardListPreview() {
    MySizeTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            val dummyItems = listOf(
                SizeSummaryItem(
                    summary = "어깨 42cm, 가슴단면 50cm, 소매 58cm",
                    category = SizeCategory.TOP,
                    sizeId = "top1"
                ),
                SizeSummaryItem(
                    summary = "허리 30인치, 기장 102cm",
                    category = SizeCategory.BOTTOM,
                    sizeId = "bottom1"
                )
            )

            val dummySavedIds = mapOf(
                SizeCategory.TOP to listOf("top1"),
                SizeCategory.BOTTOM to emptyList()
            )

            SizeSummaryCardList(
                items = dummyItems,
                dominantColor = 0xFF90CAF9.toInt(), // 연파랑
                isOwner = false,
                savedIds = dummySavedIds,
                onSave = { _, _ -> },
                onUnsave = { _, _ -> }
            )
        }
    }
}
