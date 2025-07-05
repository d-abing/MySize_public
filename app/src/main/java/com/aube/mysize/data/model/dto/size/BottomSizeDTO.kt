package com.aube.mysize.data.model.dto.size

import androidx.annotation.Keep
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.utils.toLocalDateTime
import com.aube.mysize.utils.toTimestamp
import com.google.firebase.Timestamp

@Keep
data class BottomSizeDTO(
    override val id: String = "",
    override val uid: String = "",
    val type: String = "",
    val brand: String = "",
    val sizeLabel: String = "",
    val waist: Float? = null,
    val rise: Float? = null,
    val hip: Float? = null,
    val thigh: Float? = null,
    val hem: Float? = null,
    val length: Float? = null,
    val fit: String? = null,
    val note: String? = null,
    val date: Timestamp = Timestamp.now(),
    val entryType: SizeEntryType = SizeEntryType.MY
): SizeDTO {
    override fun toDomain() = BottomSize(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        waist = waist,
        rise = rise,
        hip = hip,
        thigh = thigh,
        hem = hem,
        length = length,
        fit = fit,
        note = note,
        date = date.toLocalDateTime(),
        entryType = entryType
    )
}

fun BottomSize.toDTO(): BottomSizeDTO {
    return BottomSizeDTO(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        waist = waist,
        rise = rise,
        hip = hip,
        thigh = thigh,
        hem = hem,
        length = length,
        fit = fit,
        note = note,
        date = date.toTimestamp(),
        entryType = entryType
    )
}