package com.aube.mysize.data.model.size

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.BodySize
import java.time.LocalDate

@Entity(tableName = "body_size")
data class BodySizeEntity(
    @PrimaryKey val id: Int = 0,
    val gender: String,             // 남성 / 여성
    val height: Float?,             // 키 (cm)
    val weight: Float?,             // 몸무게 (kg)
    val chest: Float?,              // 가슴 둘레
    val waist: Float?,              // 허리 둘레
    val hip: Float?,                // 엉덩이 둘레
    val neck: Float?,               // 목 둘레
    val shoulder: Float?,           // 어깨 너비
    val arm: Float?,                // 팔 길이
    val leg: Float?,                // 다리 안쪽 길이
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
        arm = arm,
        leg = leg,
        date = date
    )
}