package com.aube.mysize.presentation.ui.screens.my_size

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.AccessorySize
import com.aube.mysize.domain.model.BottomSize
import com.aube.mysize.domain.model.ClothesSize
import com.aube.mysize.domain.model.OnePieceSize
import com.aube.mysize.domain.model.OuterSize
import com.aube.mysize.domain.model.ShoeSize
import com.aube.mysize.domain.model.TopSize
import com.aube.mysize.domain.model.toUi
import com.aube.mysize.presentation.model.BodySizeCardUiModel
import com.aube.mysize.presentation.model.SizeContentUiModel
import com.aube.mysize.presentation.ui.component.CategoryChip
import com.aube.mysize.presentation.ui.component.HighlightedTitle
import com.aube.mysize.presentation.ui.component.mysize.MySizeTabRow
import com.aube.mysize.presentation.ui.component.mysize.SensitiveBodySizeCard
import com.aube.mysize.presentation.ui.component.mysize.SubListBlock
import com.aube.mysize.presentation.ui.component.mysize.bottomsheet.SizePreviewBottomSheet
import com.aube.mysize.presentation.ui.nav.SizeCategory
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
    onNavigateToFullDetail: () -> Unit
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
        onNavigateToFullDetail =  onNavigateToFullDetail
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MySizeScreen(
    bodySizeCard: BodySizeCardUiModel?,
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    onePieceSizes: List<OnePieceSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onNavigateToFullDetail: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    var selectedCategory by remember { mutableStateOf<SizeCategory?>(null) }
    val allCategories = SizeCategory.entries.filter { it != SizeCategory.BODY }
    val categorySectionIndices = remember { mutableStateMapOf<SizeCategory, Int>() }
    val highlightedCategory = remember { mutableStateOf<SizeCategory?>(null) }

    var searchQuery by remember { mutableStateOf("") }
    val brandSectionIndices = remember { mutableStateMapOf<String, Int>() }
    val highlightedBrand = remember { mutableStateOf<String?>(null) }

    val listState = rememberLazyListState()

    var selectedSize by remember { mutableStateOf<ClothesSize?>(null) }


    val typeGroupedData = remember(topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes) {
        buildTypeGroupedSizeData(
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

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
        ,
        state = listState
    ) {
        // 1. 바디 사이즈 카드
        bodySizeCard?.let { card ->
            item {
                SensitiveBodySizeCard(
                    bodySizeCard = card,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                )
            }
        }


        // 2. TabRow (Sticky)
        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                MySizeTabRow(
                    listOf("종류별 보기", "브랜드별 보기"),
                    selectedTabIndex = selectedTab,
                    onTabSelected = { selectedTab = it }
                )

                HorizontalDivider(thickness = 0.5.dp)

                if (selectedTab == 0) {
                    CategoryChip(
                        categories = categorySectionIndices.keys.toList(),
                        selectedCategory = selectedCategory,
                        onClick = { category ->
                            selectedCategory = category
                            highlightedCategory.value = category

                            categorySectionIndices[category]?.let { index ->
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index)
                                }
                            }
                        }
                    )
                } else {
                    val keyboardController = LocalSoftwareKeyboardController.current

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("브랜드명 검색", style = MaterialTheme.typography.labelLarge) },
                        textStyle = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        singleLine = true,
                        trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                val matchedBrand = brandSectionIndices.keys.firstOrNull {
                                    it.contains(searchQuery, ignoreCase = true)
                                }
                                matchedBrand?.let {
                                    highlightedBrand.value = it
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(brandSectionIndices[it] ?: 0)
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
                            .padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                }
            }
        }

        // 3. 종류별 보기 or 브랜드별 보기
        if (selectedTab == 0) {
            var currentIndex = 1

            typeGroupedData.forEach { (categoryLabel, subTypeMap) ->

                val category = allCategories.firstOrNull { it.label == categoryLabel }
                category?.let {
                    categorySectionIndices[category] = currentIndex
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                    ) {
                        HighlightedTitle(
                            text = categoryLabel,
                            isHighlighted = highlightedCategory.value == category,
                            onAnimationEnd = { highlightedCategory.value = null }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Card (
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
            var currentIndex = 0

            brandGroupedData.forEach { (brand, categoryMap) ->
                brandSectionIndices[brand] = currentIndex

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        HighlightedTitle (
                            text = brand,
                            isHighlighted = highlightedBrand.value == brand,
                            onAnimationEnd = { highlightedBrand.value = null }
                        )

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


fun buildTypeGroupedSizeData(
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
