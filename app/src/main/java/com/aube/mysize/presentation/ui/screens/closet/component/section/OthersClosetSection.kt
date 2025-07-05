package com.aube.mysize.presentation.ui.screens.closet.component.section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.model.clothes.TagGridMode
import com.aube.mysize.presentation.ui.component.EmptyListAnimation
import com.aube.mysize.presentation.ui.screens.closet.component.ClosetViewModeTabs
import com.aube.mysize.presentation.ui.screens.closet.component.DeleteAllHistoryButton
import com.aube.mysize.presentation.ui.screens.closet.component.SearchUserOverlay
import com.aube.mysize.presentation.ui.screens.closet.component.closet_grid.PictureGrid
import com.aube.mysize.presentation.ui.screens.closet.component.closet_grid.TagGrid
import com.aube.mysize.presentation.viewmodel.clothes.OtherClothesViewModel
import com.aube.mysize.presentation.viewmodel.user.ProfileViewModel
import com.aube.mysize.presentation.viewmodel.user.UserSearchViewModel

@Composable
fun OthersClosetSection(
    userSearchViewModel: UserSearchViewModel,
    otherClothesViewModel: OtherClothesViewModel,
    profileViewModel: ProfileViewModel,
    selectedViewMode: Int,
    onViewModeChange: (Int) -> Unit,
    onClothesClick: (Clothes) -> Unit,
    onUserClick: (String) -> Unit,
    isNetworkConnected: Boolean
) {
    var showSearch by remember { mutableStateOf(false) }
    val searchQuery by userSearchViewModel.queryFlow.collectAsState()
    val otherClothes by otherClothesViewModel.othersClothes.collectAsState()
    val followingClothes by otherClothesViewModel.followingClothes.collectAsState()
    val tagFilteredClothes by otherClothesViewModel.tagFilteredClothes.collectAsState()
    val recentClothes by otherClothesViewModel.recentClothes.collectAsState()
    val isRefreshing by otherClothesViewModel.isRefreshing.collectAsState()
    val followingUids = profileViewModel.followings.collectAsState().value.map { it.uid }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ClosetViewModeTabs(
                closetViewModes = listOf(
                    Icons.Default.GridView,
                    Icons.Default.Favorite,
                    Icons.Default.Tag,
                    Icons.Default.History
                ),
                selectedTabIndex = selectedViewMode,
                onTabSelected = onViewModeChange
            )

            when (selectedViewMode) {
                0 -> PictureGrid(
                    clothesList = otherClothes,
                    onClick = onClothesClick,
                    onLoadMore = { otherClothesViewModel.loadOtherClothesList() },
                    onRefresh = { otherClothesViewModel.refreshOthersClothesList() },
                    isRefreshing = isRefreshing
                )
                1 -> PictureGrid(
                    clothesList = followingClothes,
                    onClick = onClothesClick,
                    onLoadMore = { otherClothesViewModel.loadFollowingClothesPage(followingUids) }
                )
                2 -> TagGrid(
                    clothesList = tagFilteredClothes,
                    onClick = onClothesClick,
                    mode = TagGridMode.REMOTE,
                    setEmptyList = { otherClothesViewModel.clearTagSearchState() },
                    onSearch = {
                        otherClothesViewModel.clearTagSearchState()
                        otherClothesViewModel.loadClothesByTag(it)
                    },
                    onLoadMore = { otherClothesViewModel.loadClothesByTag(it) }
                )
                3 -> Box(modifier = Modifier.fillMaxSize()) {
                    PictureGrid(clothesList = recentClothes, onClick = onClothesClick)
                    if (recentClothes.isNotEmpty()) {
                        DeleteAllHistoryButton { otherClothesViewModel.deleteAllHistory() }
                    }
                }
            }
        }

        if ((selectedViewMode == 0 && otherClothes.isEmpty()) ||
            (selectedViewMode == 1 && followingClothes.isEmpty()) ||
            (selectedViewMode == 3 && recentClothes.isEmpty())
        ) {
            EmptyListAnimation("closet_empty.json")
        }

        if (showSearch) {
            SearchUserOverlay(
                searchQuery = searchQuery,
                userSearchViewModel = userSearchViewModel,
                onUserClick = onUserClick,
                onDismiss = { showSearch = false }
            )
        }

        if (isNetworkConnected) {
            FloatingActionButton(
                onClick = { showSearch = true },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(20.dp).align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Default.Search, contentDescription = stringResource(R.string.label_search_user))
            }
        }
    }
}
