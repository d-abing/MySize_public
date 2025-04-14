package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.BottomSize
import java.time.LocalDate

@Entity(tableName = "bottom_size")
data class BottomSizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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
    val date: LocalDate
)

fun BottomSizeEntity.toDomain(): BottomSize {
    return BottomSize(
        id = id,
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
        date = date
    )
}
