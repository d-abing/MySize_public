package com.aube.mysize.presentation.ui.screens.recommend.ocr_recommend

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.model.recommend.RecommendedSizeResult
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun RecommendSizeFromImage(
    snackbarHostState: SnackbarHostState,
    recommendedResult: List<RecommendedSizeResult>,
    backHandler: () -> Unit,
    onEditBodySize: () -> Unit,
) {
    var selectedCategory by rememberSaveable { mutableStateOf<SizeCategory?>(null) }
    var selectedStep by rememberSaveable { mutableIntStateOf(1) }

    BackHandler {
        backHandler()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        when (selectedStep) {
            1 ->
                RecommendSizeFromImageStepOne(
                    onCategoryClick = {
                        selectedCategory = it
                        selectedStep = 2
                    }
                )
            2 ->
                RecommendSizeFromImageStepTwo(
                    selectedStep = selectedStep,
                    snackbarHostState = snackbarHostState,
                    selectedCategory = selectedCategory,
                    recommendedResult = recommendedResult,
                    backHandler = {
                        selectedCategory = null
                        selectedStep = 1
                    },
                    onEditBodySize = onEditBodySize
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendSizeFromImagePreview() {
    MySizeTheme {
        RecommendSizeFromImage(
            snackbarHostState = remember { SnackbarHostState() },
            recommendedResult = emptyList(),
            backHandler = {},
            onEditBodySize = {}
        )
    }
}


