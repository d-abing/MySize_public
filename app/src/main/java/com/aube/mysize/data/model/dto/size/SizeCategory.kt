package com.aube.mysize.data.model.dto.size

import com.aube.mysize.domain.model.size.SizeCategory

fun SizeCategory.collectionName(): String = when (this) {
    SizeCategory.BODY -> "body_sizes"
    SizeCategory.TOP -> "top_sizes"
    SizeCategory.BOTTOM -> "bottom_sizes"
    SizeCategory.OUTER -> "outer_sizes"
    SizeCategory.ONE_PIECE -> "one_piece_sizes"
    SizeCategory.SHOE -> "shoe_sizes"
    SizeCategory.ACCESSORY -> "accessory_sizes"
}


val SizeCategory.clazz: Class<out SizeDTO>
    get() = when (this) {
        SizeCategory.TOP -> TopSizeDTO::class.java
        SizeCategory.BOTTOM -> BottomSizeDTO::class.java
        SizeCategory.OUTER -> OuterSizeDTO::class.java
        SizeCategory.ONE_PIECE -> OnePieceSizeDTO::class.java
        SizeCategory.BODY -> BodySizeDTO::class.java
        SizeCategory.SHOE -> ShoeSizeDTO::class.java
        SizeCategory.ACCESSORY -> AccessorySizeDTO::class.java
    }
