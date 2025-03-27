package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.TopSize
import java.time.LocalDate

@Entity(tableName = "top_size")
data class TopSizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,             // 티셔츠, 셔츠 등
    val brand: String,
    val sizeLabel: String,        // 예: M, L, 95
    val shoulder: Float?,
    val chest: Float?,
    val sleeve: Float?,
    val length: Float?,
    val fit: String?,
    val note: String?,
    val date: LocalDate
)

fun TopSizeEntity.toDomain(): TopSize {
    return TopSize(
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
