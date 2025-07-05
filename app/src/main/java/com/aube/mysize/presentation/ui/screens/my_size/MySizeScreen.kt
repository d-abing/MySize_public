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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.app.NetworkMonitor
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BodySize
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
import com.aube.mysize.presentation.model.size.toUi
import com.aube.mysize.presentation.ui.component.dialog.WarningDialog
import com.aube.mysize.presentation.ui.screens.my_size.component.SizePreviewBottomSheet
import com.aube.mysize.presentation.ui.screens.my_size.content.MySizeContent
import com.aube.mysize.presentation.viewmodel.clothes.MyClothesViewModel
import com.aube.mysize.presentation.viewmodel.settings.SettingsViewModel
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel

@Composable
fun MySizeScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    myClothesViewModel: MyClothesViewModel,
    sizeViewModel: SizeViewModel,
    onNavigateToAllSizesByCategory: (SizeCategory) -> Unit,
    onNavigateToAllSizesByBrand: (String) -> Unit,
    onEdit: (SizeCategory, Size) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val isBodySizeCardSticky by settingsViewModel.isBodySizeCardSticky.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    val brandSectionIndices = remember { mutableStateMapOf<String, Int>() }
    val highlightedBrand = remember { mutableStateOf<String?>(null) }

    val listState = rememberLazyListState()

    var selectedSize by remember { mutableStateOf<ClothesSize?>(null) }

    val allSizes by sizeViewModel.sizes.collectAsState()
    val linkedSizeMap by myClothesViewModel.linkedSizeIndex.collectAsState()

    val bodySizeCard = allSizes[SizeCategory.BODY]
        ?.filterIsInstance<BodySize>()
        ?.firstOrNull()
        ?.toUi()

    var showSavedOnly by remember { mutableStateOf(false) }

    val categoryGroupedData = remember(allSizes, showSavedOnly) {
        buildCategoryGroupedSizeData(allSizes, showSavedOnly) { selectedSize = it }
    }

    val brandGroupedData = remember(allSizes) {
        buildBrandGroupedSizeData(allSizes) { selectedSize = it }
    }

    var showDeleteDialog by remember { mutableStateOf(false) }

    val networkAvailable by NetworkMonitor.networkAvailable.collectAsState()

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
            onDelete = {
                showDeleteDialog = true
            },
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
                showDeleteDialog = false
                selectedSize?.let { size ->
                    showDeleteDialog = false
                    val category = size.resolveCategory()
                    sizeViewModel.delete(category, size, linkedSizeMap)
                    selectedSize = null
                }
                selectedSize = null
            }
        )
    }

    LaunchedEffect(networkAvailable) {
        sizeViewModel.fetchAllSizes()
    }

    MySizeContent(
        isAllSizesMode = false,
        listState = listState,
        onBodySizeEdit = { id ->
            val bodySize = sizeViewModel.getSizeById(SizeCategory.BODY, id) as? BodySize
            if (bodySize != null) {
                onEdit(SizeCategory.BODY, bodySize)
            }
        },
        onBodySizeDelete = { id ->
            val bodySize = sizeViewModel.getSizeById(SizeCategory.BODY, id) as? BodySize
            if (bodySize != null) {
                sizeViewModel.delete(SizeCategory.BODY, bodySize)
            }
        },
        isBodySizeCardSticky = isBodySizeCardSticky,
        onBodySizeCardStickyChanged = {
            settingsViewModel.saveIsBodySizeCardSticky(!isBodySizeCardSticky)
        },
        bodySizeCard = bodySizeCard,
        coroutineScope = coroutineScope,
        brandSectionIndices = brandSectionIndices,
        highlightedBrand = highlightedBrand,
        searchQuery = searchQuery,
        onSearchQueryChanged = { searchQuery = it },
        categoryGroupedData = categoryGroupedData,
        onNavigateToAllSizesByCategory = onNavigateToAllSizesByCategory,
        brandGroupedData = brandGroupedData,
        onNavigateToAllSizesByBrand = onNavigateToAllSizesByBrand,
        showSavedOnly = showSavedOnly,
        onSavedSizeChipClick = { showSavedOnly = !showSavedOnly }
    )
}


