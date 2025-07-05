package com.aube.mysize.data.model.dto.size

import androidx.annotation.Keep
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.utils.toLocalDateTime
import com.aube.mysize.utils.toTimestamp
import com.google.firebase.Timestamp

@Keep
data class AccessorySizeDTO(
    override val id: String = "",
    override val uid: String = "",
    val type: String = "",
    val brand: String = "",
    val sizeLabel: String = "",
    val bodyPart: String? = null,
    val fit: String? = null,
    val note: String? = null,
    val date: Timestamp = Timestamp.now(),
    val entryType: SizeEntryType = SizeEntryType.MY
): SizeDTO {
    override fun toDomain() = AccessorySize(
        id = id,
        uid = uid,
        type = type,
        bodyPart = bodyPart,
        sizeLabel = sizeLabel,
        brand = brand,
        note = note,
        fit = fit,
        date = date.toLocalDateTime(),
        entryType = entryType
    )
}

fun AccessorySize.toDTO(): AccessorySizeDTO {
    return AccessorySizeDTO(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        bodyPart = bodyPart,
        fit = fit,
        note = note,
        date = date.toTimestamp(),
        entryType = entryType
    )
}