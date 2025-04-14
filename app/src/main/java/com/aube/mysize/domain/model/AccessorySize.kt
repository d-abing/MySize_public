package com.aube.mysize.domain.model

import com.aube.mysize.data.model.size.AccessorySizeEntity
import java.time.LocalDate

data class AccessorySize(
    override val id: Int = 0,
    override val type: String,
    override val brand: String,
    override val sizeLabel: String,
    val bodyPart: String?,
    val fit: String?,
    val note: String?,
    override val date: LocalDate
): Size, ClothSize

fun AccessorySize.toEntity(): AccessorySizeEntity {
    return AccessorySizeEntity(
        id = id,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        bodyPart = bodyPart,
        fit = fit,
        note = note,
        date = date
    )
}