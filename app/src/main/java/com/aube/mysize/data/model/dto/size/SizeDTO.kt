package com.aube.mysize.data.model.dto.size

import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.TopSize

interface SizeDTO {
    val id: String
    val uid: String
    fun toDomain(): Size
}

fun Size.toDTO(): Any {
    return when (this) {
        is TopSize -> this.toDTO()
        is BottomSize -> this.toDTO()
        is OuterSize -> this.toDTO()
        is OnePieceSize -> this.toDTO()
        is ShoeSize -> this.toDTO()
        is AccessorySize -> this.toDTO()
        is BodySize -> this.toDTO()
        else -> throw IllegalArgumentException("Unsupported Size type for toDTO: ${this::class.simpleName}")
    }
}