package com.aube.mysize.presentation.ui.nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.aube.mysize.R
import com.aube.mysize.presentation.model.Screen
import com.aube.mysize.presentation.viewmodel.clothes.ClothesDetailViewModel
import com.aube.mysize.presentation.viewmodel.clothes.MyClothesViewModel
import com.aube.mysize.presentation.viewmodel.clothes.OtherClothesViewModel
import com.aube.mysize.presentation.viewmodel.settings.SettingsViewModel
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel
import com.aube.mysize.presentation.viewmodel.user.AuthViewModel
import com.aube.mysize.presentation.viewmodel.user.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySizeApp(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    sizeViewModel: SizeViewModel = hiltViewModel(),
    myClothesViewModel: MyClothesViewModel = hiltViewModel(),
    otherClothesViewModel: OtherClothesViewModel = hiltViewModel(),
    clothesDetailViewModel: ClothesDetailViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.app_logo),
                            contentDescription = stringResource(R.string.label_app_logo),
                            modifier = Modifier
                                .height(32.dp)
                                .padding(top = 4.dp)
                        )
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
            MySizeBottomBar(
                navController = navController,
                items = listOf(
                    Screen.Recommend,
                    Screen.MySize,
                    Screen.Closet,
                    Screen.AddSize,
                    Screen.MyInfo
                )
            )
        }
    ) { innerPadding ->
        MySizeNavHost(
            navController = navController,
            innerPadding = innerPadding,
            snackbarHostState = snackbarHostState,
            settingsViewModel = settingsViewModel,
            profileViewModel = profileViewModel,
            authViewModel = authViewModel,
            sizeViewModel = sizeViewModel,
            myClothesViewModel = myClothesViewModel,
            otherClothesViewModel = otherClothesViewModel,
            clothesDetailViewModel = clothesDetailViewModel
        )
    }
}