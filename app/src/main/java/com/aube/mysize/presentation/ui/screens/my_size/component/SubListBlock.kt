package com.aube.mysize.presentation.ui.screens.my_size.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.SizeContentUiModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubListBlock(
    typeName: String,
    contents: List<SizeContentUiModel>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val fontScale = LocalDensity.current.fontScale
        val itemsPerRow = if (fontScale > 1.2f) 4 else 5
        val bottomPadding = if (fontScale > 1.2f) 16.dp else 12.dp

        Text(
            text = typeName,
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(bottom = bottomPadding)
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = itemsPerRow
        ) {
            contents.chunked(itemsPerRow).forEach { rowItems ->
                rowItems.forEach { item ->
                    SizeButton(
                        title = item.title,
                        sizeLabel = item.sizeLabel,
                        onClick = item.onClick,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                }
                repeat(itemsPerRow - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
