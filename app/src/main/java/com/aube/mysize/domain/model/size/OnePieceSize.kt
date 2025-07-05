package com.aube.mysize.domain.model.size

import java.time.LocalDateTime

data class OnePieceSize(
    override val id: String,
    override val uid: String,
    override val type: String,
    override val brand: String,
    override val sizeLabel: String,
    val shoulder: Float?,
    val chest: Float?,
    val waist: Float?,
    val hip: Float?,
    val sleeve: Float?,
    val rise: Float?,
    val thigh: Float?,
    val hem: Float?,
    val length: Float?,
    val fit: String?,
    val note: String?,
    override val date: LocalDateTime,
    override val entryType: SizeEntryType
): ClothesSize



