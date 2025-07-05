package com.aube.mysize.data.model.entity.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.SizeEntryType
import java.time.LocalDateTime

@Entity(tableName = "outer_size")
data class OuterSizeEntity(
    @PrimaryKey override val id: String,
    override val uid: String,
    val type: String,
    val brand: String,
    val sizeLabel: String,
    val shoulder: Float?,
    val chest: Float?,
    val sleeve: Float?,
    val length: Float?,
    val fit: String?,
    val note: String?,
    override val date: LocalDateTime,
    override val entryType: SizeEntryType = SizeEntryType.MY
): SizeEntity

fun OuterSizeEntity.toDomain(): OuterSize {
    return OuterSize(
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
        date = date,
        entryType = entryType
    )
}

fun OuterSize.toEntity(): SizeEntity {
    return OuterSizeEntity(
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
        date = date,
        entryType = entryType
    )
}

