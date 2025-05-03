package com.aube.mysize.presentation.ui.screens.closet.my_closet

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aube.mysize.presentation.ui.component.button.RainbowBorderFAB
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.presentation.ui.screens.closet.component.PersonalToneAnalysisView
import com.aube.mysize.utils.color_anlysis.analyzePersonalTone
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

    LaunchedEffect(showFab) {
        fabEnterAnimation.animateTo(
            targetValue = 20f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
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
            RainbowBorderFAB(
                onClick = { showSheet = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 90.dp, bottom = fabEnterAnimation.value.dp)
                    .width(80.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Animation("report.json", modifier = Modifier.size(50.dp))
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
                    text =  "🎨 색상 분석 리포트 📑",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
                val result = analyzePersonalTone(colorList)
                PersonalToneAnalysisView(result)
            }
        }
    }
}


@Composable
fun ColorItem(
    color: Int,
) {
    var isClicking by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(70.dp)
            .clickable { isClicking = !isClicking },
        elevation = CardDefaults.cardElevation(2.dp),
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
                    color = if (isColorBright(color)) Color.Black else Color.White
                )
            }
        }
    }
}