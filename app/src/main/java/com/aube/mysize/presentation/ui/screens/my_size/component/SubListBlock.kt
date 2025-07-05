package com.aube.mysize.presentation.ui.screens.my_size.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.size.SizeContentUiModel
import com.aube.mysize.presentation.ui.component.button.SizeButton
import com.aube.mysize.ui.theme.MySizeTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubListBlock(
    typeName: String? = null,
    contents: List<SizeContentUiModel>,
    maxColumnCount: Int = 5
) {
    val fontScale = LocalDensity.current.fontScale
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val itemsPerRow = remember(fontScale, configuration.orientation) {
        var count = if (fontScale > 1.2f) maxColumnCount - 1 else maxColumnCount
        if (isLandscape) count *= 2
        count
    }

    val bottomPadding = if (itemsPerRow <= 4) 16.dp else 12.dp

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!typeName.isNullOrBlank()) {
            Text(
                text = typeName,
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = bottomPadding)
            )
        } else {
            Spacer(modifier = Modifier.height(bottomPadding))
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = itemsPerRow
        ) {
            contents.forEach { item ->
                SizeButton(
                    title = item.title,
                    sizeLabel = item.sizeLabel,
                    isSaved = item.isSaved,
                    onClick = item.onClick,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }

            val remainder = contents.size % itemsPerRow
            if (remainder != 0) {
                repeat(itemsPerRow - remainder) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubListBlockPreview() {
    MySizeTheme {
        SubListBlock(
            typeName = "맨투맨",
            contents = List(7) { index ->
                SizeContentUiModel(
                    title = "M",
                    sizeLabel = "${90 + index}cm",
                    isSaved = index % 2 == 0,
                    onClick = {}
                )
            },
            maxColumnCount = 5
        )
    }
}

