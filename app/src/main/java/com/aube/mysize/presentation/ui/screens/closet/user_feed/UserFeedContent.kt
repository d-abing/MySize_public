package com.aube.mysize.presentation.ui.screens.closet.user_feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.screens.closet.component.closet_grid.PictureGrid

@Composable
fun UserFeedContent(
    isBlocked: Boolean,
    clothes: List<com.aube.mysize.domain.model.clothes.Clothes>,
    onClick: (String) -> Unit,
    onLoadMore: () -> Unit
) {
    HorizontalDivider(thickness = 0.5.dp)

    when {
        isBlocked -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.text_blocked_user_message),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        clothes.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.text_no_posts),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        else -> {
            PictureGrid(
                clothesList = clothes,
                onClick = { onClick(it.id) },
                onLoadMore = onLoadMore
            )
        }
    }
}
