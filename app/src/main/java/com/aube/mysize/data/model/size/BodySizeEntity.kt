package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.size.BodySize
import java.time.LocalDate

@Entity(tableName = "body_size")
data class BodySizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gender: String,
    val height: Float,
    val weight: Float,
    val chest: Float?,
    val waist: Float?,
    val hip: Float?,
    val neck: Float?,
    val shoulder: Float?,
    val arm: Float?,
    val leg: Float?,
    val date: LocalDate
)


fun BodySizeEntity.toDomain(): BodySize {
    return BodySize(
        id = id,
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
        date = date
    )
}