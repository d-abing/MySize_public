package com.aube.mysize.data.model.dto.size

import androidx.annotation.Keep
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.utils.toLocalDateTime
import com.aube.mysize.utils.toTimestamp
import com.google.firebase.Timestamp

@Keep
data class BodySizeDTO(
    override val id: String = "",
    override val uid: String = "",
    val gender: Gender = Gender.MALE,
    val height: Float = 0f,
    val weight: Float = 0f,
    val chest: Float? = null,
    val waist: Float? = null,
    val hip: Float? = null,
    val neck: Float? = null,
    val shoulder: Float? = null,
    val arm: Float? = null,
    val leg: Float? = null,
    val footLength: Float? = null,
    val footWidth: Float? = null,
    val date: Timestamp = Timestamp.now()
): SizeDTO {
    override fun toDomain() = BodySize(
        id = id,
        uid = uid,
        gender = gender,
        height = height,
        weight = weight,
        chest = chest,
        waist = waist,
        hip = hip,
        neck = neck,
        shoulder = shoulder,
        arm = arm,
        leg = leg,
        footLength = footLength,
        footWidth = footWidth,
        date = date.toLocalDateTime(),
    )
}


fun BodySize.toDTO(): BodySizeDTO {
    return BodySizeDTO(
        id = id,
        uid = uid,
        gender = gender,
        height = height,
        weight = weight,
        chest = chest,
        waist = waist,
        hip = hip,
        neck = neck,
        shoulder = shoulder,
        arm = arm,
        leg = leg,
        footLength = footLength,
        footWidth = footWidth,
        date = date.toTimestamp()
    )
}