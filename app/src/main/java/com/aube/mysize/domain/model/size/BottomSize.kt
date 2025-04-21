package com.aube.mysize.domain.model.size

import com.aube.mysize.data.model.size.BottomSizeEntity
import java.time.LocalDate

data class BottomSize(
    override val id: Int = 0,
    override val type: String,
    override val brand: String,
    override val sizeLabel: String,
    val waist: Float?,
    val rise: Float?,
    val hip: Float?,
    val thigh: Float?,
    val hem: Float?,
    val length: Float?,
    val fit: String?,
    val note: String?,
    override val date: LocalDate
) : Size, ClothesSize

fun BottomSize.toEntity(): BottomSizeEntity {
    return BottomSizeEntity(
        id = id,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        waist = waist,
        rise = rise,
        hip = hip,
        thigh = thigh,
        hem = hem,
        length = length,
        fit = fit,
        note = note,
        date = date
    )
}
