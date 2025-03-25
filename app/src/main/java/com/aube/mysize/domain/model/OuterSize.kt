package com.aube.mysize.domain.model

import com.aube.mysize.R
import com.aube.mysize.data.model.size.OuterSizeEntity
import com.aube.mysize.presentation.model.SizeCardUiModel
import java.time.LocalDate

data class OuterSize(
    val id: Int = 0,
    val type: String,       // 코트, 자켓 등
    val brand: String,
    val sizeLabel: String,
    val shoulder: Float?,
    val chest: Float?,
    val length: Float?,
    val sleeve: Float?,
    val fit: String?,
    val note: String?,
    val date: LocalDate
) : Size

fun OuterSize.toEntity(): OuterSizeEntity {
    return OuterSizeEntity(
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

fun OuterSize.toUi(): SizeCardUiModel {
    return SizeCardUiModel(
        title = "아우터",
        imageResId = R.drawable.outer, // 적절한 리소스 추가 필요
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
