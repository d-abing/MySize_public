package com.aube.mysize.presentation.ui.component.closet

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.aube.mysize.domain.model.clothes.Clothes

@Composable
fun PictureGrid(
    clothesList: List<Clothes>,
    onClick: (Clothes) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(clothesList) { clothes ->
            val imageBitmap = remember(clothes.imageBytes) {
                clothes.imageBytes.let { BitmapFactory.decodeByteArray(it, 0, it.size) }?.asImageBitmap()
            }

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable { onClick(clothes) }
            ) {
                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
