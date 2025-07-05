package com.aube.mysize.presentation.ui.screens.recommend.shop_recommend

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.recommend.RecommendedShop
import com.aube.mysize.presentation.constants.AnimationConstants
import com.aube.mysize.presentation.model.recommend.AgeGroup
import com.aube.mysize.presentation.model.recommend.BodyType
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.PriceRange
import com.aube.mysize.presentation.model.recommend.RecommendedShopResult
import com.aube.mysize.presentation.model.recommend.Style
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.ui.theme.MySizeTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope as CoroutineScope1

@Composable
fun RecommendedShopList(
    result: RecommendedShopResult.Success,
    listState: LazyListState,
    coroutineScope: CoroutineScope1
) {
    val (matched, others) = result.recommendedShops.partition { BodyType.ALL !in it.body }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Animation(
                    name = AnimationConstants.STAR_EFFECT_ANIMATION,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                Text(
                    text = stringResource(R.string.text_most_suitable_shop),
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
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            coroutineScope.launch { listState.animateScrollToItem(1) }
                        }
                )
            }
        }

        if (matched.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.text_perfect_fit_shop),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                )
            }
            items(matched) { RecommendedShopCard(it) }
        }

        if (others.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.text_shop_for_all_bodies),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp)
                )
            }
            items(others) { RecommendedShopCard(it) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendedShopListPreview() {
    val dummyResult = RecommendedShopResult.Success(
        recommendedShops = listOf(
            RecommendedShop(
                shopName = "핏온미",
                shopUrl = "https://fitonme.example.com",
                styles = listOf(Style.CASUAL),
                ageGroup = listOf(AgeGroup.TWENTIES),
                priceRange = PriceRange.MEDIUM,
                gender = Gender.FEMALE,
                body = listOf(BodyType.SLIM_AVERAGE)
            ),
            RecommendedShop(
                shopName = "에브리핏",
                shopUrl = "https://everyfit.example.com",
                styles = listOf(Style.MINIMAL),
                ageGroup = listOf(AgeGroup.THIRTIES),
                priceRange = PriceRange.HIGH,
                gender = Gender.UNISEX,
                body = listOf(BodyType.ALL)
            )
        )
    )

    MySizeTheme {
        // 필요 시 LazyListState, CoroutineScope 직접 생성
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        RecommendedShopList(
            result = dummyResult,
            listState = listState,
            coroutineScope = coroutineScope
        )
    }
}

