package com.aube.mysize.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.media.ExifInterface
import android.net.Uri
import com.aube.mysize.LoginActivity
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.ClothesSize
import com.aube.mysize.presentation.model.size.SizeContentUiModel
import com.google.firebase.Timestamp
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

fun Bitmap.toBytes(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 50, stream)
    return stream.toByteArray()
}

fun Context.copyToClipboard(
    text: String,
) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(this.getString(R.string.label), text)
    clipboard.setPrimaryClip(clip)
}

fun Context.getBitmapFromUri(uri: Uri): Bitmap {
    val contentResolver: ContentResolver = this.contentResolver
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true // 먼저 이미지 크기만 읽기
    }

    // 이미지 크기 확인을 위해 한 번 디코딩
    contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream, null, options)
    }

    // 적절한 inSampleSize 계산 (이미지를 더 작게 로드하여 메모리 절약)
    options.inSampleSize = calculateInSampleSize(options)
    options.inJustDecodeBounds = false // 이제 실제 이미지를 디코딩

    // 회전 각도 계산
    val rotationDegrees = getRotationDegrees(contentResolver, uri)

    // 이미지 디코딩 및 회전 적용
    return contentResolver.openInputStream(uri)?.use { inputStream ->
        val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
        bitmap?.let { rotateBitmap(it, rotationDegrees) } ?: Bitmap.createBitmap(
            1,
            1,
            Bitmap.Config.ARGB_8888
        )
    } ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
}

// 회전 각도를 얻기 위한 함수
fun getRotationDegrees(contentResolver: ContentResolver, uri: Uri): Float {
    contentResolver.openInputStream(uri)?.use { inputStream ->
        val exif = ExifInterface(inputStream)
        return when (exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> 0f
        }
    }
    return 0f
}

fun rotateBitmap(bitmap: Bitmap, rotationDegrees: Float): Bitmap {
    return if (rotationDegrees != 0f) {
        val matrix = Matrix().apply { postRotate(rotationDegrees) }
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    } else {
        bitmap
    }
}

fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int = 1080,
    reqHeight: Int = 1080,
): Int {
    val (height: Int, width: Int) = options.outHeight to options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

fun Context.navigateToLogin() {
    val intent = Intent(this, LoginActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}

fun Timestamp?.toLocalDateTime(): LocalDateTime {
    return this?.toDate()?.toInstant()
        ?.atZone(ZoneId.systemDefault())
        ?.toLocalDateTime()
        ?: LocalDateTime.MIN
}

fun LocalDateTime.toTimestamp(): Timestamp {
    val instant = this.atZone(ZoneId.systemDefault()).toInstant()
    val date = Date.from(instant)
    return Timestamp(date)
}

fun List<ClothesSize>.selectMostFrequentSize(): ClothesSize {
    val labelCount = groupingBy { it.sizeLabel }.eachCount()
    val max = labelCount.maxByOrNull { it.value }?.value ?: 0
    val candidates = labelCount.filter { it.value == max }.keys
    return filter { it.sizeLabel in candidates }.maxByOrNull { it.date }!!
}

fun List<SizeContentUiModel>.sortedByPriority(selector: (SizeContentUiModel) -> String): List<SizeContentUiModel> {
    return sortedWith(compareBy({ selector(it).contains("기타") }, { selector(it) }))
}

fun Map<String, List<SizeContentUiModel>>.sortByType(): Map<String, List<SizeContentUiModel>> {
    val (normal, etc) = entries.partition { !it.key.contains("기타") }
    return (normal.sortedBy { it.key } + etc.sortedBy { it.key }).associate { it.toPair() }
}

fun Bitmap.resizeToFit(maxSize: Int): Bitmap {
    val ratio = width.toFloat() / height
    val newWidth: Int
    val newHeight: Int
    if (ratio > 1) {
        newWidth = maxSize
        newHeight = (maxSize / ratio).toInt()
    } else {
        newHeight = maxSize
        newWidth = (maxSize * ratio).toInt()
    }
    return Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
}

fun Bitmap.toGrayscale(): Bitmap {
    val grayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(grayscale)
    val paint = Paint()
    val matrix = ColorMatrix().apply { setSaturation(0f) }
    paint.colorFilter = ColorMatrixColorFilter(matrix)
    canvas.drawBitmap(this, 0f, 0f, paint)
    return grayscale
}

fun String.isNumeric(): Boolean {
    return this.matches(Regex("^\\d+(\\.\\d+)?$"))
}
