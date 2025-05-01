package com.aube.mysize.presentation.ui.screens.closet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.ui.component.chip_tap.MSTabRow
import com.aube.mysize.presentation.ui.screens.closet.component.ClosetViewModeTabs
import com.aube.mysize.presentation.ui.screens.closet.my_closet.ColorGrid
import com.aube.mysize.presentation.ui.screens.closet.my_closet.PictureGrid
import com.aube.mysize.presentation.ui.screens.closet.my_closet.SizeGrid
import com.aube.mysize.presentation.ui.screens.closet.my_closet.TagGrid
import com.aube.mysize.presentation.viewmodel.clothes.ClothesViewModel

@Composable
fun ClosetScreen(
    viewModel: ClothesViewModel = hiltViewModel(),
    onClothesClick: (Clothes) -> Unit,
    onNavigateToAddClothes: () -> Unit
) {
    val clothes by viewModel.clothesList.collectAsState()
    val colors = clothes.map { it.dominantColor }.sortedBy { it }

    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var selectedViewMode by rememberSaveable { mutableIntStateOf(0) }

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        MSTabRow(
            listOf("내 옷장 보기", "다른 옷장 둘러보기"),
            selectedTabIndex = selectedTab,
            onTabSelected = {
                selectedTab = it
                selectedViewMode = 0
            }
        )
        HorizontalDivider(thickness = 0.5.dp)

        if (selectedTab == 0) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    val closetViewModes = listOf(
                        Icons.Default.GridView,       // 사진 보기
                        Icons.Default.Straighten,     // 사이즈 보기
                        Icons.Default.Palette,        // 색상 보기
                        Icons.Default.Tag             // 태그 보기
                    )
                    ClosetViewModeTabs(
                        closetViewModes = closetViewModes,
                        selectedTabIndex = selectedViewMode,
                        onTabSelected = { selectedViewMode = it }
                    )

                    if (selectedViewMode == 0) {
                        PictureGrid(clothesList = clothes, onClick = onClothesClick)
                    } else if (selectedViewMode == 1) {
                        SizeGrid(clothesList = clothes)
                    } else if (selectedViewMode == 2) {
                        ColorGrid(colorList = colors)
                    } else if (selectedViewMode == 3) {
                        TagGrid(clothesList = clothes, onClick = onClothesClick)
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
        } else {
            val closetViewModes = listOf(
                Icons.Default.GridView,       // 사진 보기
                Icons.Default.Favorite,       // 좋아요 보기
                Icons.Default.Tag,            // 태그 보기
                Icons.Default.History         // 최근에 본 옷 보기
            )
            ClosetViewModeTabs(
                closetViewModes = closetViewModes,
                selectedTabIndex = selectedViewMode,
                onTabSelected = { selectedViewMode = it }
            )

            if (selectedViewMode == 0) {
                PictureGrid(clothesList = clothes, onClick = onClothesClick)
            } else if (selectedViewMode == 1) {
                /* TODO */
            } else if (selectedViewMode == 2) {
                /* TODO */
            } else if (selectedViewMode == 3) {
                /* TODO */
            }
        }
    }
}