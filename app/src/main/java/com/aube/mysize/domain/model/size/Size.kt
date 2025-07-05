package com.aube.mysize.domain.model.size

import java.time.LocalDateTime

interface Size {
    val id: String
    val uid: String
    val date: LocalDateTime
}

interface ClothesSize : Size {
    val type: String
    val brand: String
    val sizeLabel: String
    val entryType: SizeEntryType
}

fun Size.withId(newId: String): Size {
    return when (this) {
        is TopSize -> this.copy(id = newId)
        is BottomSize -> this.copy(id = newId)
        is OuterSize -> this.copy(id = newId)
        is ShoeSize -> this.copy(id = newId)
        is OnePieceSize -> this.copy(id = newId)
        is AccessorySize -> this.copy(id = newId)
        is BodySize -> this.copy(id = newId)

        else -> throw IllegalArgumentException("Unsupported Size type: ${this::class.simpleName}")
    }
}

fun Size.resolveCategory(): SizeCategory = when (this) {
    is TopSize -> SizeCategory.TOP
    is BottomSize -> SizeCategory.BOTTOM
    is OuterSize -> SizeCategory.OUTER
    is OnePieceSize -> SizeCategory.ONE_PIECE
    is ShoeSize -> SizeCategory.SHOE
    is AccessorySize -> SizeCategory.ACCESSORY
    is BodySize -> SizeCategory.BODY
    else -> error("Unknown size type: ${this::class.simpleName}")
}