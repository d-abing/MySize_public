package com.aube.mysize.presentation.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StatusBar() {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,     // 상태바 배경
            darkIcons = true         // 상태바 아이콘을 어두운 색으로
        )
    }
}
