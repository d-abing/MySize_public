package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.presentation.viewmodel.clothes.ClothesViewModel

@Composable
fun ClothesDetailScreen(
    clothesId: Int,
    viewModel: ClothesViewModel = hiltViewModel()
) {
    var clothes by remember { mutableStateOf<com.aube.mysize.domain.model.clothes.Clothes?>(null) }

    LaunchedEffect(clothesId) {
        viewModel.getById(clothesId) { clothes = it }
    }

    clothes?.let { item ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item.imageBytes.let {
                val bitmap = android.graphics.BitmapFactory.decodeByteArray(it, 0, it.size).asImageBitmap()
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }

            Text("메모: ${item.memo}", style = MaterialTheme.typography.bodyLarge)
            Text("태그: ${item.tags.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
            Text("등록일: ${item.createdAt}", style = MaterialTheme.typography.bodySmall)
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("옷 정보를 불러오는 중입니다...", style = MaterialTheme.typography.bodyMedium)
    }
}
