package com.aube.mysize.presentation.ui.screens.closet.component

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aube.mysize.utils.getBitmapColorFromTap

@Composable
fun ImageForColorExtraction(
    selectedImage: Uri?,
    selectedStep: Int,
    onColorPicked: (Color) -> Unit
) {
    val context = LocalContext.current
    val imageSizeState = remember { mutableStateOf(IntSize.Zero) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(selectedImage)
            .allowHardware(false)
            .build()
    )

    val state = painter.state

    Box(modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(16.dp))
        .border(0.5.dp, Color.LightGray, RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .onGloballyPositioned { coordinates ->
                    imageSizeState.value = coordinates.size
                }
                .then(
                    if (selectedStep == 1) {
                        Modifier.pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val drawable = (painter.state as? AsyncImagePainter.State.Success)
                                    ?.result?.drawable
                                val bitmap = (drawable as? BitmapDrawable)?.bitmap
                                bitmap?.let {
                                    val color = getBitmapColorFromTap(it, offset, imageSizeState.value)
                                    if (color != null && color.alpha > 0f) {
                                        onColorPicked(color)
                                    }
                                }
                            }
                        }
                    } else Modifier
                )
        )

        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

