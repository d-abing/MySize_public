package com.aube.mysize.domain.model

import com.aube.mysize.data.model.size.OuterSizeEntity
import java.time.LocalDate

data class OuterSize(
    override val id: Int = 0,
    override val type: String,       // 코트, 자켓 등
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

fun OuterSize.toEntity(): OuterSizeEntity {
    return OuterSizeEntity(
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
