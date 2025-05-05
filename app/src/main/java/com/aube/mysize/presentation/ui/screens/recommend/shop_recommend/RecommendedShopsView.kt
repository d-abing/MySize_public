package com.aube.mysize.presentation.ui.screens.recommend.shop_recommend

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.BodyType
import com.aube.mysize.presentation.model.RecommendedShop
import com.aube.mysize.presentation.model.RecommendedShopResult
import com.aube.mysize.presentation.model.UserPreference
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.presentation.ui.screens.recommend.component.UserPreferenceBottomSheet
import com.aube.mysize.utils.recommend.recommendShops
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedShopsView(
    userPreference: UserPreference?,
    bodySize: BodySize,
    onPreferenceChange: (UserPreference) -> Unit,
) {
    if (userPreference == null) return
    val result: RecommendedShopResult = remember(userPreference, bodySize) { recommendShops(bodySize, userPreference) }

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var preferenceType by remember { mutableStateOf("") }


    if(showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = null,
        ) {
            UserPreferenceBottomSheet(
                currentPreference = userPreference,
                preferenceType = preferenceType,
                onSave = { updated ->
                    onPreferenceChange(updated)
                    showBottomSheet = false
                },
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        item {
            AssistChip(
                onClick = {
                    showBottomSheet = true
                    preferenceType = "Gender"
                },
                label = {
                    Text(
                        text = userPreference.gender.displayName,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                colors = AssistChipDefaults.assistChipColors().copy(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(0.3f)
                )
            )
        }

        userPreference.styles.forEach { style ->
            item {
                AssistChip(
                    onClick = {
                        showBottomSheet = true
                        preferenceType = "Styles"
                    },
                    label = {
                        Text(
                            text = style.displayName,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.3f)
                    )
                )
            }
        }

        userPreference.ageGroups.forEach { ageGroup ->
            item {
                AssistChip(
                    onClick = {
                        showBottomSheet = true
                        preferenceType = "AgeGroup"
                    },
                    label = {
                        Text(
                            text = ageGroup.displayName,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors().copy(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.3f)
                    )
                )
            }
        }

        userPreference.priceRanges.forEach { priceRange ->
            item {
                AssistChip(
                    onClick = {
                        showBottomSheet = true
                        preferenceType = "PriceRange"
                    },
                    label = {
                        Text(
                            text = priceRange.displayName,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors().copy(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(0.3f)
                    )
                )
            }
        }
    }

    when (result) {
        is RecommendedShopResult.Success -> {
            val recommendedShop = result.recommendedShops
            val (matchedBodyShops, allBodyShops) = recommendedShop.partition {
                BodyType.ALL !in it.body
            }

            if (recommendedShop.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "추천 가능한 쇼핑몰이 없어요!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
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
                                name = "star_effect.json",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )
                            Text(
                                text = "사용자님께 가장 적합한 쇼핑몰이에요!",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Animation(
                                name = "bottom_arrow.json",
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

                    if (matchedBodyShops.isNotEmpty()) {
                        item {
                            Text(
                                text = "내 체형에 딱 맞는 쇼핑몰",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 8.dp)
                            )
                        }
                        items(matchedBodyShops) { shop ->
                            RecommendedShopCard(shop)
                        }
                    }

                    if (allBodyShops.isNotEmpty()) {
                        item {
                            Text(
                                text = "모든 체형을 위한 쇼핑몰",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp)
                            )
                        }
                        items(allBodyShops) { shop ->
                            RecommendedShopCard(shop)
                        }
                    }
                }
            }
        }

        is RecommendedShopResult.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Animation(
                        name = "bubble_effect.json",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                    Text(
                        text = result.message,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendedShopCard(shop: RecommendedShop) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(0.2f)),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(shop.shopUrl))
            context.startActivity(intent)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = shop.shopName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = shop.shopUrl,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
