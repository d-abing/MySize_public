package com.aube.mysize.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.BodyProfile
import java.time.LocalDate

@Entity(tableName = "body_profile")
data class BodyProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gender: String,
    val height: Float,
    val weight: Float,
    val chest: Float,
    val waist: Float,
    val hip: Float,
    val date: LocalDate = LocalDate.now()
)

fun BodyProfileEntity.toDomain(): BodyProfile {
    return BodyProfile(
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