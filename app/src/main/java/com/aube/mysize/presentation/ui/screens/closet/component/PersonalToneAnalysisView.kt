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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.model.clothes.Detail
import com.aube.mysize.presentation.model.clothes.PersonalToneAnalysisResult
import com.aube.mysize.presentation.model.clothes.Season
import com.aube.mysize.presentation.model.clothes.Temperature
import com.aube.mysize.presentation.model.clothes.ToneCombination
import com.aube.mysize.presentation.model.clothes.toDisplayName
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun PersonalToneAnalysisView(
    result: PersonalToneAnalysisResult?,
    modifier: Modifier = Modifier
) {
    result?.let {
        LazyColumn(modifier = modifier.padding(16.dp)) {
            item {
                Text(
                    text = stringResource(
                        R.string.text_personal_tone_analysis_result,
                        "\"${result.mainTone.displayName}\""
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(12.dp))

                ToneBar(
                    title = stringResource(R.string.text_tone),
                    toneMap = result.temperatureRatio,
                    toneColor = Temperature.colors,
                    labelMapper = { it.toDisplayName() }
                )

                Spacer(Modifier.height(8.dp))
                ToneBar(
                    title = stringResource(R.string.text_season),
                    toneMap = result.seasonRatio,
                    toneColor = Season.colors,
                    labelMapper = { it.toDisplayName() }
                )

                Spacer(Modifier.height(8.dp))
                ToneBar(
                    title = stringResource(R.string.text_detail),
                    toneMap = result.detailRatio,
                    toneColor = Detail.colors,
                    labelMapper = { it.toDisplayName() }
                )

                Spacer(Modifier.height(16.dp))
                Text(stringResource(R.string.text_best_match_colors), style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                ColorPreviewBox(result.bestMatchedColors)

                Spacer(Modifier.height(16.dp))
                Text(stringResource(R.string.text_recommended_colors), style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                ColorPreviewBox(result.recommendedColors)
            }
        }
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
                        text = stringResource(R.string.format_percentage, rawPercents[label] ?: 0),
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

@Preview(showBackground = true)
@Composable
fun PersonalToneAnalysisViewPreview() {

    @Composable
    fun sampleToneResult() = PersonalToneAnalysisResult(
        mainTone = ToneCombination(Temperature.WARM, Season.SPRING, Detail.SOFT) ,
        temperatureRatio = mapOf(Temperature.WARM to 0.7f, Temperature.COOL to 0.3f),
        seasonRatio = mapOf(Season.SPRING to 0.6f, Season.SUMMER to 0.4f),
        detailRatio = mapOf(Detail.MUTED to 0.3f, Detail.SOFT to 0.5f, Detail.DEEP to 0.2f),
        bestMatchedColors = listOf(0xFFE57373.toInt(), 0xFF64B5F6.toInt(), 0xFFFFD54F.toInt()),
        recommendedColors = listOf(0xFF81C784.toInt(), 0xFFBA68C8.toInt(), 0xFFFF8A65.toInt())
    )

    MySizeTheme {
        PersonalToneAnalysisView(
            result = sampleToneResult()
        )
    }
}