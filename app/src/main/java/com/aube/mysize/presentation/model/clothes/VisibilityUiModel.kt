package com.aube.mysize.presentation.model.clothes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.ui.graphics.vector.ImageVector
import com.aube.mysize.domain.model.clothes.MemoVisibility
import com.aube.mysize.domain.model.clothes.Visibility

data class VisibilityUiModel(val label: String, val icon: ImageVector)

fun Visibility.toUi(): VisibilityUiModel = when (this) {
    Visibility.PRIVATE -> VisibilityUiModel("나만 보기", Icons.Default.Lock)
    Visibility.PUBLIC -> VisibilityUiModel("전체 공개", Icons.Default.Public)
}

fun MemoVisibility.toUi(): VisibilityUiModel = when (this) {
    MemoVisibility.PRIVATE -> VisibilityUiModel("비공개", Icons.Default.Lock)
    MemoVisibility.PUBLIC -> VisibilityUiModel("공개", Icons.Default.Public)
}