package com.aube.mysize.presentation.model.clothes

import com.aube.mysize.domain.model.clothes.Clothes

data class ClothesWithAuthor(
    val clothes: Clothes,
    val nickname: String,
)
