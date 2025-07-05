package com.aube.mysize.utils

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize

fun getBitmapColorFromTap(
    bitmap: Bitmap,
    tapOffset: Offset,
    imageSize: IntSize
): Color? {
    if (imageSize.width == 0 || imageSize.height == 0) return null

    val scaleX = bitmap.width.toFloat() / imageSize.width
    val scaleY = bitmap.height.toFloat() / imageSize.height

    val x = (tapOffset.x * scaleX).toInt().coerceIn(0, bitmap.width - 1)
    val y = (tapOffset.y * scaleY).toInt().coerceIn(0, bitmap.height - 1)

    return try {
        Color(bitmap.getPixel(x, y))
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
