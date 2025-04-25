package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.model.Visibility
import com.aube.mysize.presentation.ui.component.closet.formatAccessorySize
import com.aube.mysize.presentation.ui.component.closet.formatBottomSize
import com.aube.mysize.presentation.ui.component.closet.formatOnePieceSize
import com.aube.mysize.presentation.ui.component.closet.formatOuterSize
import com.aube.mysize.presentation.ui.component.closet.formatShoeSize
import com.aube.mysize.presentation.ui.component.closet.formatTopSize
import com.aube.mysize.presentation.viewmodel.clothes.ClothesViewModel
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ClothesDetailScreen(
    clothesId: Int,
    onDelete: () -> Unit,
    onModify: () -> Unit = {},
    clothesViewModel: ClothesViewModel = hiltViewModel(),
    topSizeViewModel: TopSizeViewModel = hiltViewModel(),
    bottomSizeViewModel: BottomSizeViewModel = hiltViewModel(),
    outerSizeViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceSizeViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeSizeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessorySizeViewModel: AccessorySizeViewModel = hiltViewModel()
) {
    var clothes by remember { mutableStateOf<Clothes?>(null) }

    LaunchedEffect(clothesId) {
        clothes = clothesViewModel.getById(clothesId)
    }

    clothes?.let { currentClothes ->
        val imageBitmap = remember(currentClothes.imageBytes) {
            BitmapFactory.decodeByteArray(currentClothes.imageBytes, 0, currentClothes.imageBytes.size).asImageBitmap()
        }

        val summaries = listOfNotNull(
            currentClothes.linkedSizeIds["TOP"]?.let { topSizeViewModel.getSizeById(it)?.let(::formatTopSize) },
            currentClothes.linkedSizeIds["BOTTOM"]?.let { bottomSizeViewModel.getSizeById(it)?.let(::formatBottomSize) },
            currentClothes.linkedSizeIds["OUTER"]?.let { outerSizeViewModel.getSizeById(it)?.let(::formatOuterSize) },
            currentClothes.linkedSizeIds["ONE_PIECE"]?.let { onePieceSizeViewModel.getSizeById(it)?.let(::formatOnePieceSize) },
            currentClothes.linkedSizeIds["SHOE"]?.let { shoeSizeViewModel.getSizeById(it)?.let(::formatShoeSize) },
            currentClothes.linkedSizeIds["ACCESSORY"]?.let { accessorySizeViewModel.getSizeById(it)?.let(::formatAccessorySize) }
        )

        val formattedDate = currentClothes.createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                ) {
                    /* TODO */
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("사용자 ${currentClothes.createUserId}", style = MaterialTheme.typography.labelMedium)
                    Text(formattedDate, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                }

                if(currentClothes.createUserId != 1L) { /* TODO */
                    Button(
                        modifier = Modifier
                            .wrapContentHeight(),
                        onClick = {}
                    ) {
                        Text(
                            text = "팔로우",
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "삭제",
                        modifier = Modifier.clickable {
                            clothesViewModel.delete(currentClothes)
                            onDelete()
                        }
                    )
                }
            }

            if(currentClothes.visibility == Visibility.PUBLIC && currentClothes.createUserId != 1L) { /* TODO */
                if (currentClothes.sharedBodyFields.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("공개된 신체 정보:", style = MaterialTheme.typography.labelSmall)
                    currentClothes.sharedBodyFields.forEach { field ->
                        Text("- $field", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }

            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!currentClothes.memo.isNullOrBlank()) {
                Text(
                    text = currentClothes.memo,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            if (currentClothes.tags.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    currentClothes.tags.forEach { tag ->
                        Text(
                            text = "#${tag}",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            summaries.forEach { summary ->
                Log.e("ClothesDetailScreen", "summary: $summary")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(currentClothes.dominantColor).copy(0.4f)
                    ),
                ) {
                    Text(
                        text = summary.trimEnd(),
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onTertiary),
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }
        }
    }
}
