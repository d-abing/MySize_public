package com.aube.mysize.presentation.ui.screens.my_size

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.ClothesSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.presentation.model.BodySizeCardUiModel
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.model.SizeContentUiModel
import com.aube.mysize.presentation.ui.component.CategoryChip
import com.aube.mysize.presentation.ui.component.GuideButton
import com.aube.mysize.presentation.ui.component.GuideDialog
import com.aube.mysize.presentation.ui.component.HighlightedTitle
import com.aube.mysize.presentation.ui.screens.my_size.component.MySizeTabRow
import com.aube.mysize.presentation.ui.screens.my_size.component.SensitiveBodySizeCard
import com.aube.mysize.presentation.ui.screens.my_size.component.SubListBlock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MySizeContent(
    isFullDetailMode: Boolean = false,
    listState: LazyListState,
    onBodySizeEdit: ((Int) -> Unit)? = null,
    onBodySizeDelete: ((Int) -> Unit)? = null,
    isBodySizeCardSticky: Boolean? = null,
    onBodySizeCardStickyChanged: (() -> Unit)? = null,
    bodySizeCard: BodySizeCardUiModel? = null,
    coroutineScope: CoroutineScope,
    defaultTab: Int = 0,
    defaultCategory: SizeCategory? = null,
    brandSectionIndices: SnapshotStateMap<String, Int>,
    highlightedBrand: MutableState<String?>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    categoryGroupedData: Map<String, Map<String, List<SizeContentUiModel>>>,
    onNavigateToFullDetailByCategory: ((SizeCategory) -> Unit)? = null,
    brandGroupedData: Map<String, Map<String, List<SizeContentUiModel>>>,
    onNavigateToFullDetailByBrand: ((String) -> Unit)? = null,
) {
    var selectedTab by remember { mutableIntStateOf(defaultTab) }
    var selectedCategory by remember { mutableStateOf(defaultCategory) }
    val allCategories = SizeCategory.entries.filter { it != SizeCategory.BODY }
    val categorySectionIndices = remember { mutableStateMapOf<SizeCategory, Int>() }
    val highlightedCategory = remember { mutableStateOf<SizeCategory?>(null) }
    val bodySizeCardHeightPx = remember { mutableStateOf(0) }
    val stickyHeaderHeightPx = remember { mutableStateOf(0) }
    var showGuideDialog by remember { mutableStateOf(false) }

    if (showGuideDialog) {
        GuideDialog(
            title = "어떤 사이즈가 표시되나요?",
            onDismiss = { showGuideDialog = false }
        ) {
            Text(
                text = "가장 많이 저장한 사이즈 라벨을 보여드립니다.\n" +
                        "여러 개가 같을 경우, 최근에 저장한 걸 사용해요.",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 12.dp)
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = listState
    ) {
        // 1. 바디 사이즈 카드
        if (isBodySizeCardSticky != null && !isBodySizeCardSticky && onBodySizeEdit != null && onBodySizeDelete != null) {
            bodySizeCard?.let { card ->
                item {
                    SensitiveBodySizeCard(
                        bodySizeCard = card,
                        isBodySizeCardSticky = isBodySizeCardSticky,
                        onEdit = { onBodySizeEdit(bodySizeCard.id) },
                        onDelete = { onBodySizeDelete(bodySizeCard.id) },
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                            .onGloballyPositioned { coordinates ->
                                bodySizeCardHeightPx.value = coordinates.size.height
                            },
                        onBodySizeCardStickyChanged = {
                            if (onBodySizeCardStickyChanged != null) {
                                onBodySizeCardStickyChanged()
                            }
                        }
                    )
                }
            }
        }

        // 2. TabRow (Sticky)
        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .onGloballyPositioned { layoutCoordinates ->
                        stickyHeaderHeightPx.value = layoutCoordinates.size.height
                    }
            ) {
                if (isBodySizeCardSticky != null && isBodySizeCardSticky && onBodySizeEdit != null && onBodySizeDelete != null) {
                    bodySizeCard?.let { card ->
                        SensitiveBodySizeCard(
                            bodySizeCard = card,
                            isBodySizeCardSticky = isBodySizeCardSticky,
                            onEdit = { onBodySizeEdit(bodySizeCard.id) },
                            onDelete = { onBodySizeDelete(bodySizeCard.id) },
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 16.dp),
                            onBodySizeCardStickyChanged = {
                                if (onBodySizeCardStickyChanged != null) {
                                    onBodySizeCardStickyChanged()
                                }
                            }
                        )
                    }
                }

                MySizeTabRow(
                    listOf("종류별 보기", "브랜드별 보기"),
                    selectedTabIndex = selectedTab,
                    onTabSelected = { selectedTab = it }
                )

                HorizontalDivider(thickness = 0.5.dp)

                if (selectedTab == 0) {
                    CategoryChip(
                        addGuideChip = true,
                        onGuideChipClick = { showGuideDialog = true },
                        categories = categorySectionIndices.keys.toList(),
                        selectedCategory = selectedCategory,
                        enableColorHighlight = isFullDetailMode,
                        onClick = { category ->
                            selectedCategory = category

                            if (!isFullDetailMode) {
                                highlightedCategory.value = category

                                categorySectionIndices[category]?.let { index ->
                                    coroutineScope.launch {
                                        val offset = if (isBodySizeCardSticky == false) {
                                            -(stickyHeaderHeightPx.value + bodySizeCardHeightPx.value)
                                        } else {
                                            -stickyHeaderHeightPx.value
                                        }
                                        listState.animateScrollToItem(index, offset)
                                    }
                                }
                            }
                        }
                    )
                } else {
                    val keyboardController = LocalSoftwareKeyboardController.current

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { onSearchQueryChanged(it) },
                                placeholder = {
                                    Text(
                                        "브랜드명 검색",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                },
                                textStyle = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp),
                                singleLine = true,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            val matchedBrand = brandSectionIndices.keys.firstOrNull {
                                                it.contains(searchQuery, ignoreCase = true)
                                            }
                                            matchedBrand?.let {
                                                highlightedBrand.value = it
                                                coroutineScope.launch {
                                                    val offset = if (isBodySizeCardSticky == false) {
                                                        -(stickyHeaderHeightPx.value + bodySizeCardHeightPx.value)
                                                    } else {
                                                        -stickyHeaderHeightPx.value
                                                    }
                                                    listState.animateScrollToItem(
                                                        brandSectionIndices[it] ?: 0,
                                                        offset
                                                    )
                                                }
                                            }
                                            keyboardController?.hide()
                                        }
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        val matchedBrand = brandSectionIndices.keys.firstOrNull {
                                            it.contains(searchQuery, ignoreCase = true)
                                        }
                                        matchedBrand?.let {
                                            highlightedBrand.value = it
                                            coroutineScope.launch {
                                                listState.animateScrollToItem(
                                                    brandSectionIndices[it] ?: 0,
                                                    -stickyHeaderHeightPx.value
                                                )
                                            }
                                        }
                                        keyboardController?.hide()
                                    }
                                ),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(start = 16.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(56.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            GuideButton(
                                onClick = { showGuideDialog = true },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        // 3. 종류별 보기 or 브랜드별 보기
        if (selectedTab == 0) {
            var currentIndex = if (isBodySizeCardSticky != null && !isBodySizeCardSticky) 2 else 1

            categoryGroupedData.forEach { (categoryLabel, subTypeMap) ->

                val category = allCategories.firstOrNull { it.label == categoryLabel }
                category?.let {
                    categorySectionIndices[category] = currentIndex
                }

                if (isFullDetailMode && category != selectedCategory) return@forEach

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            HighlightedTitle(
                                text = categoryLabel,
                                isHighlighted = highlightedCategory.value == category,
                                onAnimationEnd = { highlightedCategory.value = null }
                            )

                            if (onNavigateToFullDetailByCategory != null) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .clickable {
                                            if (category != null) {
                                                onNavigateToFullDetailByCategory(category)
                                            }
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "전체 보기",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                        contentDescription = "전체 보기 이동",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiary.copy(0.1f)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                subTypeMap.forEach { (typeName, contents) ->
                                    SubListBlock(typeName = typeName, contents = contents)
                                }

                            }
                        }
                    }
                }

                currentIndex++
            }
        } else {
            var currentIndex = if (isBodySizeCardSticky != null && !isBodySizeCardSticky) 2 else 1

            brandGroupedData.forEach { (brand, categoryMap) ->
                brandSectionIndices[brand] = currentIndex

                if (isFullDetailMode && !brand.contains(searchQuery, ignoreCase = true)) return@forEach

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(vertical = 4.dp, horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            HighlightedTitle(
                                text = brand,
                                isHighlighted = highlightedBrand.value == brand,
                                onAnimationEnd = { highlightedBrand.value = null }
                            )

                            if (onNavigateToFullDetailByBrand != null) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .clickable {
                                            onNavigateToFullDetailByBrand(brand)
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "전체 보기",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                        contentDescription = "전체 보기 이동",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiary.copy(0.1f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                categoryMap.forEach { (category, contents) ->
                                    SubListBlock(typeName = category, contents = contents)
                                }
                            }
                        }
                    }
                }

                currentIndex++
            }
        }
    }
}


