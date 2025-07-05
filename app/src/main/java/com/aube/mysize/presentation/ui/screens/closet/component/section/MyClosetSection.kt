package com.aube.mysize.presentation.ui.screens.closet.component.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.ui.component.EmptyListAnimation
import com.aube.mysize.presentation.ui.screens.closet.component.ClosetViewModeTabs
import com.aube.mysize.presentation.ui.screens.closet.component.closet_grid.ColorGrid
import com.aube.mysize.presentation.ui.screens.closet.component.closet_grid.PictureGrid
import com.aube.mysize.presentation.ui.screens.closet.component.closet_grid.SizeGrid
import com.aube.mysize.presentation.ui.screens.closet.component.closet_grid.TagGrid
import com.aube.mysize.presentation.viewmodel.clothes.MyClothesViewModel

@Composable
fun MyClosetSection(
    myClothesViewModel: MyClothesViewModel,
    isNetworkConnected: Boolean,
    selectedViewMode: Int,
    onViewModeChange: (Int) -> Unit,
    onClothesClick: (Clothes) -> Unit,
    onNavigateToAddClothes: () -> Unit
) {
    val myClothes by myClothesViewModel.myClothes.collectAsState()
    val isUploading by myClothesViewModel.isUploading.collectAsState()
    val colors = myClothes.map { it.dominantColor }.sortedBy { it }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ClosetViewModeTabs(
                closetViewModes = listOf(
                    Icons.Default.GridView,
                    Icons.Default.Straighten,
                    Icons.Default.Palette,
                    Icons.Default.Tag
                ),
                selectedTabIndex = selectedViewMode,
                onTabSelected = onViewModeChange
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedViewMode) {
                    0 -> PictureGrid(myClothes, onClothesClick)
                    1 -> SizeGrid(clothesList = myClothes)
                    2 -> ColorGrid(colors)
                    3 -> TagGrid(myClothes, onClothesClick)
                }
            }
        }

        if (myClothes.isEmpty()) {
            EmptyListAnimation("closet_empty.json")
        }

        if (isUploading) {
            Box(
                Modifier.fillMaxSize().background(Color(0x33000000)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(24.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            }
        }

        if (isNetworkConnected) {
            FloatingActionButton(
                onClick = onNavigateToAddClothes,
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(20.dp).align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.action_add))
            }
        }
    }
}
