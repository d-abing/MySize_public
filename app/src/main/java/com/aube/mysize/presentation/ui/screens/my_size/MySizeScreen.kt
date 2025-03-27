package com.aube.mysize.presentation.ui.screens.my_size

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.toUi
import com.aube.mysize.presentation.model.SizeCardUiModel
import com.aube.mysize.presentation.viewmodel.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.BodySizeViewModel
import com.aube.mysize.presentation.viewmodel.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.TopSizeViewModel

@Composable
fun MySizeScreen(
    bodyViewModel: BodySizeViewModel = hiltViewModel(),
    topViewModel: TopSizeViewModel = hiltViewModel(),
    bottomViewModel: BottomSizeViewModel = hiltViewModel(),
    outerViewModel: OuterSizeViewModel = hiltViewModel(),
    shoeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessoryViewModel: AccessorySizeViewModel = hiltViewModel(),
) {
    val bodySizes by bodyViewModel.sizes.collectAsState()
    val topSizes by topViewModel.sizes.collectAsState()
    val bottomSizes by bottomViewModel.sizes.collectAsState()
    val outerSizes by outerViewModel.sizes.collectAsState()
    val shoeSizes by shoeViewModel.sizes.collectAsState()
    val accessorySizes by accessoryViewModel.sizes.collectAsState()

    val sizeCards = listOfNotNull(
        bodySizes.firstOrNull()?.toUi(),
        topSizes.firstOrNull()?.toUi(),
        bottomSizes.firstOrNull()?.toUi(),
        outerSizes.firstOrNull()?.toUi(),
        shoeSizes.firstOrNull()?.toUi(),
        accessorySizes.firstOrNull()?.toUi()
    )

    MySizeScreen(sizeCards = sizeCards)
}

@Composable
fun MySizeScreen(
    sizeCards: List<SizeCardUiModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(sizeCards) { card ->
            Text(card.title, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            SizeCard(
                title = card.title,
                imageRes = card.imageResId,
                contents = card.contents
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun SizeCard(
    title: String,
    imageRes: Int,
    contents: List<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 140.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = title,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                contents.forEach { text ->
                    Text(text, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}