fun buildCategoryGroupedSizeData(
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    onePieceSizes: List<OnePieceSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onSizeClick: (ClothesSize) -> Unit
): Map<String, Map<String, List<SizeContentUiModel>>> {
    val groupedData = mutableMapOf<String, Map<String, List<SizeContentUiModel>>>()

    fun <T : ClothesSize> processSizes(
        sizes: List<T>,
        category: String
    ) {
        if (sizes.isEmpty()) return

        val typeGrouped = sizes
            .groupBy { it.type }
            .mapValues { (_, groupedByType) ->
                val brandGrouped = groupedByType
                    .groupBy { it.brand }
                    .map { (_, brandSizes) ->
                        val sizeLabelCountMap = brandSizes
                            .groupingBy { it.sizeLabel }
                            .eachCount()

                        val maxCount = sizeLabelCountMap.maxByOrNull { it.value }?.value ?: 0

                        val candidates = sizeLabelCountMap
                            .filter { it.value == maxCount }
                            .keys

                        val selectedSize = brandSizes
                            .filter { it.sizeLabel in candidates }
                            .maxByOrNull { it.date }!!

                        SizeContentUiModel(
                            title = selectedSize.brand,
                            sizeLabel = selectedSize.sizeLabel,
                            onClick = { onSizeClick(selectedSize) }
                        )
                    }
                    .sortedWith(
                        compareBy(
                            { it.title.contains("기타") },
                            { it.title }
                        )
                    )

                brandGrouped
            }

        val (normal, etc) = typeGrouped.entries.partition { !it.key.contains("기타") }
        val sortedTypeGrouped = (normal.sortedBy { it.key } + etc.sortedBy { it.key })
            .associate { it.toPair() }

        groupedData[category] = sortedTypeGrouped
    }

    processSizes(topSizes, "상의")
    processSizes(bottomSizes, "하의")
    processSizes(outerSizes, "아우터")
    processSizes(onePieceSizes, "일체형")
    processSizes(shoeSizes, "신발")
    processSizes(accessorySizes, "악세서리")

    return groupedData
}


fun buildBrandGroupedSizeData(
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    onePieceSizes: List<OnePieceSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onSizeClick: (ClothesSize) -> Unit
): Map<String, Map<String, List<SizeContentUiModel>>> {
    val allSizes: List<ClothesSize> = topSizes + bottomSizes + outerSizes + onePieceSizes + shoeSizes + accessorySizes

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
                val typeGrouped = groupedByCategory
                    .groupBy { it.type }
                    .entries
                    .sortedWith(compareBy({ it.key.contains("기타") }, { it.key }))
                    .map { (_, typeGroup) ->
                        val sizeLabelCountMap = typeGroup.groupingBy { it.sizeLabel }.eachCount()
                        val maxCount = sizeLabelCountMap.maxByOrNull { it.value }?.value ?: 0
                        val candidates = sizeLabelCountMap.filter { it.value == maxCount }.keys
                        val selected = typeGroup.filter { it.sizeLabel in candidates }
                            .maxByOrNull { it.date }!!

                        SizeContentUiModel(
                            title = selected.type,
                            sizeLabel = selected.sizeLabel,
                            onClick = { onSizeClick(selected) }
                        )
                    }

                typeGrouped
            }

        brand to categoryGrouped
    }
}
