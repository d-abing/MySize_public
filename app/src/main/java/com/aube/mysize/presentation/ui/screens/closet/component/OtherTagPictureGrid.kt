package com.aube.mysize.presentation.ui.screens.closet.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aube.mysize.domain.model.clothes.Clothes
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

@Composable
fun OtherTagPictureGrid(
    modifier: Modifier = Modifier,
    clothesList: List<Clothes>,
    onClick: (Clothes) -> Unit,
    onLoadMore: (() -> Unit)? = null
) {
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { it.lastOrNull()?.index }
            .distinctUntilChanged()
            .filterNotNull()
            .filter { lastVisibleIndex ->
                lastVisibleIndex >= clothesList.chunked(2).size - 2
            }
            .collect {
                onLoadMore?.invoke()
            }
    }

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        items(clothesList.chunked(2)) { rowItems ->
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

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}