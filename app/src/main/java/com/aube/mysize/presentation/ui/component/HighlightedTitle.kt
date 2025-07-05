package com.aube.mysize.presentation.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.ui.theme.MySizeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RowScope.HighlightedTitle(
    text: String,
    isHighlighted: Boolean,
    onAnimationEnd: () -> Unit
) {
    val highlightProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val textMeasurer = rememberTextMeasurer()
    val style = MaterialTheme.typography.titleSmall
    val measuredText = remember(text) {
        textMeasurer.measure(
            text = AnnotatedString(text),
            style = style
        )
    }

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
            .weight(1f)
            .wrapContentHeight()
    ) {
        val color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)

        Canvas(modifier = Modifier.matchParentSize().padding(top = 4.dp)) {
            if (highlightProgress.value > 0f) {
                val totalWidth = measuredText.size.width.toFloat() + 16.dp.toPx()
                val width = (totalWidth * highlightProgress.value).coerceAtLeast(16.dp.toPx())
                val height = measuredText.size.height.toFloat() * 0.7f
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
            modifier = Modifier.padding(start = 8.dp)
                .align(Alignment.CenterStart)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HighlightedTitlePreview() {
    var isHighlighted by remember { mutableStateOf(true) }

    MySizeTheme {
        Column(
            modifier = Modifier.fillMaxWidth()
                .height(48.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HighlightedTitle(
                    text = "신규 사이즈 추천됨",
                    isHighlighted = isHighlighted,
                    onAnimationEnd = {
                        isHighlighted = false
                    }
                )
            }
        }
    }
}
