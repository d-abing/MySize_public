package com.aube.mysize.domain.model

import com.aube.mysize.R
import com.aube.mysize.data.model.size.AccessorySizeEntity
import com.aube.mysize.presentation.model.SizeCardUiModel
import java.time.LocalDate

data class AccessorySize(
    val id: Int = 0,
    val type: String,           // 반지, 팔찌 등
    val bodyPart: String,       // 손가락, 손목 등
    val sizeLabel: String,      // 10호, 14호 등
    val brand: String?,
    val note: String?,
    val date: LocalDate
): Size

fun AccessorySize.toEntity(): AccessorySizeEntity {
    return AccessorySizeEntity(
        id = id,
        type = type,
        bodyPart = bodyPart,
        sizeLabel = sizeLabel,
        brand = brand,
        note = note,
        date = date
    )
}

fun AccessorySize.toUi(): SizeCardUiModel {
    return SizeCardUiModel(
        title = "악세사리",
        imageResId = R.drawable.accessory,
        contents = listOfNotNull(
            "종류: $type",
            "착용 부위: $bodyPart",
            "사이즈 라벨: $sizeLabel",
            brand?.takeIf { it.isNotBlank() }?.let { "브랜드: $brand" },
            note?.takeIf { it.isNotBlank() }?.let { "비고: $note" }
        )
    )
}
