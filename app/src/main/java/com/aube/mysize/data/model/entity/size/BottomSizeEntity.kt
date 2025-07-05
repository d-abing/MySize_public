package com.aube.mysize.data.model.entity.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.SizeEntryType
import java.time.LocalDateTime

@Entity(tableName = "bottom_size")
data class BottomSizeEntity(
    @PrimaryKey override val id: String,
    override val uid: String,
    val type: String,
    val brand: String,
    val sizeLabel: String,
    val waist: Float?,
    val rise: Float?,
    val hip: Float?,
    val thigh: Float?,
    val hem: Float?,
    val length: Float?,
    val fit: String?,
    val note: String?,
    override val date: LocalDateTime,
    override val entryType: SizeEntryType = SizeEntryType.MY
): SizeEntity

fun BottomSizeEntity.toDomain(): BottomSize {
    return BottomSize(
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
        date = date,
        entryType = entryType
    )
}

fun BottomSize.toEntity(): SizeEntity {
    return BottomSizeEntity(
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
        date = date,
        entryType = entryType
    )
}