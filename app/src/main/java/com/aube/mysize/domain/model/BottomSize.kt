package com.aube.mysize.domain.model

import com.aube.mysize.R
import com.aube.mysize.data.model.size.BottomSizeEntity
import com.aube.mysize.presentation.model.SizeCardUiModel
import java.time.LocalDate

data class BottomSize(
    val id: Int = 0,
    val type: String,          // 바지, 슬랙스 등
    val brand: String,
    val sizeLabel: String,
    val waist: Float?,         // 허리 단면
    val rise: Float?,          // 밑위
    val hip: Float?,           // 엉덩이 단면
    val thigh: Float?,         // 허벅지 단면
    val hem: Float?,           // 밑단 단면
    val length: Float?,        // 총장
    val note: String?,
    val date: LocalDate
) : Size

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
        note = note,
        date = date
    )
}

fun BottomSize.toUi(): SizeCardUiModel {
    return SizeCardUiModel(
        title = "하의",
        imageResId = R.drawable.bottom,
        contents = listOfNotNull(
            "종류: $type",
            "브랜드: $brand",
            "사이즈 라벨: $sizeLabel",
            waist?.let { "허리 단면: ${it.toInt()}cm" },
            rise?.let { "밑위: ${it.toInt()}cm" },
            hip?.let { "엉덩이 단면: ${it.toInt()}cm" },
            thigh?.let { "허벅지 단면: ${it.toInt()}cm" },
            hem?.let { "밑단 단면: ${it.toInt()}cm" },
            length?.let { "총장: ${it.toInt()}cm" },
            note?.takeIf { it.isNotBlank() }?.let { "비고: $note" }
        )
    )
}
