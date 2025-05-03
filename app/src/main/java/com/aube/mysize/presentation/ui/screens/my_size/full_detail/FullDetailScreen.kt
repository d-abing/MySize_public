package com.aube.mysize.presentation.ui.screens.my_size.full_detail

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.ClothesSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.model.SizeContentUiModel
import com.aube.mysize.presentation.ui.screens.my_size.MySizeContent
import com.aube.mysize.presentation.ui.screens.my_size.component.SizePreviewBottomSheet
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel

@Composable
fun FullDetailScreen(
    backStackEntry: NavBackStackEntry,
    topSizeViewModel: TopSizeViewModel = hiltViewModel(),
    bottomSizeViewModel: BottomSizeViewModel = hiltViewModel(),
    outerSizeViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceSizeViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeSizeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessorySizeViewModel: AccessorySizeViewModel = hiltViewModel(),
    onEdit: (SizeCategory, ClothesSize) -> Unit,
) {
    val topSizes by topSizeViewModel.sizes.collectAsState()
    val bottomSizes by bottomSizeViewModel.sizes.collectAsState()
    val outerSizes by outerSizeViewModel.sizes.collectAsState()
    val onePieceSizes by onePieceSizeViewModel.sizes.collectAsState()
    val shoeSizes by shoeSizeViewModel.sizes.collectAsState()
    val accessorySizes by accessorySizeViewModel.sizes.collectAsState()

    val category = backStackEntry.arguments?.getString("category") ?: ""
    var selectedCategory by remember { mutableStateOf(SizeCategory.TOP) }

    val brand = backStackEntry.arguments?.getString("brand") ?: ""
    var selectedBrand by remember { mutableStateOf(brand) }

    val coroutineScope = rememberCoroutineScope()

    val brandSectionIndices = remember { mutableStateMapOf<String, Int>() }
    val highlightedBrand = remember { mutableStateOf<String?>(null) }

    val listState = rememberLazyListState()

    var selectedSize by remember { mutableStateOf<ClothesSize?>(null) }

    val allCategoryGroupedData = remember(
        topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes, selectedCategory
    ) {
        buildAllCategoryGroupedSizeData(
            topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes
        ) { selectedSize = it }
    }

    val allBrandGroupedData = remember(topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes, selectedBrand) {
        buildAllBrandGroupedSizeData(
            allSizes = (topSizes + bottomSizes + outerSizes + onePieceSizes + shoeSizes + accessorySizes),
            onSizeClick = { selectedSize = it }
        )
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

    LaunchedEffect(Unit) {
        if (category != "") {
            selectedCategory = SizeCategory.valueOf(category)
        }
    }

    MySizeContent(
        isFullDetailMode = true,
        listState = listState,
        coroutineScope = coroutineScope,
        defaultTab = if(brand != "") 1 else 0,
        defaultCategory = selectedCategory,
        brandSectionIndices = brandSectionIndices,
        highlightedBrand = highlightedBrand,
        searchQuery = selectedBrand,
        onSearchQueryChanged = { selectedBrand = it },
        categoryGroupedData = allCategoryGroupedData,
        brandGroupedData = allBrandGroupedData,
    )
}

fun buildAllCategoryGroupedSizeData(
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    onePieceSizes: List<OnePieceSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onSizeClick: (ClothesSize) -> Unit = {}
): Map<String, Map<String, List<SizeContentUiModel>>> {
    val groupedData = mutableMapOf<String, Map<String, List<SizeContentUiModel>>>()

    fun <T : ClothesSize> processSizes(
        sizes: List<T>,
        category: String
    ) {
        if (sizes.isEmpty()) return

        val typeGrouped = sizes
            .groupBy { it.type }
            .mapValues { (_, typeSizes) ->
                typeSizes.sortedByDescending { it.date }.map { size ->
                    SizeContentUiModel(
                        title = size.brand,
                        sizeLabel = size.sizeLabel,
                        onClick = { onSizeClick(size) }
                    )
                }
            }

        groupedData[category] = typeGrouped
    }

    processSizes(topSizes, "상의")
    processSizes(bottomSizes, "하의")
    processSizes(outerSizes, "아우터")
    processSizes(onePieceSizes, "일체형")
    processSizes(shoeSizes, "신발")
    processSizes(accessorySizes, "악세서리")

    return groupedData
}

fun buildAllBrandGroupedSizeData(
    allSizes: List<ClothesSize>,
    onSizeClick: (ClothesSize) -> Unit = {}
): Map<String, Map<String, List<SizeContentUiModel>>> {
    val brandGrouped = allSizes.groupBy { it.brand }

    val sortedBrandGrouped = brandGrouped
        .entries
        .sortedWith(compareBy({ it.key.contains("기타") }, { it.key }))

    return sortedBrandGrouped.associate { (brand, sizesInBrand) ->
        val categoryGrouped = sizesInBrand
            .groupBy { size ->
                when (size) {
                    is TopSize -> "상의"
                    is BottomSize -> "하의"
                    is OuterSize -> "아우터"
                    is OnePieceSize -> "일체형"
                    is ShoeSize -> "신발"
                    is AccessorySize -> "악세서리"
                    else -> "기타"
                }
            }
            .mapValues { (_, groupedByCategory) ->
                groupedByCategory
                    .groupBy { it.type }
                    .entries
                    .sortedWith(compareBy({ it.key.contains("기타") }, { it.key }))
                    .flatMap { (_, typeGroup) -> // ← 여기 flatMap!!
                        typeGroup.sortedByDescending { it.date }.map { size ->
                            SizeContentUiModel(
                                title = size.type,
                                sizeLabel = size.sizeLabel,
                                onClick = { onSizeClick(size) }
                            )
                        }
                    }
            }

        brand to categoryGrouped
    }
}

