package com.aube.mysize.presentation.ui.component.closet

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.aube.mysize.utils.colorToHexString
import com.aube.mysize.utils.isColorBright

@Composable
fun ColorGrid(
    modifier: Modifier = Modifier,
    colorList: List<Int>,
    onColorSelected: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(70.dp)
    ) {
        items(
            items = colorList,
            key = { color -> color }
        ) { color ->
            GalleryColorItem(
                color = color,
                onColorSelected = {
                    onColorSelected(it)
                }
            )
        }
    }
}


@Composable
fun GalleryColorItem(
    color: Int,
    onColorSelected: (Int) -> Unit,
) {
    var isClicking by remember { mutableStateOf(false) }
    val actualSize = calculateColorSize(70.dp, 8.dp, 4.dp)

    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(70.dp)
            .height(actualSize)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onColorSelected(color)
                    },
                    onTap = {
                        isClicking = !isClicking
                    }
                )
            },
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
                val hexString = colorToHexString(color)
                Text(
                    fontSize = 12.sp,
                    text = hexString,
                    color = if (isColorBright(color)) Color.Black else Color.White
                )
            }
        }
    }
}

@Composable
fun calculateColorSize(minSize: Dp, layoutPaddings: Dp, contentPaddings: Dp): Dp {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp // 화면의 너비를 dp로 가져옴
    val actualScreenWidth = screenWidth - (layoutPaddings * 2) // 패딩을 제외한 실제 화면 너비 계산
    val columnCount = (actualScreenWidth / minSize).toInt() // 생성될 열의 개수 계산
    val paddingWidth = (columnCount + 1) * contentPaddings // 패딩의 총 너비 계산
    return (actualScreenWidth - paddingWidth) / columnCount // 각 열의 너비 계산
}