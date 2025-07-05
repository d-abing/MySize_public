package com.aube.mysize.presentation.ui.screens.my_size.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.presentation.model.size.BodySizeCardUiModel
import com.aube.mysize.presentation.ui.component.BodySizeCard
import com.aube.mysize.presentation.viewmodel.settings.SettingsViewModel

@Composable
fun SensitiveBodySizeCard(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    bodySizeCard: BodySizeCardUiModel,
    isBodySizeCardSticky: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    onBodySizeCardStickyChanged: () -> Unit
) {
    val isBodySizeCardExpanded by settingsViewModel.isBodySizeCardExpanded.collectAsState()
    val isBodySizeRevealed by settingsViewModel.isBodySizeRevealed.collectAsState()

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
                    text = stringResource(R.string.body_size_title),
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            Icon(
                modifier = Modifier.size(16.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        settingsViewModel.saveIsBodySizeCardExpanded(!isBodySizeCardExpanded)
                    },
                imageVector = if (isBodySizeCardExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = stringResource(
                    if (isBodySizeCardExpanded) R.string.label_expand else R.string.label_collapse
                )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                modifier = Modifier.size(16.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onBodySizeCardStickyChanged() },
                painter = painterResource(if (isBodySizeCardSticky) R.drawable.keep else R.drawable.keep_off),
                contentDescription = stringResource(
                    if (isBodySizeCardSticky) R.string.label_sticky else R.string.label_unsticky
                )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                modifier = Modifier.size(16.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        settingsViewModel.saveIsBodySizeRevealed(!isBodySizeRevealed)
                    },
                imageVector = if (isBodySizeRevealed) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = stringResource(
                    if (isBodySizeRevealed) R.string.label_hide else R.string.label_show
                )
            )
        }

        if (isBodySizeCardExpanded) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .graphicsLayer { this.alpha = alpha }
                    .blur(blur)
                    .clickable {
                        settingsViewModel.saveIsBodySizeRevealed(!isBodySizeRevealed)
                    }
            ) {
                BodySizeCard(
                    isClickable = isBodySizeRevealed,
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
