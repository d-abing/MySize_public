package com.aube.mysize.presentation.ui.component.chip_tap

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun MSRainbowBorderChip(
    label: String,
    modifier: Modifier,
) {
    val transition = rememberInfiniteTransition(label = "sweep")
    val sweepOffset by transition.animateFloat(
        initialValue = -200f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

    val baseBorderColor = MaterialTheme.colorScheme.secondary
    val lightBrush = Brush.linearGradient(
        colors = listOf(
            Color.Transparent,
            Color.White.copy(alpha = 0.7f),
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
            Color.Transparent
        ),
        start = Offset(sweepOffset, sweepOffset),
        end = Offset(sweepOffset + 100f, sweepOffset + 100f)
    )

    Box(
        modifier = modifier
            .height(36.dp)
            .drawBehind {
                drawRoundRect(
                    color = baseBorderColor,
                    size = size,
                    style = Stroke(width = 1.dp.toPx()),
                    cornerRadius = CornerRadius(24.dp.toPx())
                )

                drawRoundRect(
                    brush = lightBrush,
                    size = size,
                    style = Stroke(width = 1.dp.toPx()),
                    cornerRadius = CornerRadius(24.dp.toPx())
                )
            }
            .padding(horizontal = 16.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RainbowBorderChipPreview() {
    MySizeTheme {
        MSRainbowBorderChip(
            label = "❤",
            modifier = Modifier
        )
    }
}
