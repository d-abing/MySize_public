package com.aube.mysize.domain.model

import com.aube.mysize.R
import com.aube.mysize.data.model.size.BodySizeEntity
import com.aube.mysize.presentation.model.SizeCardUiModel
import java.time.LocalDate

data class BodySize(
    val id: Int = 0,
    val gender: String,      // 성별
    val height: Float?,      // 키 (cm)
    val weight: Float?,      // 몸무게 (kg)
    val chest: Float?,       // 가슴둘레
    val waist: Float?,       // 허리둘레
    val hip: Float?,         // 엉덩이둘레
    val neck: Float?,        // 목둘레
    val shoulder: Float?,    // 어깨너비
    val thigh: Float?,       // 허벅지둘레
    val calf: Float?,        // 종아리둘레
    val date: LocalDate      // 측정일
) : Size

fun BodySize.toEntity(): BodySizeEntity {
    return BodySizeEntity(
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

fun BodySize.toUi(): SizeCardUiModel {
    return SizeCardUiModel(
        title = "신체",
        imageResId = R.drawable.body, // 실제 이미지 리소스 넣기
        contents = listOfNotNull(
            height?.let { "키: ${it.toInt()}cm" },
            weight?.let { "몸무게: ${it.toInt()}kg" },
            "성별: $gender",
            chest?.let { "가슴둘레: ${it.toInt()}cm" },
            waist?.let { "허리둘레: ${it.toInt()}cm" },
            hip?.let { "엉덩이둘레: ${it.toInt()}cm" },
            neck?.let { "목둘레: ${it.toInt()}cm" },
            shoulder?.let { "어깨너비: ${it.toInt()}cm" },
            thigh?.let { "허벅지둘레: ${it.toInt()}cm" },
            calf?.let { "종아리둘레: ${it.toInt()}cm" }
        )
    )
}

