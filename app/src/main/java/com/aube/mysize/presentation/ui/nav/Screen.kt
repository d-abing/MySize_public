package com.aube.mysize.presentation.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Closet : Screen("closet", "옷장", Icons.Filled.Checkroom)
    object AddCloth : Screen("add_cloth", "옷 추가", Icons.Filled.AddCircle)
    object Recommend : Screen("recommend", "추천", Icons.Filled.ThumbUp)
    object MySize : Screen("my_size", "마이 사이즈", Icons.AutoMirrored.Filled.Assignment)
    object AddSize : Screen("add_size", "추가", Icons.Filled.Straighten)
    object Settings : Screen("settings", "설정", Icons.Default.Settings)
}
