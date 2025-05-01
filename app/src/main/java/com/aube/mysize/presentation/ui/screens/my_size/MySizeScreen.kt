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
import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.domain.model.size.toUi
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.ui.component.bottomsheet.SizePreviewBottomSheet
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
    bodySizeViewModel: BodySizeViewModel = hiltViewModel(),
    topSizeViewModel: TopSizeViewModel = hiltViewModel(),
    bottomSizeViewModel: BottomSizeViewModel = hiltViewModel(),
    outerSizeViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceSizeViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeSizeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessorySizeViewModel: AccessorySizeViewModel = hiltViewModel(),
    onNavigateToFullDetailByCategory: (SizeCategory) -> Unit,
    onNavigateToFullDetailByBrand: (String) -> Unit,
    onEdit: (SizeCategory, Size) -> Unit,
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

    val bodySizes by bodySizeViewModel.sizes.collectAsState()
    val topSizes by topSizeViewModel.sizes.collectAsState()
    val bottomSizes by bottomSizeViewModel.sizes.collectAsState()
    val outerSizes by outerSizeViewModel.sizes.collectAsState()
    val onePieceSizes by onePieceSizeViewModel.sizes.collectAsState()
    val shoeSizes by shoeSizeViewModel.sizes.collectAsState()
    val accessorySizes by accessorySizeViewModel.sizes.collectAsState()

    val bodySizeCard = bodySizes.firstOrNull()?.toUi()

    val categoryGroupedData = remember(topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes) {
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
            onEdit = { size ->
                when (size) {
                    is TopSize -> onEdit(SizeCategory.TOP, size)
                    is BottomSize -> onEdit(SizeCategory.BOTTOM, size)
                    is OuterSize -> onEdit(SizeCategory.OUTER, size)
                    is OnePieceSize -> onEdit(SizeCategory.ONE_PIECE, size)
                    is ShoeSize -> onEdit(SizeCategory.SHOE, size)
                    is AccessorySize -> onEdit(SizeCategory.ACCESSORY, size)
                }
            },
            onDelete = { size ->
                when (size) {
                    is TopSize -> topSizeViewModel.delete(size)
                    is BottomSize -> bottomSizeViewModel.delete(size)
                    is OuterSize -> outerSizeViewModel.delete(size)
                    is OnePieceSize -> onePieceSizeViewModel.delete(size)
                    is ShoeSize -> shoeSizeViewModel.delete(size)
                    is AccessorySize -> accessorySizeViewModel.delete(size)
                }
                selectedSize = null
            },
            onDismiss = { selectedSize = null },
        )
    }

    LaunchedEffect(isBodySizeCardStickyFlow.value) {
        isBodySizeCardSticky = isBodySizeCardStickyFlow.value
    }

    MySizeContent(
        isFullDetailMode = false,
        listState = listState,
        onBodySizeEdit = { id ->
            val bodySize = bodySizeViewModel.getSizeById(id)
            if (bodySize != null) {
                onEdit(SizeCategory.BODY, bodySize)
            }
        },
        onBodySizeDelete = { id ->
            val bodySize = bodySizeViewModel.getSizeById(id)
            if (bodySize != null) {
                bodySizeViewModel.delete(bodySize)
            }
        },
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
        categoryGroupedData = categoryGroupedData,
        onNavigateToFullDetailByCategory = onNavigateToFullDetailByCategory,
        brandGroupedData = brandGroupedData,
        onNavigateToFullDetailByBrand = onNavigateToFullDetailByBrand
    )
}

