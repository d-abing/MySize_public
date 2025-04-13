package com.aube.mysize.domain.model

import com.aube.mysize.data.model.size.OnePieceSizeEntity
import java.time.LocalDate

data class OnePieceSize(
    override val id: Int = 0,
    override val type: String,          // 원피스, 점프수트 등
    override val brand: String,
    override val sizeLabel: String,
    val shoulder: Float?,
    val chest: Float?,
    val waist: Float?,
    val hip: Float?,
    val sleeve: Float?,
    val rise: Float?,          // 밑위
    val thigh: Float?,         // 허벅지 단면
    val hem: Float?,           // 밑단 단면
    val length: Float?,
    val fit: String?,
    val note: String?,
    override val date: LocalDate
): Size, ClothSize

fun OnePieceSize.toEntity(): OnePieceSizeEntity {
    return OnePieceSizeEntity(
        id = id,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        shoulder = shoulder,
        chest = chest,
        waist = waist,
        hip = hip,
        sleeve = sleeve,
        rise = rise,
        thigh = thigh,
        hem = hem,
        length = length,
        fit = fit,
        note = note,
        date = date
    )
}
