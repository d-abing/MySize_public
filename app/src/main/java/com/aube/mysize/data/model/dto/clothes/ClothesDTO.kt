package com.aube.mysize.data.model.dto.clothes

import androidx.annotation.Keep
import com.aube.mysize.data.model.dto.size.BodySizeDTO
import com.aube.mysize.data.model.dto.size.toDTO
import com.aube.mysize.domain.model.clothes.BodyField
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.model.clothes.LinkedSizeGroup
import com.aube.mysize.domain.model.clothes.MemoVisibility
import com.aube.mysize.domain.model.clothes.Visibility
import com.aube.mysize.utils.toLocalDateTime
import com.aube.mysize.utils.toTimestamp
import com.google.firebase.Timestamp

@Keep
data class ClothesDTO(
    val id: String = "",
    val imageUrl: String = "",
    val dominantColor: Int = 0,
    val linkedSizes: List<LinkedSizeGroup> = emptyList(),
    val bodySize: BodySizeDTO? = null,
    val sharedBodyFields: List<BodyField> = emptyList(),
    val tags: List<String> = emptyList(),
    val memo: String? = null,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp? = null,
    val createUserId: String = "",
    val createUserProfileImageUrl: String = "",
    val visibility: Visibility = Visibility.PRIVATE,
    val memoVisibility: MemoVisibility = MemoVisibility.PRIVATE,
)

fun ClothesDTO.toDomain(): Clothes = Clothes(
    id = id,
    imageUrl = imageUrl,
    dominantColor = dominantColor,
    linkedSizes = linkedSizes,
    bodySize = bodySize?.toDomain(),
    sharedBodyFields = sharedBodyFields.toSet(),
    tags = tags.toSet(),
    memo = memo,
    createdAt = createdAt.toLocalDateTime(),
    updatedAt = updatedAt?.toLocalDateTime(),
    createUserId = createUserId,
    createUserProfileImageUrl = createUserProfileImageUrl,
    visibility = visibility,
    memoVisibility = memoVisibility
)

fun Clothes.toDTO(): ClothesDTO = ClothesDTO(
    id = id,
    imageUrl = imageUrl,
    dominantColor = dominantColor,
    linkedSizes = linkedSizes,
    bodySize = bodySize?.toDTO(),
    sharedBodyFields = sharedBodyFields.toList(),
    tags = tags.toList(),
    memo = memo,
    createdAt = createdAt.toTimestamp(),
    updatedAt = updatedAt?.toTimestamp(),
    createUserId = createUserId,
    createUserProfileImageUrl = createUserProfileImageUrl,
    visibility = visibility,
    memoVisibility = memoVisibility
)