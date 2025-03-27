package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.AccessorySize
import java.time.LocalDate

@Entity(tableName = "accessory_size")
data class AccessorySizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,            // 반지, 팔찌 등
    val brand: String,
    val sizeLabel: String,       // 10호, M 등
    val bodyPart: String?,        // 손가락, 손목 등
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
        date = date
    )
}
