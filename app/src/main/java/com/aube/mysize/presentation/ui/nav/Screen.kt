package com.aube.mysize.presentation.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Closet : Screen("closet", "내 옷장", Icons.Filled.Checkroom)
    object Recommend : Screen("recommend", "추천 사이즈", Icons.Default.Star)
    object MySize : Screen("my_size", "내 사이즈", Icons.AutoMirrored.Filled.Assignment)
    object AddSize : Screen("add_size", "사이즈 추가", Icons.Default.AddCircle)
    object Settings : Screen("settings", "설정", Icons.Default.Settings)
}
