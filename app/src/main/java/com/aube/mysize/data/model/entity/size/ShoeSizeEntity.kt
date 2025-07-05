package com.aube.mysize.data.model.entity.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.SizeEntryType
import java.time.LocalDateTime

@Entity(tableName = "shoe_size")
data class ShoeSizeEntity(
    @PrimaryKey override val id: String,
    override val uid: String,
    val type: String,
    val brand: String,
    val sizeLabel: String,
    val footLength: Float?,
    val footWidth: Float?,
    val fit: String?,
    val note: String?,
    override val date: LocalDateTime,
    override val entryType: SizeEntryType = SizeEntryType.MY
): SizeEntity

fun ShoeSizeEntity.toDomain(): ShoeSize {
    return ShoeSize(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        footLength = footLength,
        footWidth = footWidth,
        fit = fit,
        note = note,
        date = date,
        entryType = entryType
    )
}

fun ShoeSize.toEntity(): SizeEntity {
    return ShoeSizeEntity(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        footLength = footLength,
        footWidth = footWidth,
        fit = fit,
        note = note,
        date = date,
        entryType = entryType
    )
}