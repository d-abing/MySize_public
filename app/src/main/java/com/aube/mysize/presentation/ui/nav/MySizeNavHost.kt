package com.aube.mysize.presentation.ui.nav


import AddSizeScreen
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aube.mysize.MainActivity
import com.aube.mysize.R
import com.aube.mysize.presentation.model.Screen
import com.aube.mysize.presentation.ui.screens.closet.ClosetScreen
import com.aube.mysize.presentation.ui.screens.closet.add_clothes.AddClothesScreen
import com.aube.mysize.presentation.ui.screens.closet.clothes_detail.ClothesDetailScreen
import com.aube.mysize.presentation.ui.screens.closet.edit_clothes.EditClothesScreen
import com.aube.mysize.presentation.ui.screens.closet.user_feed.UserFeedScreen
import com.aube.mysize.presentation.ui.screens.info.blocking.BlockedUserListScreen
import com.aube.mysize.presentation.ui.screens.info.follow.FollowListScreen
import com.aube.mysize.presentation.ui.screens.info.info.ChangePasswordScreen
import com.aube.mysize.presentation.ui.screens.info.info.DeleteAccountScreen
import com.aube.mysize.presentation.ui.screens.info.info.EditProfileScreen
import com.aube.mysize.presentation.ui.screens.info.info.LanguageSettingScreen
import com.aube.mysize.presentation.ui.screens.info.info.MyInfoScreen
import com.aube.mysize.presentation.ui.screens.my_size.MySizeScreen
import com.aube.mysize.presentation.ui.screens.my_size.all_sizes.AllSizesScreen
import com.aube.mysize.presentation.ui.screens.recommend.RecommendScreen
import com.aube.mysize.presentation.viewmodel.clothes.ClothesDetailViewModel
import com.aube.mysize.presentation.viewmodel.clothes.MyClothesViewModel
import com.aube.mysize.presentation.viewmodel.clothes.OtherClothesViewModel
import com.aube.mysize.presentation.viewmodel.settings.SettingsViewModel
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel
import com.aube.mysize.presentation.viewmodel.user.AuthViewModel
import com.aube.mysize.presentation.viewmodel.user.ProfileViewModel
import com.aube.mysize.utils.navigateToLogin
import com.aube.mysize.utils.setAppLocale

