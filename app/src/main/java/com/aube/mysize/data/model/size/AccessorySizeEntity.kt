package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.AccessorySize
import java.time.LocalDate

@Entity(tableName = "accessory_size")
data class AccessorySizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val brand: String,
    val sizeLabel: String,
    val bodyPart: String?,
    val fit: String?,
    val note: String?,
    val date: LocalDate
)

fun AccessorySizeEntity.toDomain(): AccessorySize {
    return AccessorySize(
        id = id,
        type = type,
        bodyPart = bodyPart,
        sizeLabel = sizeLabel,
        brand = brand,
        note = note,
        fit = fit,
        date = date
    )
}
