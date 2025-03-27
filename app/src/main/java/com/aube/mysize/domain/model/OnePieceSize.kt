package com.aube.mysize.domain.model

import com.aube.mysize.R
import com.aube.mysize.data.model.size.OnePieceSizeEntity
import com.aube.mysize.presentation.model.SizeCardUiModel
import java.time.LocalDate

data class OnePieceSize(
    val id: Int = 0,
    val type: String,          // 원피스, 점프수트 등
    val brand: String,
    val sizeLabel: String,
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
    val date: LocalDate
): Size

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

fun OnePieceSize.toUi(): SizeCardUiModel {
    return SizeCardUiModel(
        title = "일체형",
        imageResId = R.drawable.onepiece,
        contents = listOfNotNull(
            "종류: $type",
            "브랜드: $brand",
            "사이즈 라벨: $sizeLabel",
            shoulder?.let { "어깨 단면: ${it.toInt()}cm" },
            chest?.let { "가슴 단면: ${it.toInt()}cm" },
            waist?.let { "허리 단면: ${it.toInt()}cm" },
            hip?.let { "엉덩이 단면: ${it.toInt()}cm" },
            sleeve?.let { "소매 길이: ${it.toInt()}cm" },
            rise?.let { "밑위: ${it.toInt()}cm" },
            thigh?.let { "허벅지 단면: ${thigh.toInt()}cm" },
            hem?.let { "밑단 단면: ${it.toInt()}cm" },
            length?.let { "총장: ${it.toInt()}cm" },
            fit?.takeIf { it.isNotBlank() }?.let { "핏: $fit" },
            note?.takeIf { it.isNotBlank() }?.let { "비고: $note" }
        )
    )
}