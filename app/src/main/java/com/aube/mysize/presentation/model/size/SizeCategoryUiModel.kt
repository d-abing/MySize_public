package com.aube.mysize.presentation.model.size

import com.aube.mysize.domain.model.size.SizeCategory

data class SizeCategoryUiModel(
    val category: SizeCategory,
    val label: String,
    val icon: String
)

fun SizeCategory.toUi(): SizeCategoryUiModel {
    return when (this) {
        SizeCategory.BODY -> SizeCategoryUiModel(this, "신체", "🧍")
        SizeCategory.TOP -> SizeCategoryUiModel(this, "상의", "👕")
        SizeCategory.BOTTOM -> SizeCategoryUiModel(this, "하의", "👖")
        SizeCategory.OUTER -> SizeCategoryUiModel(this, "아우터", "🧥")
        SizeCategory.ONE_PIECE -> SizeCategoryUiModel(this, "일체형", "🩱")
        SizeCategory.SHOE -> SizeCategoryUiModel(this, "신발", "👟")
        SizeCategory.ACCESSORY -> SizeCategoryUiModel(this, "악세사리", "💍")
    }
}

