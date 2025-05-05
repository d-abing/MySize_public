package com.aube.mysize.presentation.ui.nav

import android.content.Intent
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aube.mysize.MainActivity
import com.aube.mysize.R
import com.aube.mysize.presentation.model.Screen
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore
import com.aube.mysize.presentation.ui.screens.add_size.AddSizeScreen
import com.aube.mysize.presentation.ui.screens.closet.ClosetScreen
import com.aube.mysize.presentation.ui.screens.closet.add_clothes.AddClothesScreen
import com.aube.mysize.presentation.ui.screens.closet.clothes_detail.ClothesDetailScreen
import com.aube.mysize.presentation.ui.screens.closet.edit_clothes.EditClothesScreen
import com.aube.mysize.presentation.ui.screens.my_size.MySizeScreen
import com.aube.mysize.presentation.ui.screens.my_size.full_detail.FullDetailScreen
import com.aube.mysize.presentation.ui.screens.recommend.RecommendScreen
import com.aube.mysize.presentation.ui.screens.settings.SettingsScreen
import com.aube.mysize.utils.setAppLocale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySizeApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val items = listOf(
        Screen.Recommend,
        Screen.MySize,
        Screen.Closet,
        Screen.AddSize,
        Screen.Settings
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(
                                imageVector = Icons.Filled.Straighten,
                                tint = Color.Black,
                                modifier = Modifier
                                    .rotate(45f),
                                contentDescription = "흠 아직 미정"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    )
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        },
        bottomBar = {
            MySizeBottomBar(navController, items)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Closet.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Recommend.route) {
                RecommendScreen(
                    snackbarHostState = snackbarHostState,
                    onAddNewBodySize = { navController.navigate("add_size?category=ADDBODY") },
                    onEditBodySize = { size ->
                        navController.navigate("add_size?category=BODY&id=${size.id}")
                    }
                )
            }
            composable(Screen.MySize.route) {
                MySizeScreen(
                    onNavigateToFullDetailByCategory = { sizeCategory ->
                        navController.navigate("full_detail?category=${sizeCategory}&brand=")
                    },
                    onNavigateToFullDetailByBrand = { brand ->
                        navController.navigate("full_detail?category=&brand=${brand}")
                    },
                    onEdit = { category, size ->
                        navController.navigate("add_size?category=${category}&id=${size.id}")
                    }
                )
            }
            composable(Screen.Closet.route) {
                ClosetScreen(
                    onClothesClick = { clothes -> navController.navigate("clothes_detail/${clothes.id}") },
                    onNavigateToAddClothes = { navController.navigate("add_clothes") }
                )
            }
            composable(
                route = Screen.ClothesDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                ClothesDetailScreen(
                    clothesId = id,
                    onDelete = { navController.popBackStack() },
                    onEdit = { navController.navigate("edit_clothes/${id}") }
                )
            }
            composable(
                route = Screen.EditClothes.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                EditClothesScreen(
                    clothesId = id,
                    navController = navController,
                    onAddNewBodySize = { navController.navigate("add_size?category=ADDBODY") },
                    onAddNewSize = { selectedCategory -> navController.navigate("add_size?category=${selectedCategory}") }
                )
            }
            composable(Screen.AddClothes.route) {
                AddClothesScreen(
                    navController = navController,
                    onAddNewBodySize = { navController.navigate("add_size?category=ADDBODY") },
                    onAddNewSize = { selectedCategory -> navController.navigate("add_size?category=${selectedCategory}") }
                )
            }
            composable(
                route = Screen.FullDetail.route,
                arguments = listOf(
                    navArgument("category") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                FullDetailScreen(
                    backStackEntry = backStackEntry,
                    onEdit = { category, size ->
                        navController.navigate("add_size?category=${category}&id=${size.id}")
                    }
                )
            }
            composable(
                route = Screen.AddSize.route,
                arguments = listOf(
                    navArgument("category") {
                        type = NavType.StringType
                        defaultValue = "BODY"
                    },
                    navArgument("id") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                AddSizeScreen(navController, backStackEntry, snackbarHostState,
                    onNavigateToMySizeScreen = {
                        navController.navigate("my_size") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen (
                    onLanguageSelected = { selectedLanguage ->
                        setAppLocale(context, selectedLanguage)
                        CoroutineScope(Dispatchers.IO).launch {
                            SettingsDataStore.saveLanguage(context, selectedLanguage)
                        }
                        val restartIntent = Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        context.startActivity(restartIntent)
                    },
                    onBodySizeSelected = { selectedKeys ->
                        CoroutineScope(Dispatchers.IO).launch {
                            SettingsDataStore.saveBodyFields(context, selectedKeys)
                        }
                    }
                )
            }
        }
    }
}