package com.aube.mysize.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.ui.graphics.vector.ImageVector

enum class Visibility(val displayName: String, val icon: ImageVector) {
    PRIVATE("나만 보기", Icons.Default.Lock),
    PUBLIC("전체 공개", Icons.Default.Public)
}

enum class MemoVisibility(val displayName: String, val icon: ImageVector) {
    PRIVATE("비공개", Icons.Default.Lock),
    PUBLIC("공개", Icons.Default.Public)
}
