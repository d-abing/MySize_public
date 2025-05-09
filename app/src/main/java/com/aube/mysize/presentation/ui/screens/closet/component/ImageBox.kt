package com.aube.mysize.presentation.ui.screens.closet.component

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.aube.mysize.utils.getOriginalBitmapFromUri

@Composable
fun ImageBox(
    selectedImage: Any?,
    context: Context,
    selectedStep: Int,
    onColorPicked: (Color) -> Unit,
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) } // 터치 좌표
    var tapOffset by remember { mutableStateOf(Offset.Zero) }

    if (selectedImage is Uri? && selectedImage != null) {
        bitmap = context.getOriginalBitmapFromUri(selectedImage)

        Image(
            painter = rememberAsyncImagePainter(model = selectedImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .then( if (selectedStep == 1)
                    Modifier.pointerInput(Unit) {
                        detectTapGestures { offset ->
                            tapOffset = offset
                            // 이미지 영역에서의 위치를 비트맵 좌표로 변환
                            val bitmapColor = getBitmapColorFromTap(bitmap, tapOffset, size)
                            bitmapColor?.let { onColorPicked(it) }
                        }
                    } else Modifier
                )
        )
    } else if (selectedImage is ByteArray? && selectedImage != null) {

        Image(
            painter = rememberAsyncImagePainter(model = selectedImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .then( if (selectedStep == 1)
                    Modifier.pointerInput(Unit) {
                        detectTapGestures { offset ->
                            tapOffset = offset
                            // 이미지 영역에서의 위치를 비트맵 좌표로 변환
                            val bitmapColor = getBitmapColorFromTap(bitmap, tapOffset, size)
                            bitmapColor?.let { onColorPicked(it) }
                        }
                    } else Modifier
                )
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray, RoundedCornerShape(16.dp))
        )
    }
}

// 사용자가 터치한 위치의 색상 추출
fun getBitmapColorFromTap(bitmap: Bitmap?, tapOffset: Offset, imageSize: IntSize): Color? {
    if (bitmap == null) return null

    // 이미지의 표시된 사이즈와 비트맵의 사이즈 비율 계산
    val scaleX = bitmap.width.toFloat() / imageSize.width
    val scaleY = bitmap.height.toFloat() / imageSize.height

    // 터치 좌표를 비트맵 좌표로 변환
    val x = (tapOffset.x * scaleX).toInt()
    val y = (tapOffset.y * scaleY).toInt()

    return if (x in 0 until bitmap.width && y in 0 until bitmap.height) {
        Color(bitmap.getPixel(x, y))
    } else {
        null
    }
}
