package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.Clothes
import java.time.LocalDate

@Entity(tableName = "clothes")
data class ClothesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageBytes: ByteArray,
    val hash: String,
    val dominantColor: Int,
    val linkedSizeId: Int,
    val sizeCategory: String,
    val date: LocalDate,
    val memo: String?,
    val isPublic: Boolean = false
)

fun ClothesEntity.toDomain(): Clothes {
    return Clothes(
        id = id,
        imageBytes = imageBytes,
        hash = hash,
        dominantColor = dominantColor,
        linkedSizeId = linkedSizeId,
        sizeCategory = sizeCategory,
        date = date,
        memo = memo,
        isPublic = isPublic
    )
}