package com.aube.mysize.presentation.ui.component.closet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel
import kotlinx.coroutines.delay

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
    var isReady by remember { mutableStateOf(false) }



    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(clothesList) { clothes ->

            LaunchedEffect(clothes.linkedSizeIds) {
                delay(100)
                isReady = true
            }

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

                if (isReady) {
                    val summaries = listOfNotNull(
                        clothes.linkedSizeIds["TOP"]?.let {
                            topSizeViewModel.getSizeById(it)?.let(::formatTopSize)
                        },
                        clothes.linkedSizeIds["BOTTOM"]?.let {
                            bottomSizeViewModel.getSizeById(it)?.let(::formatBottomSize)
                        },
                        clothes.linkedSizeIds["OUTER"]?.let {
                            outerSizeViewModel.getSizeById(it)?.let(::formatOuterSize)
                        },
                        clothes.linkedSizeIds["ONE_PIECE"]?.let {
                            onePieceSizeViewModel.getSizeById(
                                it
                            )?.let(::formatOnePieceSize)
                        },
                        clothes.linkedSizeIds["SHOE"]?.let {
                            shoeSizeViewModel.getSizeById(it)?.let(::formatShoeSize)
                        },
                        clothes.linkedSizeIds["ACCESSORY"]?.let {
                            accessorySizeViewModel.getSizeById(
                                it
                            )?.let(::formatAccessorySize)
                        }
                    )

                    val pagerState = rememberPagerState(pageCount = { summaries.size })


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

fun formatTopSize(size: TopSize): String = buildString {
    appendLine("👕")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.shoulder?.let { appendLine("어깨너비: ${it}cm") }
    size.chest?.let { appendLine("가슴단면: ${it}cm") }
    size.sleeve?.let { appendLine("소매길이: ${it}cm") }
    size.length?.let { appendLine("총장: ${it}cm") }
    size.fit?.let { appendLine("핏: $it") }
    size.note?.let { appendLine("메모: $it") }
}

fun formatBottomSize(size: BottomSize): String = buildString {
    appendLine("👖")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.waist?.let { appendLine("허리단면: ${it}cm") }
    size.rise?.let { appendLine("밑위: ${it}cm") }
    size.hip?.let { appendLine("엉덩이단면: ${it}cm") }
    size.thigh?.let { appendLine("허벅지단면: ${it}cm") }
    size.hem?.let { appendLine("밑단단면: ${it}cm") }
    size.length?.let { appendLine("총장: ${it}cm") }
    size.fit?.let { appendLine("핏: $it") }
    size.note?.let { appendLine("메모: $it") }
}

fun formatOuterSize(size: OuterSize): String = buildString {
    appendLine("🧥")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.shoulder?.let { appendLine("어깨너비: ${it}cm") }
    size.chest?.let { appendLine("가슴단면: ${it}cm") }
    size.sleeve?.let { appendLine("소매길이: ${it}cm") }
    size.length?.let { appendLine("총장: ${it}cm") }
    size.fit?.let { appendLine("핏: $it") }
    size.note?.let { appendLine("메모: $it") }
}

fun formatOnePieceSize(size: OnePieceSize): String = buildString {
    appendLine("👗")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.shoulder?.let { appendLine("어깨너비: ${it}cm") }
    size.chest?.let { appendLine("가슴단면: ${it}cm") }
    size.waist?.let { appendLine("허리단면: ${it}cm") }
    size.hip?.let { appendLine("엉덩이단면: ${it}cm") }
    size.sleeve?.let { appendLine("소매길이: ${it}cm") }
    size.rise?.let { appendLine("밑위: ${it}cm") }
    size.thigh?.let { appendLine("허벅지단면: ${it}cm") }
    size.hem?.let { appendLine("밑단단면: ${it}cm") }
    size.length?.let { appendLine("총장: ${it}cm") }
    size.fit?.let { appendLine("핏: ${it}") }
    size.note?.let { appendLine("메모: ${it}") }
}

fun formatShoeSize(size: ShoeSize): String = buildString {
    appendLine("👟")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.footLength?.let { appendLine("발길이: ${it}cm") }
    size.footWidth?.let { appendLine("발볼너비: ${it}cm") }
    size.fit?.let { appendLine("핏: ${it}") }
    size.note?.let { appendLine("메모: ${it}") }
}

fun formatAccessorySize(size: AccessorySize): String = buildString {
    appendLine("💍")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.bodyPart?.let { appendLine("착용 부위: ${it}") }
    size.fit?.let { appendLine("핏: ${it}") }
    size.note?.let { appendLine("메모: ${it}") }
}

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            Text(
                text = if (index == currentPage) "●" else "○",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
    }
}
