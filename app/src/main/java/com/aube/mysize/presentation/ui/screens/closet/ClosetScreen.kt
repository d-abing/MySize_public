package com.aube.mysize.presentation.ui.screens.closet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.aube.mysize.R
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.ui.component.chip_tap.MSTabRow
import com.aube.mysize.presentation.ui.screens.closet.component.section.MyClosetSection
import com.aube.mysize.presentation.ui.screens.closet.component.section.OthersClosetSection
import com.aube.mysize.presentation.viewmodel.clothes.MyClothesViewModel
import com.aube.mysize.presentation.viewmodel.clothes.OtherClothesViewModel
import com.aube.mysize.presentation.viewmodel.user.ProfileViewModel
import com.aube.mysize.presentation.viewmodel.user.UserSearchViewModel

@Composable
fun ClosetScreen(
    userSearchViewModel: UserSearchViewModel = hiltViewModel(),
    myClothesViewModel: MyClothesViewModel,
    otherClothesViewModel: OtherClothesViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    onClothesClick: (Clothes) -> Unit,
    onNavigateToAddClothes: () -> Unit,
    onUserClick: (String) -> Unit,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var selectedViewMode by rememberSaveable { mutableIntStateOf(0) }
    val isNetworkConnected by profileViewModel.isConnected.collectAsState()

    val handle = navController.currentBackStackEntry?.savedStateHandle
    val isBlocked = handle?.get<Boolean>("is_blocked")

    LaunchedEffect(Unit) {
        otherClothesViewModel.loadRecentClothes()
        isBlocked?.let {
            if(isBlocked) {
                otherClothesViewModel.refreshOthersClothesList()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        MSTabRow(
            listOf(
                stringResource(R.string.closet_tab_my),
                stringResource(R.string.closet_tab_others)
            ),
            selectedTabIndex = selectedTab,
            onTabSelected = {
                selectedTab = it
                selectedViewMode = 0
            }
        )
        HorizontalDivider(thickness = 0.5.dp)

        if (selectedTab == 0) {
            MyClosetSection(
                myClothesViewModel = myClothesViewModel,
                isNetworkConnected = isNetworkConnected,
                selectedViewMode = selectedViewMode,
                onViewModeChange = { selectedViewMode = it },
                onClothesClick = { clothes ->
                    if (clothes.createUserId != profileViewModel.user?.uid) {
                        otherClothesViewModel.saveRecentClothesView(clothes.id)
                    }
                    onClothesClick(clothes)
                },
                onNavigateToAddClothes = onNavigateToAddClothes
            )
        } else {
            OthersClosetSection(
                userSearchViewModel = userSearchViewModel,
                otherClothesViewModel = otherClothesViewModel,
                profileViewModel = profileViewModel,
                selectedViewMode = selectedViewMode,
                onViewModeChange = { selectedViewMode = it },
                onClothesClick = { clothes ->
                    if (clothes.createUserId != profileViewModel.user?.uid) {
                        otherClothesViewModel.saveRecentClothesView(clothes.id)
                    }
                    onClothesClick(clothes)
                },
                onUserClick = onUserClick,
                isNetworkConnected = isNetworkConnected
            )
        }
    }
}
