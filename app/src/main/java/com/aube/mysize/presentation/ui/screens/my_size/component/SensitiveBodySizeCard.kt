package com.aube.mysize.presentation.ui.screens.my_size.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.model.BodySizeCardUiModel
import com.aube.mysize.presentation.ui.component.BodySizeCard
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore.saveIsBodySizeCardExpanded
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore.saveIsBodySizeRevealed
import kotlinx.coroutines.launch

@Composable
fun SensitiveBodySizeCard(
    bodySizeCard: BodySizeCardUiModel,
    isBodySizeCardSticky: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    onBodySizeCardStickyChanged: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val isBodySizeCardExpanded by SettingsDataStore.getIsBodySizeCardExpanded(context).collectAsState(initial = true)
    val isBodySizeRevealed by SettingsDataStore.getIsBodySizeRevealed(context).collectAsState(initial = false)

    val alpha by animateFloatAsState(
        targetValue = if (isBodySizeRevealed) 1f else 0.3f,
        animationSpec = tween(durationMillis = 500),
        label = "AlphaAnimation"
    )
    val blur by animateDpAsState(
        targetValue = if (isBodySizeRevealed) 0.dp else 8.dp,
        animationSpec = tween(durationMillis = 500),
        label = "BlurAnimation"
    )

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "바디 사이즈",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Icon(
                modifier = Modifier.size(16.dp)
                    .clickable {
                        coroutineScope.launch {
                            saveIsBodySizeCardExpanded(context, !isBodySizeCardExpanded)
                        }
                    },
                imageVector = if (isBodySizeCardExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = if (isBodySizeCardExpanded) "Expand" else "Collapse"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                modifier = Modifier.size(16.dp)
                    .clickable { onBodySizeCardStickyChanged() },
                painter = painterResource(if (isBodySizeCardSticky) R.drawable.keep else R.drawable.keep_off),
                contentDescription = if (isBodySizeCardSticky) "Sticky" else "UnSticky"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                modifier = Modifier.size(16.dp)
                    .clickable {
                        coroutineScope.launch {
                            saveIsBodySizeRevealed(context, !isBodySizeRevealed)
                        }
                    },
                imageVector = if (isBodySizeRevealed) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = if (isBodySizeRevealed) "Hide" else "Show"
            )
        }

        if (isBodySizeCardExpanded) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .graphicsLayer { this.alpha = alpha }
                    .blur(blur)
                    .clickable {
                        coroutineScope.launch {
                            saveIsBodySizeRevealed(context, !isBodySizeRevealed)
                        }
                    }
            ) {
                BodySizeCard(
                    containerColor = Color.LightGray.copy(0.2f),
                    title = bodySizeCard.title,
                    imageVector = bodySizeCard.imageVector,
                    description = bodySizeCard.description,
                    onEdit = onEdit,
                    onDelete = onDelete,
                )
            }
        }
    }
}
