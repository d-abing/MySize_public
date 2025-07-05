package com.aube.mysize.data.model.entity.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.SizeEntryType
import java.time.LocalDateTime

@Entity(tableName = "one_piece_size")
data class OnePieceSizeEntity(
    @PrimaryKey override val id: String,
    override val uid: String,
    val type: String,
    val brand: String,
    val sizeLabel: String,
    val shoulder: Float?,
    val chest: Float?,
    val waist: Float?,
    val hip: Float?,
    val sleeve: Float?,
    val rise: Float?,
    val thigh: Float?,
    val hem: Float?,
    val length: Float?,
    val fit: String?,
    val note: String?,
    override val date: LocalDateTime,
    override val entryType: SizeEntryType = SizeEntryType.MY
): SizeEntity

fun OnePieceSizeEntity.toDomain(): OnePieceSize {
    return OnePieceSize(
        id = id,
        uid = uid,
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
        date = date,
        entryType = entryType
    )
}

fun OnePieceSize.toEntity(): SizeEntity {
    return OnePieceSizeEntity(
        id = id,
        uid = uid,
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
        date = date,
        entryType = entryType
    )
}