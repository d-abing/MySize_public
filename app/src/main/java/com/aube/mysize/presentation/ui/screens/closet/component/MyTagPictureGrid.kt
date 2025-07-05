package com.aube.mysize.presentation.ui.screens.closet.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aube.mysize.domain.model.clothes.Clothes

@Composable
fun MyTagPictureGrid(
    clothesList: List<Clothes>,
    searchQuery: String,
    onClick: (Clothes) -> Unit,
) {
    val tagToClothes = clothesList
        .flatMap { cloth -> cloth.tags.map { tag -> tag to cloth } }
        .groupBy({ it.first }, { it.second })
        .filterKeys { it.contains(searchQuery, ignoreCase = true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        tagToClothes.forEach { (tag, taggedClothes) ->
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    text = "#$tag",
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            items(taggedClothes.chunked(2)) { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { clothes ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickable { onClick(clothes) }
                        ) {
                            AsyncImage(
                                model = clothes.imageUrl,
                                contentDescription = "${clothes.id} 옷 이미지",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
