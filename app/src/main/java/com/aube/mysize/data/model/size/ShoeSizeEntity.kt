package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.ShoeSize
import java.time.LocalDate

@Entity(tableName = "shoe_size")
data class ShoeSizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val brand: String,
    val sizeLabel: String,
    val footLength: Float?,
    val footWidth: Float?,
    val fit: String?,
    val note: String?,
    val date: LocalDate
)

fun ShoeSizeEntity.toDomain(): ShoeSize {
    return ShoeSize(
        id = id,
        type = type,
        brand = brand,
        sizeLabel = sizeLabel,
        footLength = footLength,
        footWidth = footWidth,
        fit = fit,
        note = note,
        date = date
    )
}
