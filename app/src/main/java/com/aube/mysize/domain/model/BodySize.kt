package com.aube.mysize.domain.model

import com.aube.mysize.R
import com.aube.mysize.data.model.size.BodySizeEntity
import com.aube.mysize.presentation.model.BodySizeCardUiModel
import java.time.LocalDate

data class BodySize(
    override val id: Int = 0,
    val gender: String,      // 성별
    val height: Float?,      // 키 (cm)
    val weight: Float?,      // 몸무게 (kg)
    val chest: Float?,       // 가슴 둘레
    val waist: Float?,       // 허리 둘레
    val hip: Float?,         // 엉덩이 둘레
    val neck: Float?,        // 목 둘레
    val shoulder: Float?,    // 어깨 너비
    val arm: Float?,         // 팔 길이
    val leg: Float?,         // 다리 안쪽 길이
    override val date: LocalDate      // 측정일
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
        arm = arm,
        leg = leg,
        date = date
    )
}

fun BodySize.toUi(): BodySizeCardUiModel {
    return BodySizeCardUiModel(
        title = "신체",
        imageResId = R.drawable.body, // 실제 이미지 리소스 넣기
        description = listOfNotNull(
            height?.let { "키: ${it.toInt()}cm" },
            weight?.let { "몸무게: ${it.toInt()}kg" },
            "성별: $gender",
            chest?.let { "가슴둘레: ${it.toInt()}cm" },
            waist?.let { "허리둘레: ${it.toInt()}cm" },
            hip?.let { "엉덩이둘레: ${it.toInt()}cm" },
            neck?.let { "목둘레: ${it.toInt()}cm" },
            shoulder?.let { "어깨너비: ${it.toInt()}cm" },
            arm?.let { "팔 길이: ${it.toInt()}cm" },
            leg?.let { "다리 안쪽 길이: ${it.toInt()}cm" }
        ),
    )
}