@Composable
fun MySizeNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    settingsViewModel: SettingsViewModel,
    profileViewModel: ProfileViewModel,
    authViewModel: AuthViewModel,
    sizeViewModel: SizeViewModel,
    myClothesViewModel: MyClothesViewModel,
    otherClothesViewModel: OtherClothesViewModel,
    clothesDetailViewModel: ClothesDetailViewModel
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Closet.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Recommend.route) {
            RecommendScreen(
                sizeViewModel = sizeViewModel,
                snackbarHostState = snackbarHostState,
                onAddNewBodySize = { navController.navigate("add_size?category=ADDBODY") },
                onEditBodySize = { size ->
                    navController.navigate("add_size?category=BODY&id=${size.id}")
                }
            )
        }
        composable(Screen.MySize.route) {
            MySizeScreen(
                sizeViewModel = sizeViewModel,
                myClothesViewModel = myClothesViewModel,
                onNavigateToAllSizesByCategory = { sizeCategory ->
                    navController.navigate("all_sizes?category=${sizeCategory}&brand=")
                },
                onNavigateToAllSizesByBrand = { brand ->
                    navController.navigate("all_sizes?category=&brand=${brand}")
                },
                onEdit = { category, size ->
                    navController.navigate("add_size?category=${category}&id=${size.id}")
                }
            )
        }
        composable(
            route = Screen.AllSizes.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            AllSizesScreen(
                myClothesViewModel = myClothesViewModel,
                sizeViewModel = sizeViewModel,
                backStackEntry = backStackEntry,
                onEdit = { category, size ->
                    navController.navigate("add_size?category=${category}&id=${size.id}")
                }
            )
        }
        composable(Screen.Closet.route) {
            ClosetScreen(
                myClothesViewModel = myClothesViewModel,
                otherClothesViewModel = otherClothesViewModel,
                profileViewModel = profileViewModel,
                navController = navController,
                onClothesClick = { clothes ->
                    navController.navigate("clothes_detail/${clothes.id}")
                },
                onNavigateToAddClothes = { navController.navigate("add_clothes") },
                onUserClick = { uid ->
                    navController.navigate("user_feed/${uid}")
                }
            )
        }
        composable(
            route = Screen.ClothesDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable

            ClothesDetailScreen(
                clothesDetailViewModel = clothesDetailViewModel,
                myClothesViewModel = myClothesViewModel,
                otherClothesViewModel = otherClothesViewModel,
                sizeViewModel = sizeViewModel,
                profileViewModel = profileViewModel,
                navController = navController,
                clothesId = id,
                onDelete = { navController.popBackStack() },
                onEdit = { navController.navigate("edit_clothes/${id}") },
                onProfileClick = { navController.navigate("user_feed/$it") }
            )
        }
        composable(
            route = Screen.UserFeed.route,
            arguments = listOf(navArgument("uid") { type = NavType.StringType })
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: return@composable

            UserFeedScreen(
                otherClothesViewModel = otherClothesViewModel,
                profileViewModel = profileViewModel,
                userId = uid,
                navController = navController,
                onReport = {
                    Toast.makeText(context, context.getString(R.string.report_success), Toast.LENGTH_SHORT).show()
                }
            )

        }
        composable(
            route = Screen.EditClothes.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable

            EditClothesScreen(
                myClothesViewModel = myClothesViewModel,
                clothesDetailViewModel = clothesDetailViewModel,
                sizeViewModel = sizeViewModel,
                clothesId = id,
                navController = navController,
                onAddNewBodySize = { navController.navigate("add_size?category=ADDBODY") },
                onAddNewSize = { selectedCategory ->
                    navController.navigate("add_size?category=${selectedCategory}")
                },
            )
        }
        composable(Screen.AddClothes.route) {
            AddClothesScreen(
                myClothesViewModel = myClothesViewModel,
                navController = navController,
                onAddNewBodySize = { navController.navigate("add_size?category=ADDBODY") },
                onAddNewSize = { selectedCategory ->
                    navController.navigate("add_size?category=${selectedCategory}")
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
                    type = NavType.StringType
                    defaultValue = "No Id"
                }
            )
        ) { backStackEntry ->
            AddSizeScreen(
                sizeViewModel = sizeViewModel,
                navController = navController,
                backStackEntry = backStackEntry,
                snackbarHostState = snackbarHostState,
                onNavigateToMySizeScreen = {
                    navController.navigate("my_size") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.MyInfo.route){
            MyInfoScreen(
                profileViewModel = profileViewModel,
                authViewModel = authViewModel,
                onFollowClick = { navController.navigate("follow") },
                onEditClick = { navController.navigate(Screen.EditProfile.route) },
                onBodySizeSelected = { selectedKeys ->
                    settingsViewModel.saveBodyFields(selectedKeys)
                },
                onBlockedUserClick = { navController.navigate(Screen.BlockedUser.route) },
                onLanguageSettingSelected = {
                    navController.navigate(Screen.LanguageSetting.route)
                },
                onSignOut = { context.navigateToLogin() }
            )
        }
        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                profileViewModel = profileViewModel,
                authViewModel = authViewModel,
                onMoveToMyInfoScreen = { navController.popBackStack() },
                onPasswordChangeButtonClick = { navController.navigate(Screen.EditPassword.route) },
                onDeleteAccountButtonClick = { navController.navigate(Screen.DeleteAccount.route) },
                onDeleteAccount = { context.navigateToLogin() }
            )
        }
        composable(Screen.EditPassword.route) {
            ChangePasswordScreen(
                authViewModel = authViewModel,
                onPasswordUpdated = { navController.popBackStack() },
            )
        }
        composable(Screen.DeleteAccount.route) {
            DeleteAccountScreen(
                authViewModel = authViewModel,
                onDeleteAccount = {
                    context.navigateToLogin()
                }
            )
        }
        composable(Screen.Follow.route) {
            FollowListScreen(
                profileViewModel = profileViewModel,
                onUserClick = { navController.navigate("user_feed/$it") }
            )
        }
        composable(Screen.BlockedUser.route) {
            BlockedUserListScreen(
                otherClothesViewModel = otherClothesViewModel
            )
        }
        composable(Screen.LanguageSetting.route) {
            LanguageSettingScreen(
                onLanguageSelected = { selectedLanguage ->
                    setAppLocale(context, selectedLanguage)
                    settingsViewModel.saveLanguage(selectedLanguage)
                    val restartIntent = Intent(context, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(restartIntent)
                }
            )
        }
    }
}
