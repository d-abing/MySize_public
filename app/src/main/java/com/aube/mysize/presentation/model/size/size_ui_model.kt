package com.aube.mysize.presentation.model.size

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.ui.graphics.vector.ImageVector
import com.aube.mysize.domain.model.size.BodySize

data class BodySizeCardUiModel(
    val id: String,
    val title: String,
    val imageVector: ImageVector,
    val description: Map<String, String?>
)

data class SizeContentUiModel(
    val title: String,
    val sizeLabel: String,
    val isSaved: Boolean = false,
    val onClick: () -> Unit
)

fun BodySize.toUi(): BodySizeCardUiModel {
    return BodySizeCardUiModel(
        id = id,
        title = "신체",
        imageVector = Icons.Filled.Accessibility,
        description = mapOf(
            "키" to "${height.toInt()}cm",
            "몸무게" to "${weight.toInt()}kg",
            "발 길이" to footLength?.let { "${it.toInt()}mm" },
            "발 너비" to footWidth?.let { "${it.toInt()}mm"},
            "성별" to gender.displayName,
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