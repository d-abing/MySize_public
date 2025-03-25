package com.aube.mysize.presentation.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aube.mysize.presentation.ui.screens.add_size.AddSizeScreen
import com.aube.mysize.presentation.ui.screens.closet.ClosetScreen
import com.aube.mysize.presentation.ui.screens.my_size.MySizeScreen
import com.aube.mysize.presentation.ui.screens.recommend_size.RecommendSizeScreen
import com.aube.mysize.presentation.ui.screens.settings.SettingsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Closet,
        Screen.Recommend,
        Screen.MySize,
        Screen.AddSize,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.MySize.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Closet.route) { ClosetScreen() }
            composable(Screen.Recommend.route) { RecommendSizeScreen() }
            composable(Screen.MySize.route) { MySizeScreen() }
            composable(Screen.AddSize.route) { AddSizeScreen { navController.navigate("my_size")} }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}
