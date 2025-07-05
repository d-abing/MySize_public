package com.aube.mysize.domain.model.clothes

import androidx.annotation.Keep

@Keep
data class LinkedSizeGroup(
    val category: String = "",
    val sizeIds: List<String> = emptyList()
)
