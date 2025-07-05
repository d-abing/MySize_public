package com.aube.mysize.presentation.model.size

import com.aube.mysize.domain.model.size.SizeCategory

data class SizeSummaryItem(
    val summary: String,
    val category: SizeCategory,
    val sizeId: String
)