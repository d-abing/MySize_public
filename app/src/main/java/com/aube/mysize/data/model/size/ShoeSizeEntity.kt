package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.ShoeSize
import java.time.LocalDate

@Entity(tableName = "shoe_size")
data class ShoeSizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String,
    val sizeLabel: String,       // 245mm, US9 등
    val footLength: Float?,
    val footWidth: Float?,
    val fit: String?,
    val note: String?,
    val date: LocalDate
)

fun ShoeSizeEntity.toDomain(): ShoeSize {
    return ShoeSize(
        id = id,
        brand = brand,
        sizeLabel = sizeLabel,
        footLength = footLength,
        footWidth = footWidth,
        fit = fit,
        note = note,
        date = date
    )
}
