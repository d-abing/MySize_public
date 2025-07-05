package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.aube.mysize.R

@Composable
fun ClothesImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.label_clothes_image),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
}
