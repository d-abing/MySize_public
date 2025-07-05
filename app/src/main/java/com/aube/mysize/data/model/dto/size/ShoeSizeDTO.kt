package com.aube.mysize.data.model.dto.size

import androidx.annotation.Keep
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.utils.toLocalDateTime
import com.aube.mysize.utils.toTimestamp
import com.google.firebase.Timestamp

@Keep
data class ShoeSizeDTO(
    override val id: String = "",
    override val uid: String = "",
    val type: String = "",
    val brand: String = "",
    val sizeLabel: String = "",
    val footLength: Float? = null,
    val footWidth: Float? = null,
    val fit: String? = null,
    val note: String? = null,
    val date: Timestamp = Timestamp.now(),
    val entryType: SizeEntryType = SizeEntryType.MY
): SizeDTO {
    override fun toDomain() = ShoeSize(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        footLength = footLength,
        footWidth = footWidth,
        fit = fit,
        note = note,
        date = date.toLocalDateTime(),
        entryType = entryType
    )
}

fun ShoeSize.toDTO(): ShoeSizeDTO {
    return ShoeSizeDTO(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        footLength = footLength,
        footWidth = footWidth,
        fit = fit,
        note = note,
        date = date.toTimestamp(),
        entryType = entryType
    )
}