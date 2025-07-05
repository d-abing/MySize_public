package com.aube.mysize.presentation.model.clothes

import com.aube.mysize.domain.model.clothes.BodyField
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.model.clothes.LinkedSizeGroup
import com.aube.mysize.domain.model.clothes.MemoVisibility
import com.aube.mysize.domain.model.clothes.Visibility
import com.aube.mysize.domain.model.size.BodySize
import java.time.LocalDateTime

data class ClothesSaveModel (
    val id: String? = null,
    val imageBytes: ByteArray? = null,
    val originalImageUrl: String? = null,
    val dominantColor: Int,
    val linkedSizes: List<LinkedSizeGroup>,
    val bodySize: BodySize?,
    val sharedBodyFields: Set<String>,
    val tags: Set<String>,
    val memo: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val createUserId: String,
    val createUserProfileImageUrl: String,
    val visibility: Visibility,
    val memoVisibility: MemoVisibility
)

fun ClothesSaveModel.toDomain(id: String): Clothes {
    return Clothes(
        id = id,
        imageUrl = this.originalImageUrl ?: "",
        dominantColor = this.dominantColor,
        linkedSizes = this.linkedSizes,
        bodySize = this.bodySize,
        sharedBodyFields = this.sharedBodyFields.mapNotNull { BodyField.fromDisplayName(it) }.toSet(),
        tags = this.tags,
        memo = this.memo,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        createUserId = this.createUserId,
        createUserProfileImageUrl = this.createUserProfileImageUrl,
        visibility = this.visibility,
        memoVisibility = this.memoVisibility
    )
}
