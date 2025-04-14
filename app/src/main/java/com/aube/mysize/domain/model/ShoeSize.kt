package com.aube.mysize.domain.model

import com.aube.mysize.data.model.size.ShoeSizeEntity
import java.time.LocalDate

data class ShoeSize(
    override val id: Int = 0,
    override val type: String,
    override val brand: String,
    override val sizeLabel: String,
    val footLength: Float?,
    val footWidth: Float?,
    val fit: String?,
    val note: String?,
    override val date: LocalDate
) : Size, ClothSize

fun ShoeSize.toEntity(): ShoeSizeEntity {
    return ShoeSizeEntity(
        id = id,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        footLength = footLength,
        footWidth = footWidth,
        fit = fit,
        note = note,
        date = date
    )
}