package com.aube.mysize.domain.model.size

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import com.aube.mysize.data.model.size.BodySizeEntity
import com.aube.mysize.presentation.model.BodySizeCardUiModel
import java.time.LocalDate

data class BodySize(
    override val id: Int = 0,
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
    override val date: LocalDate
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
        imageVector = Icons.Filled.Accessibility,
        description = mapOf(
            "키" to "${height.toInt()}cm",
            "몸무게" to "${weight.toInt()}kg",
            "성별" to gender,
            "가슴둘레" to chest?.let { "${it.toInt()}cm" },
            "허리둘레" to waist?.let { "${it.toInt()}cm" },
            "엉덩이둘레" to hip?.let { "${it.toInt()}cm" },
            "목둘레" to neck?.let { "${it.toInt()}cm" },
            "어깨너비" to shoulder?.let { "${it.toInt()}cm" },
            "팔 길이" to arm?.let { "${it.toInt()}cm" },
            "다리 안쪽 길이" to leg?.let { "${it.toInt()}cm" }
        )
    )
}

