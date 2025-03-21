package com.aube.mysize.domain.model

import com.aube.mysize.data.model.BodyProfileEntity
import java.time.LocalDate

data class BodyProfile(
    val id: Int,
    val gender: String,
    val height: Float,
    val weight: Float,
    val chest: Float,
    val waist: Float,
    val hip: Float,
    val date: LocalDate
)

fun BodyProfile.toEntity(): BodyProfileEntity {
    return BodyProfileEntity(
        id = this.id,
        gender = this.gender,
        height = this.height,
        weight = this.weight,
        chest = this.chest,
        waist = this.waist,
        hip = this.hip,
        date = this.date
    )
}