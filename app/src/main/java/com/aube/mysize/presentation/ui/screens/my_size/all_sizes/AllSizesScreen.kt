package com.aube.mysize.presentation.ui.screens.my_size.all_sizes

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.ClothesSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.domain.model.size.resolveCategory
import com.aube.mysize.presentation.model.size.SizeContentUiModel
import com.aube.mysize.presentation.ui.component.dialog.WarningDialog
import com.aube.mysize.presentation.ui.screens.my_size.component.SizePreviewBottomSheet
import com.aube.mysize.presentation.ui.screens.my_size.content.MySizeContent
import com.aube.mysize.presentation.viewmodel.clothes.MyClothesViewModel
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel

@Composable
fun AllSizesScreen(
    myClothesViewModel: MyClothesViewModel,
    sizeViewModel: SizeViewModel,
    backStackEntry: NavBackStackEntry,
    onEdit: (SizeCategory, ClothesSize) -> Unit,
) {
    val allSizes by sizeViewModel.sizes.collectAsState()
    val linkedSizeMap by myClothesViewModel.linkedSizeIndex.collectAsState()

    val defaultCategory = backStackEntry.arguments?.getString("category")?.let {
        runCatching { SizeCategory.valueOf(it) }.getOrNull()
    }

    val defaultBrand = backStackEntry.arguments?.getString("brand") ?: ""

    val selectedCategory by rememberSaveable { mutableStateOf(defaultCategory ?: SizeCategory.TOP) }
    var selectedBrand by rememberSaveable { mutableStateOf(defaultBrand) }
    var showSavedOnly by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val brandSectionIndices = remember { mutableStateMapOf<String, Int>() }
    val highlightedBrand = remember { mutableStateOf<String?>(null) }
    val listState = rememberLazyListState()
    var selectedSize by remember { mutableStateOf<ClothesSize?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val categoryGroupedData = remember(allSizes, showSavedOnly) {
        buildAllCategoryGroupedSizes(allSizes, showSavedOnly) { selectedSize = it }
    }

    val brandGroupedData by remember(allSizes, selectedBrand) {
        derivedStateOf {
            buildAllBrandGroupedSizes(allSizes) { selectedSize = it }
        }
    }

    selectedSize?.let {
        SizePreviewBottomSheet(
            size = it,
            onEdit = { size -> onEdit(size.resolveCategory(), size) },
            onDelete = { showDeleteDialog = true },
            onDismiss = { selectedSize = null },
        )
    }

    if (showDeleteDialog) {
        WarningDialog(
            title = stringResource(R.string.text_delete_confirm_title),
            description = stringResource(R.string.text_delete_size_description),
            confirmText = stringResource(R.string.action_delete),
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                selectedSize?.let { size ->
                    sizeViewModel.delete(size.resolveCategory(), size, linkedSizeMap)
                }
                selectedSize = null
                showDeleteDialog = false
            }
        )
    }

    MySizeContent(
        isAllSizesMode = true,
        listState = listState,
        coroutineScope = coroutineScope,
        defaultTab = if (defaultBrand.isNotBlank()) 1 else 0,
        defaultCategory = selectedCategory,
        brandSectionIndices = brandSectionIndices,
        highlightedBrand = highlightedBrand,
        searchQuery = selectedBrand,
        onSearchQueryChanged = { selectedBrand = it },
        categoryGroupedData = categoryGroupedData,
        brandGroupedData = brandGroupedData,
        showSavedOnly = showSavedOnly,
        onSavedSizeChipClick = { showSavedOnly = !showSavedOnly }
    )
}


fun buildAllCategoryGroupedSizes(
    allSizes: Map<SizeCategory, List<Size>>,
    showSavedOnly: Boolean = false,
    onSizeClick: (ClothesSize) -> Unit = {},
): Map<String, Map<String, List<SizeContentUiModel>>> {
    return allSizes
        .filterKeys { it != SizeCategory.BODY }
        .mapNotNull { (category, list) ->
            val sizes = list
                .filterIsInstance<ClothesSize>()
                .filter { !showSavedOnly || it.entryType == SizeEntryType.SAVED }

            if (sizes.isEmpty()) return@mapNotNull null

            val categoryLabel = when (category) {
                SizeCategory.TOP -> "상의"
                SizeCategory.BOTTOM -> "하의"
                SizeCategory.OUTER -> "아우터"
                SizeCategory.ONE_PIECE -> "일체형"
                SizeCategory.SHOE -> "신발"
                SizeCategory.ACCESSORY -> "악세서리"
                else -> return@mapNotNull null
            }

            val typeGrouped = sizes
                .groupBy { it.type }
                .mapValues { (_, typeGroup) ->
                    typeGroup.sortedByDescending { it.date }.map { size ->
                        SizeContentUiModel(
                            title = size.brand,
                            sizeLabel = size.sizeLabel,
                            isSaved = size.entryType == SizeEntryType.SAVED,
                            onClick = { onSizeClick(size) }
                        )
                    }
                }

            categoryLabel to typeGrouped
        }
        .toMap()
}

fun buildAllBrandGroupedSizes(
    allSizes: Map<SizeCategory, List<Size>>,
    onSizeClick: (ClothesSize) -> Unit = {}
): Map<String, Map<String, List<SizeContentUiModel>>> {
    val allClothesSizes = allSizes
        .filterKeys { it != SizeCategory.BODY }
        .values.flatten()
        .filterIsInstance<ClothesSize>()

    return allClothesSizes
        .groupBy { it.brand }
        .toSortedMap(compareBy<String> { it.contains("기타") }.thenBy { it })
        .mapValues { (_, brandSizes) ->
            brandSizes
                .groupBy {
                    when (it) {
                        is TopSize -> "상의"
                        is BottomSize -> "하의"
                        is OuterSize -> "아우터"
                        is OnePieceSize -> "일체형"
                        is ShoeSize -> "신발"
                        is AccessorySize -> "악세서리"
                        else -> "기타"
                    }
                }
                .mapValues { (_, categoryGroup) ->
                    categoryGroup
                        .groupBy { it.type }
                        .toSortedMap(compareBy<String> { it.contains("기타") }.thenBy { it })
                        .flatMap { (_, typeGroup) ->
                            typeGroup.sortedByDescending { it.date }.map { size ->
                                SizeContentUiModel(
                                    title = size.type,
                                    sizeLabel = size.sizeLabel,
                                    isSaved = size.entryType == SizeEntryType.SAVED,
                                    onClick = { onSizeClick(size) }
                                )
                            }
                        }
                }
        }
}

