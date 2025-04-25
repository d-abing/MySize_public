package com.aube.mysize.presentation.ui.screens.closet.add_clothes

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.ui.component.closet.BigSizeButton
import java.time.format.DateTimeFormatter

@Composable
fun ColumnScope.AddClothesStepTwo(
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    onePieceSizes: List<OnePieceSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    isOpenInFullMode: Boolean,
    onOpenInFullClick: () -> Unit,
    selectedIds: Map<String, Int>,
    selectedCategory: SizeCategory,
    onSelectedIdsChanged: (Map<String, Int>) -> Unit,
    onSelectedCategoryChanged: (SizeCategory) -> Unit,
    onAddNewSize: (SizeCategory) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd")

    val sizeItems = remember(
        topSizes, bottomSizes, outerSizes, onePieceSizes, shoeSizes, accessorySizes, selectedCategory
    ) {
        when (selectedCategory) {

            SizeCategory.TOP -> topSizes.map { size ->
                val title = "${size.type} ${size.sizeLabel} - ${size.brand}"
                val sizeLabel = listOfNotNull(
                    size.date.format(formatter),
                    size.shoulder?.let { "어깨 너비: ${it}cm" },
                    size.chest?.let { "가슴 단면: ${it}cm" },
                    size.sleeve?.let { "소매 길이: ${it}cm" },
                    size.length?.let { "총장: ${it}cm" },
                    size.fit?.let { "핏: $it" },
                    size.note?.let { "메모: $it" },
                ).take(5).joinToString("\n")
                size.id to (title to sizeLabel)
            }

            SizeCategory.BOTTOM -> bottomSizes.map { size ->
                val title = "${size.type} ${size.sizeLabel} - ${size.brand}"
                val sizeLabel = listOfNotNull(
                    size.date.format(formatter),
                    size.waist?.let { "허리 단면: ${it}cm" },
                    size.rise?.let { "밑위: ${it}cm" },
                    size.hip?.let { "엉덩이 단면: ${it}cm" },
                    size.thigh?.let { "허벅지 단면: ${it}cm" },
                    size.hem?.let { "밑단 단면: ${it}cm" },
                    size.length?.let { "총장: ${it}cm" },
                    size.fit?.let { "핏: $it" },
                    size.note?.let { "메모: $it" },
                ).take(5).joinToString("\n")
                size.id to (title to sizeLabel)
            }

            SizeCategory.OUTER -> outerSizes.map { size ->
                val title = "${size.type} ${size.sizeLabel} - ${size.brand}"
                val sizeLabel = listOfNotNull(
                    size.date.format(formatter),
                    size.shoulder?.let { "어깨 너비: ${it}cm" },
                    size.chest?.let { "가슴 단면: ${it}cm" },
                    size.sleeve?.let { "소매 길이: ${it}cm" },
                    size.length?.let { "총장: ${it}cm" },
                    size.fit?.let { "핏: $it" },
                    size.note?.let { "메모: $it" },
                ).take(5).joinToString("\n")
                size.id to (title to sizeLabel)
            }

            SizeCategory.ONE_PIECE -> onePieceSizes.map { size ->
                val title = "${size.type} ${size.sizeLabel} - ${size.brand}"
                val sizeLabel = listOfNotNull(
                    size.date.format(formatter),
                    size.shoulder?.let { "어깨 너비: ${it}cm" },
                    size.chest?.let { "가슴 단면: ${it}cm" },
                    size.waist?.let { "허리 단면: ${it}cm" },
                    size.hip?.let { "엉덩이 단면: ${it}cm" },
                    size.sleeve?.let { "소매 길이: ${it}cm" },
                    size.rise?.let { "밑위: ${it}cm" },
                    size.thigh?.let { "허벅지 단면: ${it}cm" },
                    size.hem?.let { "밑단 단면: ${it}cm" },
                    size.length?.let { "총장: ${it}cm" },
                    size.fit?.let { "핏: ${it}" },
                    size.note?.let { "메모: ${it}" }
                ).take(5).joinToString("\n")
                size.id to (title to sizeLabel)
            }

            SizeCategory.SHOE -> shoeSizes.map { size ->
                val title = "${size.type} ${size.sizeLabel} - ${size.brand}"
                val sizeLabel = listOfNotNull(
                    size.date.format(formatter),
                    size.footLength?.let { "발 길이: ${it}cm" },
                    size.footWidth?.let { "발볼 너비: ${it}cm" },
                    size.fit?.let { "핏: ${it}" },
                    size.note?.let { "메모: ${it}" }
                ).take(5).joinToString("\n")
                size.id to (title to sizeLabel)
            }

            SizeCategory.ACCESSORY -> accessorySizes.map { size ->
                val title = "${size.type} ${size.sizeLabel} - ${size.brand}"
                val sizeLabel = listOfNotNull(
                    size.date.format(formatter),
                    size.bodyPart?.let { "착용 부위: $it" },
                    size.fit?.let { "핏: ${it}" },
                    size.note?.let { "메모: ${it}" }
                ).take(5).joinToString("\n")
                size.id to (title to sizeLabel)
            }

            else -> emptyList()
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
            .then(
                if(!isOpenInFullMode) Modifier.padding(top = 8.dp, bottom = 4.dp)
                else Modifier.padding(bottom = 4.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "사이즈 추가", style = MaterialTheme.typography.bodyLarge)

        Icon(
            imageVector = Icons.Default.OpenInFull,
            contentDescription = "전체보기",
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically)
                .clickable { onOpenInFullClick() }
        )
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(SizeCategory.entries.filter { it != SizeCategory.BODY }) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onSelectedCategoryChanged(category) },
                label = { Text(category.label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color.Black,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color.DarkGray
                ),
            )
        }
    }

    Column(modifier = Modifier.weight(1f).padding(top = 8.dp)) {
        if (isOpenInFullMode) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(sizeItems.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { (id, pair) ->
                            val isSelected = selectedIds[selectedCategory.name] == id

                            BigSizeButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .background(
                                        color =
                                        if(isSelected) MaterialTheme.colorScheme.secondary
                                        else Color.White,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                title = pair.first,
                                sizeLabel = pair.second,
                                onClick = {
                                    val categoryKey = selectedCategory.name

                                    onSelectedIdsChanged(
                                        selectedIds.toMutableMap().apply {
                                            if (isSelected) remove(categoryKey)
                                            else put(categoryKey, id)
                                        }
                                    )
                                }
                            )
                        }

                        if (rowItems.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                sizeItems.forEach { (id, pair) ->
                    val isSelected = selectedIds[selectedCategory.name] == id

                    item {
                        BigSizeButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .background(
                                    color =
                                    if(isSelected) MaterialTheme.colorScheme.secondary
                                    else Color.White,
                                    shape = RoundedCornerShape(16.dp)
                                )
                            ,
                            title = pair.first,
                            onClick = {
                                val categoryKey = selectedCategory.name

                                onSelectedIdsChanged(
                                    if (isSelected) {
                                        selectedIds - categoryKey
                                    } else {
                                        selectedIds + (categoryKey to id)
                                    }
                                )
                            },
                        )
                    }
                }
            }
        }
    }

    TextButton(
        onClick = { onAddNewSize(selectedCategory) },
        modifier = Modifier
            .padding(vertical = 4.dp)
            .wrapContentHeight(),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            "새로운 사이즈 등록하기")
    }

    Spacer(modifier = Modifier.height(8.dp))

    // 이전 / 다음 버튼
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Button(
            onClick = onPrevious,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        ) {
            Text("이전")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = onNext,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        ) {
            Text("다음")
        }
    }
}

