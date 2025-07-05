package com.aube.mysize.presentation.model.clothes

import com.aube.mysize.domain.model.clothes.Clothes

sealed class PictureGridItem {
    data class ClothesItem(val clothes: Clothes) : PictureGridItem()
    object AdItem : PictureGridItem()
}
