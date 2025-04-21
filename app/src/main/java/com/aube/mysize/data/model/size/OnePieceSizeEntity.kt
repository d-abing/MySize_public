package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.size.OnePieceSize
import java.time.LocalDate

@Entity(tableName = "one_piece_size")
data class OnePieceSizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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
    val date: LocalDate
)

fun OnePieceSizeEntity.toDomain(): OnePieceSize {
    return OnePieceSize(
        id = id,
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
        date = date
    )
}