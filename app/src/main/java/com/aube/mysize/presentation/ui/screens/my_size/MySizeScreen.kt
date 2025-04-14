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
import com.aube.mysize.domain.model.OuterSize
import com.aube.mysize.domain.model.ShoeSize
import com.aube.mysize.domain.model.Size
import com.aube.mysize.domain.model.TopSize
import com.aube.mysize.domain.model.toUi
import com.aube.mysize.presentation.model.BodySizeCardUiModel
import com.aube.mysize.presentation.model.SizeContentUiModel
import com.aube.mysize.presentation.ui.component.mysize.BodySizeCard
import com.aube.mysize.presentation.ui.component.mysize.MyCustomTabRow
import com.aube.mysize.presentation.ui.component.mysize.SubListBlock
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel

@Composable
fun MySizeScreen(
    bodyViewModel: BodySizeViewModel = hiltViewModel(),
    topViewModel: TopSizeViewModel = hiltViewModel(),
    bottomViewModel: BottomSizeViewModel = hiltViewModel(),
    outerViewModel: OuterSizeViewModel = hiltViewModel(),
    shoeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessoryViewModel: AccessorySizeViewModel = hiltViewModel(),
) {
    val bodySizes by bodyViewModel.sizes.collectAsState()
    val topSizes by topViewModel.sizes.collectAsState()
    val bottomSizes by bottomViewModel.sizes.collectAsState()
    val outerSizes by outerViewModel.sizes.collectAsState()
    val shoeSizes by shoeViewModel.sizes.collectAsState()
    val accessorySizes by accessoryViewModel.sizes.collectAsState()

    val bodySizeCard = bodySizes.firstOrNull()?.toUi()

    MySizeScreen(
        bodySizeCard = bodySizeCard,
        topSizes = topSizes,
        bottomSizes = bottomSizes,
        outerSizes = outerSizes,
        shoeSizes = shoeSizes,
        accessorySizes = accessorySizes,
        onSizeClick = { size ->
//            onNavigateToSizeDetail(size) // 상세 화면 이동 처리
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MySizeScreen(
    bodySizeCard: BodySizeCardUiModel?,
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onSizeClick: (Size) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()

    val typeGroupedData = remember(topSizes, bottomSizes, outerSizes, shoeSizes, accessorySizes) {
        buildTypeGroupedSizeData(
            topSizes, bottomSizes, outerSizes, shoeSizes, accessorySizes, onSizeClick
        )
    }

    val brandGroupedData = remember(topSizes, bottomSizes, outerSizes, shoeSizes, accessorySizes) {
        buildBrandGroupedSizeData(
            topSizes, bottomSizes, outerSizes, shoeSizes, accessorySizes, onSizeClick
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
                        .padding(top = 16.dp, start = 16.dp, end = 6.dp, bottom = 8.dp)
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
                MyCustomTabRow(
                    selectedTabIndex = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
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
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onSizeClick: (Size) -> Unit
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
                groupedByType
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
            }

        val (normal, etc) = typeGrouped.entries.partition { !it.key.contains("기타") }
        val sortedTypeGrouped = (normal.sortedBy { it.key } + etc.sortedBy { it.key })
            .associate { it.toPair() }

        groupedData[category] = sortedTypeGrouped
    }

    processSizes(topSizes, "상의")
    processSizes(bottomSizes, "하의")
    processSizes(outerSizes, "아우터")
    processSizes(shoeSizes, "신발")
    processSizes(accessorySizes, "악세서리")

    return groupedData
}


fun buildBrandGroupedSizeData(
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onSizeClick: (Size) -> Unit
): Map<String, Map<String, List<SizeContentUiModel>>> {
    val allSizes: List<ClothSize> = topSizes + bottomSizes + outerSizes + shoeSizes + accessorySizes

    val brandGrouped = allSizes.groupBy { it.brand }

    return brandGrouped.mapValues { (_, sizesInBrand) ->
        sizesInBrand
            .groupBy { size ->
                when (size) {
                    is TopSize -> "상의"
                    is BottomSize -> "하의"
                    is OuterSize -> "아우터"
                    is ShoeSize -> "신발"
                    is AccessorySize -> "악세서리"
                    else -> "기타"
                }
            }
            .mapValues { (_, groupedByCategory) ->
                groupedByCategory.map { size ->
                    SizeContentUiModel(
                        title = when (size) {
                            is TopSize -> size.type
                            is BottomSize -> size.type
                            is OuterSize -> size.type
                            is ShoeSize -> size.type
                            is AccessorySize -> size.type
                            else -> ""
                        },
                        sizeLabel = size.sizeLabel,
                        onClick = { onSizeClick(size) }
                    )
                }
            }
    }
}
