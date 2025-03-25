package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.BodySize
import java.time.LocalDate

@Entity(tableName = "body_size")
data class BodySizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gender: String,             // 남성 / 여성 / 기타
    val height: Float?,             // 키 (cm)
    val weight: Float?,             // 몸무게 (kg)
    val chest: Float?,              // 가슴둘레
    val waist: Float?,              // 허리둘레
    val hip: Float?,                // 엉덩이둘레
    val neck: Float?,               // 목둘레
    val shoulder: Float?,           // 어깨너비
    val thigh: Float?,              // 허벅지둘레
    val calf: Float?,               // 종아리둘레
    val date: LocalDate             // 측정일
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
        thigh = thigh,
        calf = calf,
        date = date
    )
}