package com.aube.mysize.presentation.ui.screens.my_size

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.AccessorySize
import com.aube.mysize.domain.model.BottomSize
import com.aube.mysize.domain.model.ClothSize
import com.aube.mysize.domain.model.OnePieceSize
import com.aube.mysize.domain.model.OuterSize
import com.aube.mysize.domain.model.ShoeSize
import com.aube.mysize.domain.model.TopSize
import com.aube.mysize.domain.model.toUi
import com.aube.mysize.presentation.model.BodySizeCardUiModel
import com.aube.mysize.presentation.model.SizeContentUiModel
import com.aube.mysize.presentation.ui.component.mysize.BodySizeCard
import com.aube.mysize.presentation.ui.component.mysize.MySizeTabRow
import com.aube.mysize.presentation.ui.component.mysize.SubListBlock
import com.aube.mysize.presentation.ui.component.mysize.bottomsheet.SizePreviewBottomSheet
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel

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

    val listState = rememberLazyListState()

    var selectedSize by remember { mutableStateOf<ClothSize?>(null) }

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

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        // 1. 바디 사이즈 카드
        bodySizeCard?.let { card ->
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
                ) {
                    BodySizeCard(
                        title = card.title,
                        imageVector = card.imageVector,
                        description = card.description
                    )
                }
            }
        }


        // 2. TabRow (Sticky)
        stickyHeader {
            Surface(
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                MySizeTabRow(
                    selectedTabIndex = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
            HorizontalDivider(thickness = 0.5.dp)
        }

        // 3. 종류별 보기 or 브랜드별 보기
        if (selectedTab == 0) {
            typeGroupedData.forEach { (category, subTypeMap) ->
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiary.copy(0.2f)
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
            }
        } else {
            brandGroupedData.forEach { (brand, categoryMap) ->
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = brand,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiary.copy(0.2f)
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
    onSizeClick: (ClothSize) -> Unit
): Map<String, Map<String, List<SizeContentUiModel>>> {
    val groupedData = mutableMapOf<String, Map<String, List<SizeContentUiModel>>>()

    fun <T : ClothSize> processSizes(
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
    onSizeClick: (ClothSize) -> Unit
): Map<String, Map<String, List<SizeContentUiModel>>> {
    val allSizes: List<ClothSize> = topSizes + bottomSizes + outerSizes + onePieceSizes + shoeSizes + accessorySizes

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
