package com.aube.mysize.domain.model

import com.aube.mysize.R
import com.aube.mysize.data.model.size.ShoeSizeEntity
import com.aube.mysize.presentation.model.SizeCardUiModel
import java.time.LocalDate

data class ShoeSize(
    val id: Int = 0,
    val type: String,
    val brand: String,
    val sizeLabel: String,      // 245mm, US 9 등
    val footLength: Float?,     // 발 길이
    val footWidth: Float?,      // 발볼
    val fit: String?,           // 작음/정사이즈/큼 등
    val note: String?,
    val date: LocalDate
) : Size

fun ShoeSize.toEntity(): ShoeSizeEntity {
    return ShoeSizeEntity(
        id = id,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        footLength = footLength,
        footWidth = footWidth,
        fit = fit,
        note = note,
        date = date
    )
}

fun ShoeSize.toUi(): SizeCardUiModel {
    return SizeCardUiModel(
        title = "신발",
        imageResId = R.drawable.shoes,
        contents = listOfNotNull(
            "종류: $type",
            "브랜드: $brand",
            "사이즈 라벨: $sizeLabel",
            footLength?.let { "발 길이: ${it.toInt()}mm" },
            footWidth?.let { "발볼: ${it.toInt()}mm" },
            fit?.takeIf { it.isNotBlank() }?.let { "핏: $fit" },
            note?.takeIf { it.isNotBlank() }?.let { "비고: $note" }
        )
    )
}
