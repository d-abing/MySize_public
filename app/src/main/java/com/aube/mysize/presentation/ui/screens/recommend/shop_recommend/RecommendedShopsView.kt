package com.aube.mysize.presentation.ui.screens.recommend.shop_recommend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.recommend.AgeGroup
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.PriceRange
import com.aube.mysize.presentation.model.recommend.RecommendedShopResult
import com.aube.mysize.presentation.model.recommend.Style
import com.aube.mysize.presentation.model.recommend.UserPreference
import com.aube.mysize.presentation.ui.component.ad.BannerAdView
import com.aube.mysize.presentation.viewmodel.recommend.RecommendViewModel
import com.aube.mysize.ui.theme.MySizeTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedShopsView(
    recommendViewModel: RecommendViewModel = hiltViewModel(),
    userPreference: UserPreference?,
    bodySize: BodySize,
    onPreferenceChange: (UserPreference) -> Unit,
) {
    if (userPreference == null) return

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var preferenceType by remember { mutableStateOf("") }

    val shopResult by recommendViewModel.recommendedShops.collectAsState()

    LaunchedEffect(bodySize, userPreference) {
        recommendViewModel.loadRecommendations(
            bodySize = bodySize,
            preference = userPreference
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (showBottomSheet) {
            PreferenceBottomSheet(
                sheetState = sheetState,
                userPreference = userPreference,
                preferenceType = preferenceType,
                onDismiss = { showBottomSheet = false },
                onSave = {
                    onPreferenceChange(it)
                    showBottomSheet = false
                }
            )
        }

        PreferenceChipRow(
            userPreference = userPreference,
            onChipClick = {
                preferenceType = it
                showBottomSheet = true
            }
        )

        Box(modifier = Modifier.weight(1f)) {
            when (shopResult) {
                is RecommendedShopResult.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is RecommendedShopResult.Success -> {
                    RecommendedShopList(
                        result = shopResult as RecommendedShopResult.Success,
                        listState = listState,
                        coroutineScope = coroutineScope
                    )
                }

                is RecommendedShopResult.Failure -> {
                    RecommendationFailure(message = (shopResult as RecommendedShopResult.Failure).message)
                }
            }
        }

        BannerAdView(adUnitId = stringResource(R.string.ad_unit_id_banner))
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendedShopsViewPreview() {
    MySizeTheme {
        RecommendedShopsView(
            userPreference = UserPreference(
                gender = Gender.FEMALE,
                styles = listOf(Style.CASUAL),
                ageGroups = listOf(AgeGroup.TWENTIES),
                priceRanges = listOf(PriceRange.MEDIUM)
            ),
            bodySize = BodySize(
                id = "1",
                uid = "user1",
                gender = Gender.FEMALE,
                height = 162.0f,
                weight = 48.0f,
                chest = 82.0f,
                waist = 63.0f,
                hip = 90.0f,
                neck = 30.0f,
                shoulder = 39.0f,
                arm = 30.0f,
                leg = 30.0f,
                footLength = 270.0f,
                footWidth = 30.0f,
                date = LocalDateTime.now(),
            ),
            onPreferenceChange = {}
        )
    }
}