package com.aube.mysize.domain.model

import com.aube.mysize.data.model.size.BottomSizeEntity
import java.time.LocalDate

data class BottomSize(
    override val id: Int = 0,
    override val type: String,          // 바지, 슬랙스 등
    override val brand: String,
    override val sizeLabel: String,
    val waist: Float?,         // 허리 단면
    val rise: Float?,          // 밑위
    val hip: Float?,           // 엉덩이 단면
    val thigh: Float?,         // 허벅지 단면
    val hem: Float?,           // 밑단 단면
    val length: Float?,        // 총장
    val fit: String?,
    val note: String?,
    override val date: LocalDate
) : Size, ClothSize

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
