package com.aube.mysize.domain.model

import com.aube.mysize.data.model.size.AccessorySizeEntity
import java.time.LocalDate

data class AccessorySize(
    override val id: Int = 0,
    override val type: String,           // 반지, 팔찌 등
    override val brand: String,
    override val sizeLabel: String,      // 10호, 14호 등
    val bodyPart: String?,       // 손가락, 손목 등
    val fit: String?,           // 작음/정사이즈/큼 등
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