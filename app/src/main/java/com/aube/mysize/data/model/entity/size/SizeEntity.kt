package com.aube.mysize.data.model.entity.size

import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.domain.model.size.TopSize
import java.time.LocalDateTime

interface SizeEntity {
    val id: String
    val uid: String
    val date: LocalDateTime
    val entryType: SizeEntryType
}

fun SizeEntity.toDomain(): Size {
    return when (this) {
        is TopSizeEntity -> this.toDomain()
        is BottomSizeEntity -> this.toDomain()
        is OuterSizeEntity -> this.toDomain()
        is OnePieceSizeEntity -> this.toDomain()
        is ShoeSizeEntity -> this.toDomain()
        is AccessorySizeEntity -> this.toDomain()
        is BodySizeEntity -> this.toDomain()
        else -> throw IllegalArgumentException("Unsupported entity type: ${this::class.simpleName}")
    }
}

fun Size.toEntity(): SizeEntity {
    return when (this) {
        is TopSize -> this.toEntity()
        is BottomSize -> this.toEntity()
        is OuterSize -> this.toEntity()
        is OnePieceSize -> this.toEntity()
        is ShoeSize -> this.toEntity()
        is AccessorySize -> this.toEntity()
        is BodySize -> this.toEntity()
        else -> throw IllegalArgumentException("Unsupported Size type for toEntity: ${this::class.simpleName}")
    }
}