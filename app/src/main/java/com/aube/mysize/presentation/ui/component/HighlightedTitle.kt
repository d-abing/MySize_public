package com.aube.mysize.presentation.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HighlightedTitle(
    text: String,
    isHighlighted: Boolean,
    onAnimationEnd: () -> Unit
) {
    val highlightProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val textMeasurer = rememberTextMeasurer()
    val style = MaterialTheme.typography.titleMedium
    val measuredText = remember(text) {
        textMeasurer.measure(
            text = AnnotatedString(text),
            style = style
        )
    }

    // 트리거되면 애니메이션 시작
    LaunchedEffect(isHighlighted) {
        if (isHighlighted) {
            scope.launch {
                highlightProgress.snapTo(0f)
                highlightProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 600)
                )
                delay(400)
                highlightProgress.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300)
                )
                onAnimationEnd()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        val color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)

        Canvas(modifier = Modifier.matchParentSize()) {
            if (highlightProgress.value > 0f) {
                val totalWidth = measuredText.size.width.toFloat() + 16.dp.toPx()
                val width = (totalWidth * highlightProgress.value).coerceAtLeast(16.dp.toPx())
                val height = 12.dp.toPx()
                drawRoundRect(
                    color = color,
                    topLeft = Offset(0f, (size.height - height) / 2),
                    size = Size(width, height),
                    cornerRadius = CornerRadius(4.dp.toPx())
                )
            }
        }

        Text(
            text = text,
            style = style,

            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
    }
}
