package com.aube.mysize.domain.model.clothes

import com.aube.mysize.data.model.clothes.ClothesEntity
import java.time.LocalDateTime

data class Clothes(
    val id: Int = 0,
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
    val isPublic: Boolean = false
)

fun Clothes.toEntity(): ClothesEntity {
    return ClothesEntity(
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
        isPublic = isPublic
    )
}