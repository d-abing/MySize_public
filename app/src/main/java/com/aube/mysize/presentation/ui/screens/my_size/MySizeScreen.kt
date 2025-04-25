package com.aube.mysize.presentation.ui.screens.my_size

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.ClothesSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.domain.model.size.toUi
import com.aube.mysize.presentation.model.BodySizeCardUiModel
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.ui.component.mysize.bottomsheet.SizePreviewBottomSheet
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore.saveIsBodySizeCardSticky
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel
import kotlinx.coroutines.launch

@Composable
fun MySizeScreen(
    bodyViewModel: BodySizeViewModel = hiltViewModel(),
    topViewModel: TopSizeViewModel = hiltViewModel(),
    bottomViewModel: BottomSizeViewModel = hiltViewModel(),
    outerViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessoryViewModel: AccessorySizeViewModel = hiltViewModel(),
    onNavigateToFullDetailByCategory: (SizeCategory) -> Unit,
    onNavigateToFullDetailByBrand: (String) -> Unit
) {
    val bodySizes by bodyViewModel.sizes.collectAsState()
    val topSizes by topViewModel.sizes.collectAsState()
    val bottomSizes by bottomViewModel.sizes.collectAsState()
    val outerSizes by outerViewModel.sizes.collectAsState()
    val onePieceSizes by onePieceViewModel.sizes.collectAsState()
    val shoeSizes by shoeViewModel.sizes.collectAsState()
    val accessorySizes by accessoryViewModel.sizes.collectAsState()

    val bodySizeCard = bodySizes.firstOrNull()?.toUi()

    MySizeScreen(
        bodySizeCard = bodySizeCard,
        topSizes = topSizes,
        bottomSizes = bottomSizes,
        outerSizes = outerSizes,
        onePieceSizes = onePieceSizes,
        shoeSizes = shoeSizes,
        accessorySizes = accessorySizes,
        onNavigateToFullDetailByCategory =  onNavigateToFullDetailByCategory,
        onNavigateToFullDetailByBrand = onNavigateToFullDetailByBrand
    )
}

@Composable
fun MySizeScreen(
    bodySizeCard: BodySizeCardUiModel?,
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    onePieceSizes: List<OnePieceSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onNavigateToFullDetailByCategory: (SizeCategory) -> Unit,
    onNavigateToFullDetailByBrand: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val isBodySizeCardStickyFlow = SettingsDataStore.getIsBodySizeCardSticky(context).collectAsState(initial = false)
    var isBodySizeCardSticky by remember { mutableStateOf(isBodySizeCardStickyFlow.value) }

    var searchQuery by remember { mutableStateOf("") }
    val brandSectionIndices = remember { mutableStateMapOf<String, Int>() }
    val highlightedBrand = remember { mutableStateOf<String?>(null) }

    val listState = rememberLazyListState()

    var selectedSize by remember { mutableStateOf<ClothesSize?>(null) }

    val typeGroupedData = remember(topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes) {
        buildCategoryGroupedSizeData(
            topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes) { selectedSize = it }
    }

    val brandGroupedData = remember(topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes) {
        buildBrandGroupedSizeData(
            topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes) { selectedSize = it }
    }

    if (selectedSize != null) {
        SizePreviewBottomSheet(
            size = selectedSize!!,
            onDismiss = { selectedSize = null },
        )
    }

    LaunchedEffect(isBodySizeCardStickyFlow.value) {
        isBodySizeCardSticky = isBodySizeCardStickyFlow.value
    }

    MySizeContent(
        isFullDetailMode = false,
        listState = listState,
        isBodySizeCardSticky = isBodySizeCardSticky,
        onBodySizeCardStickyChanged = {
            isBodySizeCardSticky = !isBodySizeCardSticky
            coroutineScope.launch {
                saveIsBodySizeCardSticky(context, isBodySizeCardSticky)
            }
        },
        bodySizeCard = bodySizeCard,
        coroutineScope = coroutineScope,
        brandSectionIndices = brandSectionIndices,
        highlightedBrand = highlightedBrand,
        searchQuery = searchQuery,
        onSearchQueryChanged = { searchQuery = it },
        categoryGroupedData = typeGroupedData,
        onNavigateToFullDetailByCategory = onNavigateToFullDetailByCategory,
        brandGroupedData = brandGroupedData,
        onNavigateToFullDetailByBrand = onNavigateToFullDetailByBrand
    )
}

