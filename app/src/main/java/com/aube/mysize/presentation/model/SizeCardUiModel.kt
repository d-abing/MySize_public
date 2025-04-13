package com.aube.mysize.presentation.model

data class BodySizeCardUiModel(
    val title: String,
    val imageResId: Int,
    val description: List<String>
)

data class SizeContentUiModel(
    val title: String,
    val sizeLabel: String,
    val onClick: () -> Unit
)