fun buildCategoryGroupedSizeData(
    allSizes: Map<SizeCategory, List<Size>>,
    showSavedOnly: Boolean,
    onSizeClick: (ClothesSize) -> Unit
): Map<String, Map<String, List<SizeContentUiModel>>> {

    val groupedData = mutableMapOf<String, Map<String, List<SizeContentUiModel>>>()

    fun <T : ClothesSize> processSizes(
        sizes: List<T>,
        categoryLabel: String
    ) {
        if (sizes.isEmpty()) return

        val typeGrouped = sizes.groupBy { it.type }.mapValues { (_, typeGroup) ->
            typeGroup.groupBy { it.brand }
                .map { (_, brandGroup) ->
                    val sizeLabelCount = brandGroup.groupingBy { it.sizeLabel }.eachCount()
                    val max = sizeLabelCount.maxByOrNull { it.value }?.value ?: 0
                    val selectedSize = brandGroup.filter { it.sizeLabel in sizeLabelCount.filter { it.value == max }.keys }
                        .maxByOrNull { it.date }!!

                    SizeContentUiModel(
                        title = selectedSize.brand,
                        sizeLabel = selectedSize.sizeLabel,
                        isSaved = selectedSize.entryType == SizeEntryType.SAVED,
                        onClick = { onSizeClick(selectedSize) }
                    )
                }
                .sortedWith(compareBy({ it.title.contains("기타") }, { it.title }))
        }

        val (normal, etc) = typeGrouped.entries.partition { !it.key.contains("기타") }
        val sortedTypeGrouped = (normal.sortedBy { it.key } + etc.sortedBy { it.key }).associate { it.toPair() }

        groupedData[categoryLabel] = sortedTypeGrouped
    }

    // 카테고리별 처리
    allSizes.forEach { (category, sizes) ->
        val clothesSizes = sizes.filterIsInstance<ClothesSize>()
            .filter { !showSavedOnly || it.entryType == SizeEntryType.SAVED }
        val label = when (category) {
            SizeCategory.TOP -> "상의"
            SizeCategory.BOTTOM -> "하의"
            SizeCategory.OUTER -> "아우터"
            SizeCategory.ONE_PIECE -> "일체형"
            SizeCategory.SHOE -> "신발"
            SizeCategory.ACCESSORY -> "악세사리"
            else -> return@forEach
        }
        processSizes(clothesSizes, label)
    }

    return groupedData
}


fun buildBrandGroupedSizeData(
    allSizes: Map<SizeCategory, List<Size>>,
    onSizeClick: (ClothesSize) -> Unit
): Map<String, Map<String, List<SizeContentUiModel>>> {

    val allClothesSizes = allSizes
        .filterKeys { it != SizeCategory.BODY }
        .values
        .flatten()
        .filterIsInstance<ClothesSize>()

    return allClothesSizes.groupBy { it.brand }
        .toSortedMap(compareBy({ it.contains("기타") }, { it }))
        .mapValues { (_, sizesInBrand) ->
            sizesInBrand.groupBy {
                when (it) {
                    is TopSize -> "상의"
                    is BottomSize -> "하의"
                    is OuterSize -> "아우터"
                    is OnePieceSize -> "일체형"
                    is ShoeSize -> "신발"
                    is AccessorySize -> "악세사리"
                    else -> "기타"
                }
            }.mapValues { (_, categoryGroup) ->
                categoryGroup.groupBy { it.type }
                    .toSortedMap(compareBy({ it.contains("기타") }, { it }))
                    .map { (_, typeGroup) ->
                        val sizeLabelCount = typeGroup.groupingBy { it.sizeLabel }.eachCount()
                        val max = sizeLabelCount.maxByOrNull { it.value }?.value ?: 0
                        val candidates = sizeLabelCount.filter { it.value == max }.keys
                        val selected = typeGroup.filter { it.sizeLabel in candidates }.maxByOrNull { it.date }!!

                        SizeContentUiModel(
                            title = selected.type,
                            sizeLabel = selected.sizeLabel,
                            isSaved = selected.entryType == SizeEntryType.SAVED,
                            onClick = { onSizeClick(selected) }
                        )
                    }
            }
        }
}