package com.aube.mysize.presentation.ui.screens.closet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.Cloth
import com.aube.mysize.presentation.ui.component.closet.ClothCard
import com.aube.mysize.presentation.ui.component.mysize.MySizeTabRow
import com.aube.mysize.presentation.viewmodel.cloth.ClothViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClosetScreen(
    viewModel: ClothViewModel = hiltViewModel(),
    onClothClick: (Cloth) -> Unit,
    onNavigateToAddCloth: () -> Unit
) {
    val cloths by viewModel.clothList.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }

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

            // 3. 종류별 보기 or 브랜드별 보기
            if (selectedTab == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {}

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {
                    items(cloths) { cloth ->
                        ClothCard(cloth = cloth, onClick = { onClothClick(cloth) })
                    }
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
            onClick = onNavigateToAddCloth,
            containerColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}