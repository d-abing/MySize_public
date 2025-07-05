package com.aube.mysize.presentation.ui.screens.recommend.type_recommend

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.constants.AnimationConstants
import com.aube.mysize.presentation.model.recommend.RecommendedSizeResult
import com.aube.mysize.presentation.model.recommend.SizeDetail
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.presentation.ui.screens.recommend.component.EmptyShoeSize
import kotlinx.coroutines.launch

@Composable
fun RecommendedSizesView(
    recommendedResult: List<RecommendedSizeResult>,
    backHandler: () -> Unit,
    onEditBodySize: () -> Unit
) {
    BackHandler {
        backHandler()
    }

    RecommendedSizeDetailList(recommendedResult.first(), onEditBodySize)
}

@Composable
fun RecommendedSizeDetailList(
    result: RecommendedSizeResult,
    onEditBodySize: () -> Unit
) {

    when (result) {
        is RecommendedSizeResult.Success -> {
            val category = result.category
            val typeToSizeMap = result.typeToSizeMap
            val mostSelectedLabel = result.mostSelectedLabel

            val coroutineScope = rememberCoroutineScope()
            val listState = rememberLazyListState()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                state = listState
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Animation(
                            name = AnimationConstants.STAR_EFFECT_ANIMATION,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Text(
                            text = stringResource(R.string.text_best_fitting_size),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Animation(
                            name = AnimationConstants.BOTTOM_ARROW_ANIMATION,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .height(50.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(1)
                                    }
                                }
                        )
                    }
                }

                val typeSizeList = typeToSizeMap.entries.toList()

                items(typeSizeList.chunked(2)) { chunk ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        chunk.forEach { (type, sizeDetail) ->
                            Box(modifier = Modifier.weight(1f)) {
                                RecommendedSizeCard(category, type, sizeDetail, mostSelectedLabel[type])
                            }
                        }
                        if (chunk.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
        is RecommendedSizeResult.Failure -> {
            EmptyShoeSize(result, onEditBodySize)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendedSizeCard(
    category: SizeCategory,
    type: String,
    sizeDetail: SizeDetail,
    mostSelectedLabel: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = type,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(12.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    sizeDetail.measurements.forEach { (label, value) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            Text(
                                text =
                                    if (category != SizeCategory.SHOE) "${value}cm"
                                    else "${value}mm",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            /*mostSelectedLabel?.let { label ->
                Column {
                    Text(
                        text = "📌 비슷한 체형의 사용자 사이즈",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }*/
        }
    }
}
