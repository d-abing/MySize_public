package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.Cloth
import java.time.LocalDate

@Entity(tableName = "cloth")
data class ClothEntity(
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

fun ClothEntity.toDomain(): Cloth {
    return Cloth(
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