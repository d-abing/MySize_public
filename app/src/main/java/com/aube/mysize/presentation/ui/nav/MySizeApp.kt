package com.aube.mysize.presentation.ui.nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.screens.add_size.AddSizeScreen
import com.aube.mysize.presentation.ui.screens.closet.ClosetScreen
import com.aube.mysize.presentation.ui.screens.my_size.MySizeScreen
import com.aube.mysize.presentation.ui.screens.recommend_size.RecommendSizeScreen
import com.aube.mysize.presentation.ui.screens.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySizeApp() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Closet,
        Screen.Recommend,
        Screen.MySize,
        Screen.AddSize,
        Screen.Settings
    )

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.app_logo),
                            contentDescription = "앱 로고",
                            modifier = Modifier
                                .height(32.dp)
                                .padding(top = 4.dp)
                        )
                    },
                    actions = {
                        IconButton(onClick = { /*  */ }) {
                            Icon(
                                imageVector = Icons.Filled.Straighten,
                                tint = Color.Black,
                                modifier = Modifier
                                    .rotate(45f),
                                contentDescription = "흠 아직 미정"
                            )
                        }
                    }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant // 혹은 Color.LightGray
                )
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(110.dp)
            ) {
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
            startDestination = Screen.AddSize.route,
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
