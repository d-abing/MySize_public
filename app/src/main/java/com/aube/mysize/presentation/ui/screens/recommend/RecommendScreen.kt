package com.aube.mysize.presentation.ui.screens.recommend

import RecommendedSizeGrid
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.RecommendedSizeResult
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.ui.component.chip_tap.MSTabRow
import com.aube.mysize.presentation.ui.component.guide.GuideDialog
import com.aube.mysize.presentation.ui.component.ocr.SizeOcrCard
import com.aube.mysize.presentation.ui.screens.recommend.component.EmptyBodySize
import com.aube.mysize.presentation.ui.screens.recommend.ocr_recommend.RecommendSizeFromImage
import com.aube.mysize.presentation.ui.screens.recommend.type_recommend.RecommendedSizesView
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel
import com.aube.mysize.utils.recommendAccessorySizes
import com.aube.mysize.utils.recommendBottomSizes
import com.aube.mysize.utils.recommendOnePieceSizes
import com.aube.mysize.utils.recommendOuterSizes
import com.aube.mysize.utils.recommendShoeSizes
import com.aube.mysize.utils.recommendTopSizes

@Composable
fun RecommendScreen(
    snackbarHostState: SnackbarHostState,
    onAddNewBodySize: () -> Unit,
    bodySizeViewModel: BodySizeViewModel = hiltViewModel(),
) {
    val bodySize by bodySizeViewModel.sizes.collectAsState()

    RecommendScreen(
        snackbarHostState = snackbarHostState,
        onAddNewBodySize = onAddNewBodySize,
        bodySize = bodySize.firstOrNull()
    )
}
@Composable
fun RecommendScreen(
    snackbarHostState: SnackbarHostState,
    onAddNewBodySize: () -> Unit,
    bodySize: BodySize?,
) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    var selectedCategory by rememberSaveable { mutableStateOf<SizeCategory?>(null) }

    var showGuideDialog by remember { mutableStateOf(false) }

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
                onTabSelected = {
                    selectedTabIndex = it
                    selectedCategory = null
                }
            )

            HorizontalDivider(thickness = 0.5.dp)

            val recommendedResult: List<RecommendedSizeResult> = remember(selectedCategory) {
                when (selectedCategory) {
                    SizeCategory.TOP -> listOf(recommendTopSizes(bodySize))
                    SizeCategory.BOTTOM -> listOf(recommendBottomSizes(bodySize))
                    SizeCategory.OUTER -> listOf(recommendOuterSizes(bodySize))
                    SizeCategory.ONE_PIECE -> listOf(recommendOnePieceSizes(bodySize))
                    SizeCategory.SHOE -> listOf(recommendShoeSizes(bodySize))
                    SizeCategory.ACCESSORY -> listOf(recommendAccessorySizes(bodySize))
                    SizeCategory.BODY -> listOf(
                        recommendTopSizes(bodySize),
                        recommendBottomSizes(bodySize),
                        recommendOuterSizes(bodySize),
                        recommendOnePieceSizes(bodySize),
                        recommendShoeSizes(bodySize),
                        recommendAccessorySizes(bodySize)
                    )
                    null -> listOf(RecommendedSizeResult.Failure("NULL"))
                }
            }

            if (selectedTabIndex == 0) {
                
                when (selectedCategory) {
                    null ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                RecommendedSizeGrid { sizeCategory ->
                                    selectedCategory = sizeCategory
                                }
                            }
                            item {
                                SizeOcrCard(
                                    onGuideButtonClick = { showGuideDialog = true },
                                    onRecommendationButtonClick = {
                                        selectedCategory = SizeCategory.BODY
                                    }
                                )
                            }
                        }

                    SizeCategory.BODY -> // SizeOcrCard 바로 가기 눌렀을 때
                        RecommendSizeFromImage(
                            snackbarHostState = snackbarHostState,
                            recommendedResult = recommendedResult as List<RecommendedSizeResult.Success>,
                        ) {
                            selectedCategory = null
                        }

                    else ->
                        RecommendedSizesView(
                            recommendedResult as List<RecommendedSizeResult.Success>,
                        ){
                            selectedCategory = null
                        }
                }

            } else if (selectedTabIndex == 1) {
                /* TODO */
            }
        }
    }


    if (showGuideDialog) {
        GuideDialog(
            onDismiss = { showGuideDialog = false },
            title = "사이즈 표 자르기 가이드",
            content = {
                Text(
                    text = "표 영역만 자르면 사이즈 추출 정확도가 올라갑니다!😎\n" +
                            "제목이나 단위, 부가설명 등은 제외해주세요. 🙅‍♂️🙅",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 12.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.size_guide),
                    contentDescription = "사이즈표 크롭 가이드",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}
