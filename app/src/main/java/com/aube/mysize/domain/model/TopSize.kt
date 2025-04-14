package com.aube.mysize.domain.model

import com.aube.mysize.data.model.size.TopSizeEntity
import java.time.LocalDate

data class TopSize(
    override val id: Int = 0,
    override val type: String,
    override val brand: String,
    override val sizeLabel: String,
    val shoulder: Float?,
    val chest: Float?,
    val sleeve: Float?,
    val length: Float?,
    val fit: String?,
    val note: String?,
    override val date: LocalDate
) : Size, ClothSize

fun TopSize.toEntity(): TopSizeEntity {
    return TopSizeEntity(
        id = id,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        shoulder = shoulder,
        chest = chest,
        sleeve = sleeve,
        length = length,
        fit = fit,
        note = note,
        date = date
    )
}