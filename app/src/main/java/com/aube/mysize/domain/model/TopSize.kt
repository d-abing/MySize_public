package com.aube.mysize.domain.model

import com.aube.mysize.R
import com.aube.mysize.data.model.size.TopSizeEntity
import com.aube.mysize.presentation.model.SizeCardUiModel
import java.time.LocalDate

data class TopSize(
    val id: Int = 0,
    val type: String,         // 옷 종류 (티셔츠, 셔츠 등)
    val brand: String,
    val sizeLabel: String,    // 예: M, L, 95
    val shoulder: Float?,     // 어깨 너비
    val chest: Float?,        // 가슴 단면
    val length: Float?,       // 총장
    val sleeve: Float?,       // 소매 길이
    val fit: String?,         // 슬림, 오버핏 등
    val note: String?,        // 착용감 등 자유기록
    val date: LocalDate
) : Size

fun TopSize.toEntity(): TopSizeEntity {
    return TopSizeEntity(
        id = id,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        shoulder = shoulder,
        chest = chest,
        length = length,
        sleeve = sleeve,
        fit = fit,
        note = note,
        date = date
    )
}

fun TopSize.toUi(): SizeCardUiModel {
    return SizeCardUiModel(
        title = "상의",
        imageResId = R.drawable.top, // 적절한 이미지 리소스 사용
        contents = listOfNotNull(
            "종류: $type",
            "브랜드: $brand",
            "사이즈 라벨: $sizeLabel",
            shoulder?.let { "어깨 사이즈: ${it.toInt()}cm" },
            chest?.let { "가슴 단면: ${it.toInt()}cm" },
            length?.let { "총장: ${it.toInt()}cm" },
            sleeve?.let { "소매 길이: ${it.toInt()}cm" },
            fit?.takeIf { it.isNotBlank() }?.let { "핏: $fit" },
            note?.takeIf { it.isNotBlank() }?.let { "비고: $note" }
        )
    )
}
