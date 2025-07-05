package com.aube.mysize.presentation.ui.screens.my_size.content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.model.size.BodySizeCardUiModel
import com.aube.mysize.presentation.model.size.SizeContentUiModel
import com.aube.mysize.presentation.model.size.toUi
import com.aube.mysize.presentation.ui.component.EmptyListAnimation
import com.aube.mysize.presentation.ui.component.HighlightedTitle
import com.aube.mysize.presentation.ui.component.button.MSGuideButton
import com.aube.mysize.presentation.ui.component.chip_tap.MSCategoryChip
import com.aube.mysize.presentation.ui.component.chip_tap.MSTabRow
import com.aube.mysize.presentation.ui.component.dialog.GuideDialog
import com.aube.mysize.presentation.ui.component.dialog.WarningDialog
import com.aube.mysize.presentation.ui.screens.my_size.component.SensitiveBodySizeCard
import com.aube.mysize.presentation.ui.screens.my_size.component.SubListBlock
import com.aube.mysize.ui.theme.MySizeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MySizeContent(
    isAllSizesMode: Boolean = false,
    listState: LazyListState,
    onBodySizeEdit: ((String) -> Unit)? = null,
    onBodySizeDelete: ((String) -> Unit)? = null,
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
    onNavigateToAllSizesByCategory: ((SizeCategory) -> Unit)? = null,
    brandGroupedData: Map<String, Map<String, List<SizeContentUiModel>>>,
    onNavigateToAllSizesByBrand: ((String) -> Unit)? = null,
    showSavedOnly: Boolean,
    onSavedSizeChipClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(defaultTab) }
    var selectedCategory by remember { mutableStateOf(defaultCategory) }
    val allCategories = SizeCategory.entries.filter { it != SizeCategory.BODY }
    val categorySectionIndices = remember { mutableStateMapOf<SizeCategory, Int>() }
    var highlightedCategory by remember { mutableStateOf<SizeCategory?>(null) }
    val bodySizeCardHeightPx = remember { mutableStateOf(0) }
    val stickyHeaderHeightPx = remember { mutableStateOf(0) }
    var showGuideDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showGuideDialog) {
        GuideDialog(
            title = stringResource(R.string.guide_size_label_title),
            onDismiss = { showGuideDialog = false }
        ) {
            Text(
                text = if (isAllSizesMode) {
                    stringResource(R.string.guide_full_size_label_description)
                } else {
                    stringResource(R.string.guide_size_label_description)
                },
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 12.dp)
            )
        }
    }

    if (showDeleteDialog) {
        WarningDialog(
            title = stringResource(R.string.text_delete_confirm_title),
            description = stringResource(R.string.text_delete_size_description),
            confirmText = stringResource(R.string.action_delete),
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                showDeleteDialog = false
                if (onBodySizeDelete != null && bodySizeCard != null) {
                    onBodySizeDelete(bodySizeCard.id)
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .wrapContentHeight(),
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
                            onDelete = {
                                showDeleteDialog = true
                            },
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

                    MSTabRow(
                        listOf(
                            stringResource(R.string.my_size_tab_category),
                            stringResource(R.string.my_size_tab_brand)
                        ),
                        selectedTabIndex = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )

                    HorizontalDivider(thickness = 0.5.dp)

                    if (selectedTab == 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(60.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                MSCategoryChip(
                                    addSavedSizeChip = true,
                                    showSavedSizeOnly = showSavedOnly,
                                    onSavedSizeChipClick = onSavedSizeChipClick,
                                    categories = categorySectionIndices.keys.toList(),
                                    selectedCategory = selectedCategory,
                                    enableColorHighlight = isAllSizesMode,
                                    onClick = { category ->
                                        selectedCategory = category

                                        if (!isAllSizesMode) {
                                            highlightedCategory = category

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
                            }

                            Column(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .height(56.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                MSGuideButton(
                                    onClick = { showGuideDialog = true },
                                )
                            }
                        }
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
                                            stringResource(R.string.placeholder_brand_search),
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
                                    .height(56.dp)
                                    .padding(top = 2.5.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                MSGuideButton(
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

                    val category = allCategories.firstOrNull { it.toUi().label == categoryLabel }
                    category?.let {
                        categorySectionIndices[category] = currentIndex
                    }

                    if (isAllSizesMode && category != selectedCategory) return@forEach

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
                                    isHighlighted = highlightedCategory == category,
                                    onAnimationEnd = { highlightedCategory = null }
                                )

                                if (onNavigateToAllSizesByCategory != null) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .clickable(
                                                indication = null,
                                                interactionSource = remember { MutableInteractionSource() }
                                            ) {
                                                if (category != null) {
                                                    onNavigateToAllSizesByCategory(category)
                                                }
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(R.string.action_see_all),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                            contentDescription = stringResource(R.string.action_see_all),
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

                    if (isAllSizesMode && !brand.contains(searchQuery, ignoreCase = true)) return@forEach

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

                                if (onNavigateToAllSizesByBrand != null) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .clickable {
                                                onNavigateToAllSizesByBrand(brand)
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(R.string.action_see_all),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                            contentDescription = stringResource(R.string.action_see_all),
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

        if (categoryGroupedData.values.all { it.isEmpty() }) {
            EmptyListAnimation("size_empty.json")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MySizeContentPreview() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val dummySize = SizeContentUiModel(
        title = "맨투맨",
        sizeLabel = "M",
        isSaved = true,
        onClick = {}
    )

    val dummyCategoryData = mapOf(
        "상의" to mapOf(
            "맨투맨" to listOf(dummySize, dummySize),
            "셔츠" to listOf(dummySize)
        )
    )

    val dummyBrandData = mapOf(
        "무신사" to mapOf(
            "맨투맨" to listOf(dummySize)
        )
    )

    val brandSectionIndices = remember { mutableStateMapOf<String, Int>() }
    val highlightedBrand = remember { mutableStateOf<String?>(null) }

    MySizeTheme {
        MySizeContent(
            listState = listState,
            coroutineScope = coroutineScope,
            brandSectionIndices = brandSectionIndices,
            highlightedBrand = highlightedBrand,
            searchQuery = "",
            onSearchQueryChanged = {},
            categoryGroupedData = dummyCategoryData,
            brandGroupedData = dummyBrandData,
            showSavedOnly = false,
            onSavedSizeChipClick = {},
        )
    }
}
