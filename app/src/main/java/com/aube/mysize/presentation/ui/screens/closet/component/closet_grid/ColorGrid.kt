package com.aube.mysize.presentation.ui.screens.closet.component.closet_grid

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aube.mysize.R
import com.aube.mysize.presentation.constants.AnimationConstants
import com.aube.mysize.presentation.model.clothes.PersonalToneAnalysisResult
import com.aube.mysize.presentation.ui.component.button.MSRainbowBorderFAB
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.presentation.ui.screens.closet.component.PersonalToneAnalysisView
import com.aube.mysize.ui.theme.MySizeTheme
import com.aube.mysize.utils.color_anlysis.analyzePersonalTone
import com.aube.mysize.utils.copyToClipboard
import com.aube.mysize.utils.isColorBright

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorGrid(
    colorList: List<Int>,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by rememberSaveable { mutableStateOf(false) }

    val showFab by remember { mutableStateOf(true) }
    val fabEnterAnimation = remember { Animatable(0f) }

    var analysisResult by remember { mutableStateOf<PersonalToneAnalysisResult?>(null) }

    LaunchedEffect(showFab) {
        fabEnterAnimation.animateTo(
            targetValue = 20f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
    }

    if(colorList.isNotEmpty()) {
        LaunchedEffect(colorList) {
            analysisResult = analyzePersonalTone(colorList)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            columns = GridCells.Adaptive(70.dp)
        ) {
            items(
                items = colorList,
                key = { color -> color }
            ) { color ->
                ColorItem(
                    color = color,
                )
            }
        }

        if(colorList.isNotEmpty()) {
            MSRainbowBorderFAB(
                onClick = { showSheet = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 90.dp, bottom = fabEnterAnimation.value.dp)
                    .width(80.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Animation(AnimationConstants.REPORT_ANIMATION, modifier = Modifier.height(50.dp))
                }
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = null,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text(
                    text =  stringResource(R.string.text_color_analysis_report_title),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
                PersonalToneAnalysisView(analysisResult)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ColorGridPreview() {
    val sampleColors = listOf(
        0xFFE57373.toInt(), 0xFF64B5F6.toInt(), 0xFFFFD54F.toInt(),
        0xFF81C784.toInt(), 0xFFBA68C8.toInt(), 0xFFFF8A65.toInt()
    )

    MaterialTheme {
        ColorGrid(colorList = sampleColors)
    }
}


@Composable
fun ColorItem(
    color: Int,
) {
    val context = LocalContext.current
    var isClicking by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(70.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { isClicking = !isClicking },
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(color)
        ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (isClicking) {
                val hexString = String.format("#%06X", color and 0xFFFFFF)
                Text(
                    fontSize = 12.sp,
                    text = hexString,
                    color = if (isColorBright(color)) Color.Black else Color.White,
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    context.copyToClipboard(hexString)
                                }
                            )
                        }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ColorItemPreview() {
    MySizeTheme {
        ColorItem(0xFFE57373.toInt())
    }
}
