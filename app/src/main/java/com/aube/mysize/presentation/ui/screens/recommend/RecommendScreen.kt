package com.aube.mysize.presentation.ui.screens.recommend

import RecommendedSizeGrid
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.ui.component.chip_tap.MSTabRow
import com.aube.mysize.presentation.ui.screens.recommend.component.EmptyBodySize
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel

@Composable
fun RecommendScreen(
    onAddNewBodySize: () -> Unit,
    bodySizeViewModel: BodySizeViewModel = hiltViewModel(),
) {
    val bodySize by bodySizeViewModel.sizes.collectAsState()

    RecommendScreen(
        onAddNewBodySize = onAddNewBodySize,
        bodySize = bodySize.firstOrNull()
    )
}
@Composable
fun RecommendScreen(
    onAddNewBodySize: () -> Unit,
    bodySize: BodySize?,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedType by remember { mutableStateOf<SizeCategory?>(null) }

    if (bodySize == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            EmptyBodySize(onAddNewBodySize)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            MSTabRow(
                tabs = listOf("추천 사이즈", "추천 쇼핑몰"),
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            HorizontalDivider(thickness = 0.5.dp)

            if (selectedTabIndex == 0) {


                if (selectedType == null) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item {
                            RecommendedSizeGrid { sizeCategory ->
                                selectedType = sizeCategory
                            }
                        }
                    }
                } else {
                    /* TODO */
                }

            } else if (selectedTabIndex == 1) {
                /* TODO */
            }
        }
    }
}
