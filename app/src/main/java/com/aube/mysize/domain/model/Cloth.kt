package com.aube.mysize.domain.model

import com.aube.mysize.data.model.size.ClothEntity
import java.time.LocalDate

data class Cloth(
    val id: Int = 0,
    val imageBytes: ByteArray,
    val hash: String,
    val dominantColor: Int,
    val linkedSizeId: Int,
    val sizeCategory: String,
    val date: LocalDate,
    val memo: String? = null,
    val isPublic: Boolean = false
)

fun Cloth.toEntity(): ClothEntity {
    return ClothEntity(
        id = id,
        imageBytes = imageBytes,
        hash = hash,
        dominantColor = dominantColor,
        linkedSizeId = linkedSizeId,
        sizeCategory = sizeCategory,
        date = date,
        memo = memo,
        isPublic = isPublic
    )
}