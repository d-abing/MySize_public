package com.aube.mysize.presentation.ui.component.mysize

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.BodySizeCardUiModel
import com.aube.mysize.presentation.ui.component.BodySizeCard

@Composable
fun SensitiveBodySizeCard(
    bodySizeCard: BodySizeCardUiModel,
    modifier: Modifier = Modifier
) {
    var isRevealed by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isRevealed) 1f else 0.3f,
        animationSpec = tween(durationMillis = 500),
        label = "AlphaAnimation"
    )
    val blur by animateDpAsState(
        targetValue = if (isRevealed) 0.dp else 8.dp,
        animationSpec = tween(durationMillis = 500),
        label = "BlurAnimation"
    )

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "바디 사이즈",
                style = MaterialTheme.typography.titleMedium,
            )
            Icon(
                modifier = Modifier.size(16.dp)
                    .clickable{ isRevealed = !isRevealed },
                imageVector = if (isRevealed) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = if (isRevealed) "Hide" else "Show"
            )
        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = alpha
                }
                .blur(blur)
                .clickable { isRevealed = !isRevealed }
        ) {
            BodySizeCard(
                title = bodySizeCard.title,
                imageVector = bodySizeCard.imageVector,
                description = bodySizeCard.description
            )
        }
    }
}
