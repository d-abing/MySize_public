package com.aube.mysize.domain.model.clothes

import com.aube.mysize.domain.model.size.BodySize
import java.time.LocalDateTime

data class Clothes(
    val id: String,
    var imageUrl: String,
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

