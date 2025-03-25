package com.aube.mysize.presentation.model

data class SizeCardUiModel(
    val title: String,             // 예: "신체", "상의"
    val imageResId: Int,           // 예: R.drawable.body_image
    val contents: List<String> // 예: ["키: 170cm", "몸무게: 60kg", ...]
)
