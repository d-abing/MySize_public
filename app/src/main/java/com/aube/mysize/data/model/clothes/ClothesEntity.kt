package com.aube.mysize.data.model.clothes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.model.Visibility
import java.time.LocalDateTime

@Entity(tableName = "clothes")
data class ClothesEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val imageBytes: ByteArray,
    val hash: String,
    val dominantColor: Int,
    val linkedSizeIds: Map<String, Int>,
    val tags: Set<String>,
    val memo: String?,
    val sharedBodyFields: Set<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val createUserId: Long,
    val createUserProfileFilePath: String,
    val visibility: Visibility
)

fun ClothesEntity.toDomain(): Clothes {
    return Clothes(
        id = id,
        imageBytes = imageBytes,
        hash = hash,
        dominantColor = dominantColor,
        linkedSizeIds = linkedSizeIds,
        tags = tags,
        memo = memo,
        sharedBodyFields = sharedBodyFields,
        createdAt = createdAt,
        updatedAt = updatedAt,
        createUserId = createUserId,
        createUserProfileFilePath = createUserProfileFilePath,
        visibility = visibility
    )
}