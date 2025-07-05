package com.aube.mysize.data.model.dto.size

import androidx.annotation.Keep
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.utils.toLocalDateTime
import com.aube.mysize.utils.toTimestamp
import com.google.firebase.Timestamp

@Keep
data class OuterSizeDTO(
    override val id: String = "",
    override val uid: String = "",
    val type: String = "",
    val brand: String = "",
    val sizeLabel: String = "",
    val shoulder: Float? = null,
    val chest: Float? = null,
    val sleeve: Float? = null,
    val length: Float? = null,
    val fit: String? = null,
    val note: String? = null,
    val date: Timestamp = Timestamp.now(),
    val entryType: SizeEntryType = SizeEntryType.MY
): SizeDTO {
    override fun toDomain() = OuterSize(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        shoulder = shoulder,
        chest = chest,
        sleeve = sleeve,
        length = length,
        fit = fit,
        note = note,
        date = date.toLocalDateTime(),
        entryType = entryType
    )
}

fun OuterSize.toDTO(): OuterSizeDTO {
    return OuterSizeDTO(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        shoulder = shoulder,
        chest = chest,
        sleeve = sleeve,
        length = length,
        fit = fit,
        note = note,
        date = date.toTimestamp(),
        entryType = entryType
    )
}
