package com.aube.mysize.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Recommend : Screen("recommend", "추천", Icons.Filled.ThumbUp)
    object MySize : Screen("my_size", "사이즈", Icons.AutoMirrored.Filled.Assignment)
    object Closet : Screen("closet", "옷장", Icons.Filled.Checkroom)
    object ClothesDetail : Screen("clothes_detail/{id}", "옷 보기", Icons.Filled.Checkroom)
    object UserFeed : Screen("user_feed/{uid}", "사용자 피드", Icons.Filled.Checkroom)
    object EditClothes : Screen("edit_clothes/{id}", "옷 수정", Icons.Filled.Checkroom)
    object AddClothes : Screen("add_clothes", "옷 추가", Icons.Filled.AddCircle)
    object AllSizes : Screen("all_sizes?category={category}&brand={brand}", "전체 사이즈 상세", Icons.AutoMirrored.Filled.Assignment)
    object AddSize : Screen("add_size?category={category}&id={id}", "추가", Icons.Filled.Straighten)
    object MyInfo : Screen("my_info", "내 정보", Icons.Filled.AccountCircle)
    object EditProfile : Screen("edit_profile", "프로필 수정", Icons.Filled.SupervisedUserCircle)
    object EditPassword : Screen("edit_password", "비밀번호 수정", Icons.Filled.SupervisedUserCircle)
    object DeleteAccount : Screen("delete_account", "계정 삭제", Icons.Filled.SupervisedUserCircle)
    object Follow : Screen("follow?type={type}", "팔로우", Icons.Filled.Favorite)
    object BlockedUser : Screen("blocked_user", "차단된 사용자", Icons.Filled.NoAccounts)
    object LanguageSetting : Screen("language_setting", "언어 설정", Icons.Filled.Settings)
}
