package com.aube.mysize.presentation.ui.screens.closet.add_clothes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.aube.mysize.presentation.model.size.toUi
import com.aube.mysize.presentation.ui.screens.closet.component.BigSizeButton
import com.aube.mysize.ui.theme.MySizeTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ColumnScope.AddClothesStepTwo(
    allSizes: Map<SizeCategory, List<Size>>,
    isOpenInFullMode: Boolean,
    onOpenInFullClick: () -> Unit,
    selectedIds: Map<SizeCategory, List<String>>,
    selectedCategory: SizeCategory,
    onSelectedIdsChanged: (Map<SizeCategory, List<String>>) -> Unit,
    onSelectedCategoryChanged: (SizeCategory) -> Unit,
    onAddNewSize: (SizeCategory) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd")
    val sizeList = (allSizes[selectedCategory].orEmpty().filterIsInstance<ClothesSize>())

    val sizeItems = remember(sizeList) {
        sizeList.map { size ->
            val title = "${size.type} ${size.sizeLabel} - ${size.brand}"
            val detail = when (size) {
                is TopSize -> listOfNotNull(
                    size.date.format(formatter),
                    size.shoulder?.let { "어깨 너비: $it cm" },
                    size.chest?.let { "가슴 단면: $it cm" },
                    size.sleeve?.let { "소매 길이: $it cm" },
                    size.length?.let { "총장: $it cm" },
                    size.fit?.let { "핏: $it" },
                    size.note?.let { "메모: $it" },
                )
                is BottomSize -> listOfNotNull(
                    size.date.format(formatter),
                    size.waist?.let { "허리 단면: $it cm" },
                    size.rise?.let { "밑위: $it cm" },
                    size.hip?.let { "엉덩이 단면: $it cm" },
                    size.length?.let { "총장: $it cm" },
                )
                is OuterSize -> listOfNotNull(
                    size.date.format(formatter),
                    size.shoulder?.let { "어깨 너비: $it cm" },
                    size.chest?.let { "가슴 단면: $it cm" },
                    size.length?.let { "총장: $it cm" },
                )
                is OnePieceSize -> listOfNotNull(
                    size.date.format(formatter),
                    size.waist?.let { "허리: $it cm" },
                    size.hip?.let { "엉덩이: $it cm" },
                    size.length?.let { "총장: $it cm" }
                )
                is ShoeSize -> listOfNotNull(
                    size.date.format(formatter),
                    size.footLength?.let { "발 길이: $it cm" },
                    size.footWidth?.let { "발볼 너비: $it cm" },
                )
                is AccessorySize -> listOfNotNull(
                    size.date.format(formatter),
                    size.bodyPart?.let { "착용 부위: $it" },
                )
                else -> emptyList()
            }.take(5).joinToString("\n")

            size.id to (title to detail)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if(isOpenInFullMode) Modifier.padding(bottom = 8.dp)
                else Modifier.padding(vertical = 8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.text_add_size_title), style = MaterialTheme.typography.bodyLarge)
        Icon(
            imageVector = Icons.Default.OpenInFull,
            contentDescription = stringResource(R.string.label_view_full),
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically)
                .clickable { onOpenInFullClick() }
        )
    }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(SizeCategory.entries.filter { it != SizeCategory.BODY }) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onSelectedCategoryChanged(category) },
                label = { Text(category.toUi().label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color.Black,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color.DarkGray
                ),
            )
        }
    }

    Column(modifier = Modifier.weight(1f).padding(top = 4.dp)) {
        val selectedList = selectedIds[selectedCategory].orEmpty()
        if (isOpenInFullMode) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(sizeItems.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { (id, pair) ->
                            val isSelected = id in selectedList
                            BigSizeButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f),
                                title = pair.first,
                                sizeDetail = pair.second,
                                isSelected = isSelected,
                                onClick = {
                                    val newList = if (isSelected) selectedList - id else selectedList + id
                                    val newMap = selectedIds.toMutableMap()
                                    if (newList.isEmpty()) newMap.remove(selectedCategory)
                                    else newMap[selectedCategory] = newList
                                    onSelectedIdsChanged(newMap)
                                }
                            )
                        }
                        if (rowItems.size < 2) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Top
            ) {
                items(sizeItems) { (id, pair) ->
                    val isSelected = id in selectedList
                    BigSizeButton(
                        title = pair.first,
                        sizeDetail = pair.second,
                        isSelected = isSelected,
                        onClick = {
                            val newList = if (isSelected) selectedList - id else selectedList + id
                            val newMap = selectedIds.toMutableMap()
                            if (newList.isEmpty()) newMap.remove(selectedCategory)
                            else newMap[selectedCategory] = newList
                            onSelectedIdsChanged(newMap)
                        }
                    )
                }
            }
        }
    }

    TextButton(
        onClick = { onAddNewSize(selectedCategory) },
        modifier = Modifier.padding(vertical = 4.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.action_add))
        Spacer(modifier = Modifier.width(4.dp))
        Text(stringResource(R.string.action_register_new_size))
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = onPrevious,
            modifier = Modifier.weight(1f).height(50.dp)
        ) { Text(stringResource(R.string.action_previous)) }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onNext,
            modifier = Modifier.weight(1f).height(50.dp)
        ) { Text(stringResource(R.string.action_next)) }
    }
}

@Preview(showBackground = true)
@Composable
fun AddClothesStepTwoPreview() {
    val dummyTopSize = TopSize(
        id = "top123",
        uid = "123",
        type = "맨투맨",
        brand = "TestBrand",
        sizeLabel = "M",
        shoulder = 45.0f,
        chest = 50.0f,
        sleeve = 60.0f,
        length = 70.0f,
        fit = "Regular",
        note = "편안함",
        date = LocalDateTime.now(),
        entryType = SizeEntryType.MY
    )

    val allSizes = mapOf(
        SizeCategory.TOP to listOf(dummyTopSize)
    )

    val selectedIds = mapOf(
        SizeCategory.TOP to listOf("top123")
    )

    MySizeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AddClothesStepTwo(
                allSizes = allSizes,
                isOpenInFullMode = false,
                onOpenInFullClick = {},
                selectedIds = selectedIds,
                selectedCategory = SizeCategory.TOP,
                onSelectedIdsChanged = {},
                onSelectedCategoryChanged = {},
                onAddNewSize = {},
                onPrevious = {},
                onNext = {}
            )
        }
    }
}
