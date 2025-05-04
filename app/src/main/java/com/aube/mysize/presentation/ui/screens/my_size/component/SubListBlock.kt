package com.aube.mysize.presentation.ui.screens.my_size.component

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.SizeContentUiModel
import com.aube.mysize.presentation.ui.component.button.SizeButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubListBlock(
    typeName: String,
    contents: List<SizeContentUiModel>,
    maxColumnCount: Int = 5
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val fontScale = LocalDensity.current.fontScale
        var itemsPerRow = if (fontScale > 1.2f) maxColumnCount - 1 else maxColumnCount
        val bottomPadding = if (itemsPerRow <= 4) 16.dp else 12.dp

        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        if (isLandscape) {
            itemsPerRow *= 2
        }

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
