package com.aube.mysize.domain.model

import com.aube.mysize.data.model.size.TopSizeEntity
import java.time.LocalDate

data class TopSize(
    override val id: Int = 0,
    override val type: String,         // 옷 종류 (티셔츠, 셔츠 등)
    override val brand: String,
    override val sizeLabel: String,    // 예: M, L, 95
    val shoulder: Float?,     // 어깨 너비
    val chest: Float?,        // 가슴 단면
    val sleeve: Float?,       // 소매 길이
    val length: Float?,       // 총장
    val fit: String?,         // 슬림, 오버핏 등
    val note: String?,        // 착용감 등 자유기록
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