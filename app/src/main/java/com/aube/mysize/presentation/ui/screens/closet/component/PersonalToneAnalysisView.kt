package com.aube.mysize.presentation.ui.screens.closet.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.Detail
import com.aube.mysize.presentation.model.PersonalToneAnalysisResult
import com.aube.mysize.presentation.model.Season
import com.aube.mysize.presentation.model.Temperature
import com.aube.mysize.presentation.model.toDisplayName

@Composable
fun PersonalToneAnalysisView(
    result: PersonalToneAnalysisResult,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("퍼스널 컬러 분석 \"${result.mainTone.displayName}\"", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))

        ToneBar(
            title = "톤",
            toneMap = result.temperatureRatio,
            toneColor = Temperature.colors,
            labelMapper = { it.toDisplayName() }
        )

        Spacer(Modifier.height(8.dp))
        ToneBar(
            title = "시즌",
            toneMap = result.seasonRatio,
            toneColor = Season.colors,
            labelMapper = { it.toDisplayName() }
        )

        Spacer(Modifier.height(8.dp))
        ToneBar(
            title = "세부",
            toneMap = result.detailRatio,
            toneColor = Detail.colors,
            labelMapper = { it.toDisplayName() }
        )

        Spacer(Modifier.height(16.dp))
        Text("내 옷장에서 가장 잘 어울리는 색상", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        ColorPreviewBox(result.bestMatchedColors)

        Spacer(Modifier.height(16.dp))
        Text("추천 색상", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        ColorPreviewBox(result.recommendedColors)
    }
}


@Composable
fun <T> ToneBar(
    title: String,
    toneMap: Map<T, Float>,
    toneColor: Map<T, Color>,
    labelMapper: (T) -> String
) {
    val total = toneMap.values.sum().coerceAtLeast(0.0001f)
    val rawPercents = toneMap.mapValues { ((it.value / total) * 100).toInt() }.toMutableMap()
    val diff = 100 - rawPercents.values.sum()
    rawPercents.maxByOrNull { it.value }?.let { (k, v) -> rawPercents[k] = v + diff }

    Column {
        Text(text = title, style = MaterialTheme.typography.labelMedium)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            toneMap.forEach { (label, ratio) ->
                if (ratio == 0f) return@forEach
                Box(
                    modifier = Modifier
                        .weight(ratio)
                        .fillMaxHeight()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    toneColor[label]?.copy(alpha = 0.9f) ?: Color.Gray,
                                    toneColor[label]?.copy(alpha = 0.6f) ?: Color.DarkGray
                                )
                            )
                        )
                ) {
                    Text(
                        text = "${rawPercents[label]}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {
            toneMap.forEach { (label, ratio) ->
                if (ratio == 0f) return@forEach

                Box(
                    modifier = Modifier
                        .weight(ratio)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = labelMapper(label),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun ColorPreviewBox(colors: List<Int>) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        colors.forEach { colorInt ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .size(40.dp)
                    .background(Color(colorInt))
            )
        }
    }
}


