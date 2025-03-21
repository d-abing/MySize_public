package com.aube.mysize.presentation.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aube.mysize.presentation.ui.screens.BodyProfileHistoryScreen
import com.aube.mysize.presentation.ui.screens.BodyProfileInputScreen

@Composable
fun MySizeApp(innerPaddings: PaddingValues) {
    Box(modifier = Modifier.padding(innerPaddings)) {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "input") {
            composable("input") {
                BodyProfileInputScreen(viewModel = hiltViewModel()) {
                    navController.navigate("history")
                }
            }
            composable("history") {
                BodyProfileHistoryScreen(viewModel = hiltViewModel())
            }
        }

    }
}
