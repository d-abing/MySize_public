package com.aube.mysize.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Recommend : Screen("recommend", "추천", Icons.Filled.ThumbUp)
    object MySize : Screen("my_size", "사이즈", Icons.AutoMirrored.Filled.Assignment)
    object Closet : Screen("closet", "옷장", Icons.Filled.Checkroom)
    object ClothesDetail : Screen("clothes_detail/{id}", "옷 보기", Icons.Filled.Checkroom)
    object ClothesModify : Screen("clothes_modify/{id}", "옷 수정", Icons.Filled.Checkroom)
    object AddClothes : Screen("add_clothes", "옷 추가", Icons.Filled.AddCircle)
    object FullDetail : Screen("full_detail?category={category}&brand={brand}", "전체 사이즈 상세", Icons.AutoMirrored.Filled.Assignment)
    object AddSize : Screen("add_size?category={category}&id={id}", "추가", Icons.Filled.Straighten)
    object Settings : Screen("settings", "설정", Icons.Default.Settings)
}
