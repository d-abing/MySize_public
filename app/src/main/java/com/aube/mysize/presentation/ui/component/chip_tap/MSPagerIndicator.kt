package com.aube.mysize.presentation.ui.component.chip_tap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            Icon(
                imageVector = if (index == currentPage) Icons.Filled.Circle else Icons.Outlined.Circle,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "indicator: $index",
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PagerIndicatorPreview() {
    MySizeTheme {
        PagerIndicator(
            pageCount = 5,
            currentPage = 2,
            modifier = Modifier.padding(16.dp)
        )
    }
}
