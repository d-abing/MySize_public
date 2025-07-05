package com.aube.mysize.domain.model.size

import com.aube.mysize.presentation.model.recommend.Gender
import java.time.LocalDateTime

data class BodySize(
    override val id: String,
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
    override val date: LocalDateTime
) : Size