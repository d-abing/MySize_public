package com.aube.mysize.data.model.entity.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.presentation.model.recommend.Gender
import java.time.LocalDateTime

@Entity(tableName = "body_size")
data class BodySizeEntity(
    @PrimaryKey override val id: String,
    override val uid: String,
    val gender: Gender,
    val height: Float,
    val weight: Float,
    val chest: Float?,
    val waist: Float?,
    val hip: Float?,
    val neck: Float?,
    val shoulder: Float?,
    val arm: Float?,
    val leg: Float?,
    val footLength: Float?,
    val footWidth: Float?,
    override val date: LocalDateTime,
    override val entryType: SizeEntryType = SizeEntryType.MY,
): SizeEntity


fun BodySizeEntity.toDomain(): BodySize {
    return BodySize(
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
        date = date,
    )
}

fun BodySize.toEntity(): SizeEntity {
    return BodySizeEntity(
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
        date = date,
    )
}