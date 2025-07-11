package com.aube.mysize.presentation.ui.screens.recommend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.RecommendedSizeResult
import com.aube.mysize.presentation.ui.component.chip_tap.MSTabRow
import com.aube.mysize.presentation.ui.component.dialog.CropGuideDialog
import com.aube.mysize.presentation.ui.component.ocr.SizeOcrCard
import com.aube.mysize.presentation.ui.screens.recommend.component.EmptyBodySize
import com.aube.mysize.presentation.ui.screens.recommend.ocr_recommend.RecommendSizeFromImage
import com.aube.mysize.presentation.ui.screens.recommend.shop_recommend.RecommendedShopsView
import com.aube.mysize.presentation.ui.screens.recommend.shop_recommend.UserPreferenceScreen
import com.aube.mysize.presentation.viewmodel.settings.SettingsViewModel
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel
import com.aube.mysize.utils.recommend.buildAccessorySizeReference
import com.aube.mysize.utils.recommend.buildBottomSizeReference
import com.aube.mysize.utils.recommend.buildOnePieceSizeReference
import com.aube.mysize.utils.recommend.buildOuterSizeReference
import com.aube.mysize.utils.recommend.buildShoeSizeReference
import com.aube.mysize.utils.recommend.buildTopSizeReference

@Composable
fun RecommendScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    sizeViewModel: SizeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onAddNewBodySize: () -> Unit,
    onEditBodySize: (BodySize) -> Unit,
) {
    val allSizes by sizeViewModel.sizes.collectAsState()
    val bodySize = (allSizes[SizeCategory.BODY]?.firstOrNull() as? BodySize)

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    var selectedCategory by rememberSaveable { mutableStateOf<SizeCategory?>(null) }

    var recommendShopStep by rememberSaveable { mutableStateOf(1) }
    val userPreference by settingsViewModel.userPreference.collectAsState()

    LaunchedEffect(userPreference) {
        userPreference?.let {
            recommendShopStep = 2
        }
    }

    var showGuideDialog by remember { mutableStateOf(false) }

    if (bodySize == null) {
        EmptyBodySize(onAddNewBodySize)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            MSTabRow(
                tabs = listOf(
                    stringResource(R.string.recommend_tab_shop),
                    stringResource(R.string.recommend_tab_size)
                ),
                selectedTabIndex = selectedTabIndex,
                onTabSelected = {
                    selectedTabIndex = it
                    selectedCategory = null
                }
            )

            HorizontalDivider(thickness = 0.5.dp)

            val recommendedResult: List<RecommendedSizeResult> = remember(selectedCategory) {
                when (selectedCategory) {
                    SizeCategory.BODY -> listOf(
                        buildTopSizeReference(bodySize),
                        buildBottomSizeReference(bodySize),
                        buildOuterSizeReference(bodySize),
                        buildOnePieceSizeReference(bodySize),
                        buildShoeSizeReference(bodySize),
                        buildAccessorySizeReference(bodySize)
                    )
                    null -> listOf(RecommendedSizeResult.Failure("NULL"))
                    else -> emptyList()
                }
            }

            if (selectedTabIndex == 0) {
                if (recommendShopStep == 1) {
                    UserPreferenceScreen(
                        gender = Gender.entries.find { it.displayName == bodySize.gender.displayName } ?: Gender.UNISEX,
                        onSave = { userPref ->
                            settingsViewModel.saveUserPreference(userPref)
                            recommendShopStep = 2
                        }
                    )
                } else if (recommendShopStep == 2) {
                    RecommendedShopsView(
                        userPreference = userPreference,
                        bodySize = bodySize
                    ) {
                        settingsViewModel.saveUserPreference(it)
                    }
                }
            } else if (selectedTabIndex == 1) {
                when (selectedCategory) {
                    null ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                SizeOcrCard(
                                    onGuideButtonClick = { showGuideDialog = true },
                                    onRecommendationButtonClick = {
                                        /*selectedCategory = SizeCategory.BODY*/
                                    }
                                )
                            }
                        }

                    else -> // SizeOcrCard 바로 가기 눌렀을 때
                        RecommendSizeFromImage(
                            snackbarHostState = snackbarHostState,
                            recommendedResult = recommendedResult,
                            backHandler = {
                                selectedCategory = null
                            },
                            onEditBodySize = { onEditBodySize(bodySize) }
                        )
                }
            }
        }
    }

    if (showGuideDialog) {
        CropGuideDialog { showGuideDialog = false }
    }
}
