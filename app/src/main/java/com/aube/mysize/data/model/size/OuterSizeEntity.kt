package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.OuterSize
import java.time.LocalDate

@Entity(tableName = "outer_size")
data class OuterSizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val brand: String,
    val sizeLabel: String,
    val shoulder: Float?,
    val chest: Float?,
    val sleeve: Float?,
    val length: Float?,
    val fit: String?,
    val note: String?,
    val date: LocalDate
)

fun OuterSizeEntity.toDomain(): OuterSize {
    return OuterSize(
        id = id,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        shoulder = shoulder,
        chest = chest,
        sleeve = sleeve,
        length = length,
        fit = fit,
        note = note,
        date = date
    )
}
