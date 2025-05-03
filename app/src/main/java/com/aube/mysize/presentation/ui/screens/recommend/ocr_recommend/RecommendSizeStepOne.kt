package com.aube.mysize.presentation.ui.screens.recommend.ocr_recommend

import RecommendedSizeGrid
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.SizeCategory

@Composable
fun RecommendSizeStepOne(
    onCategoryClick: (SizeCategory) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 40.dp),
            text = "카테고리 선택",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        RecommendedSizeGrid(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(),
            isRecommendSizeStep = true,
            maxItemsInEachRow = 2,
            itemHeight = 120.dp,
            onClick = onCategoryClick
        )
    }
}
