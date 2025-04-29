package com.aube.mysize.presentation.ui.screens.closet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.ui.screens.closet.component.ClosetViewModeTabs
import com.aube.mysize.presentation.ui.screens.closet.component.ColorGrid
import com.aube.mysize.presentation.ui.screens.closet.component.PictureGrid
import com.aube.mysize.presentation.ui.screens.closet.component.SizeGrid
import com.aube.mysize.presentation.ui.screens.closet.component.TagGrid
import com.aube.mysize.presentation.ui.screens.my_size.component.MySizeTabRow
import com.aube.mysize.presentation.viewmodel.clothes.ClothesViewModel

@Composable
fun ClosetScreen(
    viewModel: ClothesViewModel = hiltViewModel(),
    onClothesClick: (Clothes) -> Unit,
    onNavigateToAddClothes: () -> Unit
) {
    val clothes by viewModel.clothesList.collectAsState()
    val colors = clothes.map { it.dominantColor }.sortedBy { it }

    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedViewMode by remember { mutableIntStateOf(0) }

    var selectedColor by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
        ) {
            MySizeTabRow(
                listOf("내 옷장 보기", "다른 옷장 둘러보기"),
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it }
            )
            HorizontalDivider(thickness = 0.5.dp)

            if (selectedTab == 0) {
                ClosetViewModeTabs(selectedViewMode, onTabSelected = { selectedViewMode = it })

                if (selectedViewMode == 0) {
                    PictureGrid(clothesList = clothes, onClick = onClothesClick)
                } else if (selectedViewMode == 1) {
                    SizeGrid(clothesList = clothes)
                } else if (selectedViewMode == 2) {
                    ColorGrid(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                        colorList = colors,
                        onColorSelected = { selectedColor = it },
                    )
                } else if (selectedViewMode == 3) {
                    TagGrid(clothesList = clothes, onClick = onClothesClick)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {}
            }
        }


        FloatingActionButton(
            onClick = onNavigateToAddClothes,
            containerColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}