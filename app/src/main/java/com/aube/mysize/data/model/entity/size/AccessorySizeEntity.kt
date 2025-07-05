package com.aube.mysize.data.model.entity.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.SizeEntryType
import java.time.LocalDateTime

@Entity(tableName = "accessory_size")
data class AccessorySizeEntity(
    @PrimaryKey override val id: String,
    override val uid: String,
    val type: String,
    val brand: String,
    val sizeLabel: String,
    val bodyPart: String?,
    val fit: String?,
    val note: String?,
    override val date: LocalDateTime,
    override val entryType: SizeEntryType = SizeEntryType.MY
): SizeEntity

fun AccessorySizeEntity.toDomain(): AccessorySize {
    return AccessorySize(
        id = id,
        uid = uid,
        type = type,
        bodyPart = bodyPart,
        sizeLabel = sizeLabel,
        brand = brand,
        note = note,
        fit = fit,
        date = date,
        entryType = entryType
    )
}

fun AccessorySize.toEntity(): SizeEntity {
    return AccessorySizeEntity(
        id = id,
        uid = uid,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        bodyPart = bodyPart,
        fit = fit,
        note = note,
        date = date,
        entryType = entryType
    )
}