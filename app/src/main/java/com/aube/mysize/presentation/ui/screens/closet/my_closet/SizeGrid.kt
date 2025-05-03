package com.aube.mysize.presentation.ui.screens.closet.my_closet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.ui.component.PagerIndicator
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel
import com.aube.mysize.utils.formatAccessorySize
import com.aube.mysize.utils.formatBottomSize
import com.aube.mysize.utils.formatOnePieceSize
import com.aube.mysize.utils.formatOuterSize
import com.aube.mysize.utils.formatShoeSize
import com.aube.mysize.utils.formatTopSize

@Composable
fun SizeGrid(
    clothesList: List<Clothes>,
    topSizeViewModel: TopSizeViewModel = hiltViewModel(),
    bottomSizeViewModel: BottomSizeViewModel = hiltViewModel(),
    outerSizeViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceSizeViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeSizeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessorySizeViewModel: AccessorySizeViewModel = hiltViewModel(),
) {
    val topSizes by topSizeViewModel.sizes.collectAsState()
    val bottomSizes by bottomSizeViewModel.sizes.collectAsState()
    val outerSizes by outerSizeViewModel.sizes.collectAsState()
    val onePieceSizes by onePieceSizeViewModel.sizes.collectAsState()
    val shoeSizes by shoeSizeViewModel.sizes.collectAsState()
    val accessorySizes by accessorySizeViewModel.sizes.collectAsState()

    val filteredList = clothesList.filter { it.linkedSizeIds.isNotEmpty() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(filteredList) { clothes ->

            val summaries = remember(
                clothes.linkedSizeIds,
                topSizes,
                bottomSizes,
                outerSizes,
                onePieceSizes,
                shoeSizes,
                accessorySizes
            ) {
                listOfNotNull(
                    clothes.linkedSizeIds["TOP"]?.let { id -> topSizes.find { it.id == id }?.let(::formatTopSize) },
                    clothes.linkedSizeIds["BOTTOM"]?.let { id -> bottomSizes.find { it.id == id }?.let(::formatBottomSize) },
                    clothes.linkedSizeIds["OUTER"]?.let { id -> outerSizes.find { it.id == id }?.let(::formatOuterSize) },
                    clothes.linkedSizeIds["ONE_PIECE"]?.let { id -> onePieceSizes.find { it.id == id }?.let(::formatOnePieceSize) },
                    clothes.linkedSizeIds["SHOE"]?.let { id -> shoeSizes.find { it.id == id }?.let(::formatShoeSize) },
                    clothes.linkedSizeIds["ACCESSORY"]?.let { id -> accessorySizes.find { it.id == id }?.let(::formatAccessorySize) }
                )
            }

            if (summaries.isNotEmpty()) {
                val pagerState = rememberPagerState(pageCount = { summaries.size })

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(clothes.imageBytes)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = 0.4f,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterHorizontally)
                        ) { page ->
                            Text(
                                text = summaries[page],
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        PagerIndicator(
                            modifier = Modifier
                                .wrapContentSize(),
                            pageCount = summaries.size,
                            currentPage = pagerState.currentPage
                        )
                    }
                }
            }
        }
    }
}


