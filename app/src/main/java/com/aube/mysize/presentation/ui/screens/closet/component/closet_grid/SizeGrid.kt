package com.aube.mysize.presentation.ui.screens.closet.component.closet_grid

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.constants.formatSizeByCategory
import com.aube.mysize.presentation.ui.component.chip_tap.PagerIndicator
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel

@Composable
fun SizeGrid(
    sizeViewModel: SizeViewModel = hiltViewModel(),
    clothesList: List<Clothes>,
) {
    val allSizes by sizeViewModel.sizes.collectAsState()

    val filteredListWithSummaries = remember(clothesList, allSizes) {
        clothesList.mapNotNull { clothes ->
            val summaries = clothes.linkedSizes.flatMap { group ->
                group.sizeIds.mapNotNull { id ->
                    formatSizeByCategory(SizeCategory.valueOf(group.category), id, allSizes)
                }
            }
            if (summaries.isNotEmpty()) clothes to summaries else null
        }
    }

    if (filteredListWithSummaries.isEmpty()) return

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(filteredListWithSummaries) { (clothes, summaries) ->
            val pagerState = rememberPagerState(pageCount = { summaries.size })

            Box(
                modifier = Modifier.aspectRatio(1f)
            ) {
                AsyncImage(
                    model = clothes.imageUrl,
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
                        modifier = Modifier.wrapContentSize(),
                        pageCount = summaries.size,
                        currentPage = pagerState.currentPage
                    )
                }
            }
        }
    }
}


