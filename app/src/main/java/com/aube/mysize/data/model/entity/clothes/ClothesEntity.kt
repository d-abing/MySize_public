package com.aube.mysize.data.model.entity.clothes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.clothes.BodyField
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.model.clothes.LinkedSizeGroup
import com.aube.mysize.domain.model.clothes.MemoVisibility
import com.aube.mysize.domain.model.clothes.Visibility
import com.aube.mysize.domain.model.size.BodySize
import java.time.LocalDateTime

@Entity(tableName = "clothes")
data class ClothesEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val dominantColor: Int,
    val linkedSizes: List<LinkedSizeGroup>,
    val bodySize: BodySize?,
    val sharedBodyFields: Set<BodyField>,
    val tags: Set<String>,
    val memo: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val createUserId: String,
    val createUserProfileImageUrl: String,
    val visibility: Visibility,
    val memoVisibility: MemoVisibility
)

fun ClothesEntity.toDomain(): Clothes {
    return Clothes(
        id = id,
        imageUrl = imageUrl,
        dominantColor = dominantColor,
        linkedSizes = linkedSizes,
        tags = tags,
        memo = memo,
        sharedBodyFields = sharedBodyFields,
        bodySize = bodySize,
        createdAt = createdAt,
        updatedAt = updatedAt,
        createUserId = createUserId,
        createUserProfileImageUrl = createUserProfileImageUrl,
        visibility = visibility,
        memoVisibility = memoVisibility
    )
}

fun Clothes.toEntity(): ClothesEntity {
    return ClothesEntity(
        id = id,
        imageUrl = imageUrl,
        dominantColor = dominantColor,
        linkedSizes = linkedSizes,
        tags = tags,
        memo = memo,
        sharedBodyFields = sharedBodyFields,
        bodySize = bodySize,
        createdAt = createdAt,
        updatedAt = updatedAt,
        createUserId = createUserId,
        createUserProfileImageUrl = createUserProfileImageUrl,
        visibility = visibility,
        memoVisibility = memoVisibility
    )
}