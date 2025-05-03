package com.aube.mysize.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BodySizeCardUiModel(
    val id: Int,
    val title: String,
    val imageVector: ImageVector,
    val description: Map<String, String?>
)

data class SizeContentUiModel(
    val title: String,
    val sizeLabel: String,
    val onClick: () -> Unit
)