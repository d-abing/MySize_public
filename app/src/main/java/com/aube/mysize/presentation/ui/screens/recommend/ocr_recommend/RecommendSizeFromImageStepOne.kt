package com.aube.mysize.presentation.ui.screens.recommend.ocr_recommend

import RecommendedSizeTab
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun RecommendSizeFromImageStepOne(
    onCategoryClick: (SizeCategory) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 40.dp),
            text = stringResource(R.string.text_select_category_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        RecommendedSizeTab(
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

@Preview(showBackground = true)
@Composable
fun RecommendSizeFromImageStepOnePreview() {
    MySizeTheme {
        RecommendSizeFromImageStepOne(
            onCategoryClick = { /* no-op */ }
        )
    }
}