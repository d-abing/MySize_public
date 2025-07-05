package com.aube.mysize.presentation.ui.screens.closet.component.closet_grid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.pullToRefreshIndicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.aube.mysize.R
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.model.clothes.PictureGridItem
import com.aube.mysize.presentation.ui.component.ad.NativeAdGridItemView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureGrid(
    clothesList: List<Clothes>,
    onClick: (Clothes) -> Unit,
    onLoadMore: (() -> Unit)? = null,
    onRefresh: (() -> Unit)? = null,
    isRefreshing: Boolean = false
) {
    val items = remember(clothesList) { withAds(clothesList) }
    val refreshState = if (onRefresh != null) rememberPullToRefreshState() else null
    val gridState = rememberLazyGridState()

    if (onLoadMore != null) {
        LaunchedEffect(gridState, items.size) {
            snapshotFlow { gridState.layoutInfo }
                .map { layoutInfo ->
                    val totalItems = layoutInfo.totalItemsCount
                    val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                    lastVisibleIndex >= totalItems - 2
                }
                .distinctUntilChanged()
                .filter { it }
                .collectLatest { onLoadMore() }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (onRefresh != null && refreshState != null) {
                    Modifier.pullToRefresh(
                        isRefreshing = isRefreshing,
                        state = refreshState,
                        onRefresh = onRefresh
                    )
                } else Modifier
            )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(items.size) { index ->
                when (val item = items[index]) {
                    is PictureGridItem.ClothesItem -> {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clickable { onClick(item.clothes) }
                        ) {
                            AsyncImage(
                                model = item.clothes.imageUrl,
                                contentDescription = "${item.clothes.id} 옷 이미지",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    is PictureGridItem.AdItem -> {
                        NativeAdGridItemView(adUnitId = stringResource(R.string.ad_unit_id_native))
                    }
                }
            }
        }

        if (onRefresh != null && refreshState != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .pullToRefreshIndicator(
                        state = refreshState,
                        isRefreshing = isRefreshing,
                    )
            )
        }
    }
}

fun withAds(clothesList: List<Clothes>, interval: Int = 10): List<PictureGridItem> {
    val result = mutableListOf<PictureGridItem>()
    clothesList.forEachIndexed { index, clothes ->
        result.add(PictureGridItem.ClothesItem(clothes))
        if ((index + 1) % interval == 0 && index != clothesList.lastIndex)
            result.add(PictureGridItem.AdItem)
    }
    return result
}