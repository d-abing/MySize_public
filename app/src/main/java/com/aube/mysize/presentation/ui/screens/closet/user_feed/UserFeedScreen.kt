package com.aube.mysize.presentation.ui.screens.closet.user_feed

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aube.mysize.presentation.viewmodel.clothes.OtherClothesViewModel
import com.aube.mysize.presentation.viewmodel.clothes.UserFeedViewModel
import com.aube.mysize.presentation.viewmodel.user.BlockedUserViewModel
import com.aube.mysize.presentation.viewmodel.user.ProfileViewModel
import com.aube.mysize.presentation.viewmodel.user.ReportViewModel

@Composable
fun UserFeedScreen(
    reportViewModel: ReportViewModel = hiltViewModel(),
    blockedUserViewModel: BlockedUserViewModel = hiltViewModel(),
    userFeedViewModel: UserFeedViewModel = hiltViewModel(),
    otherClothesViewModel: OtherClothesViewModel,
    profileViewModel: ProfileViewModel,
    userId: String,
    navController: NavController,
    onReport: () -> Unit,
) {
    val followings by profileViewModel.followings.collectAsState()
    val followers by profileViewModel.followers.collectAsState()
    val isFollowing = followings.any { it.uid == userId }
    val isFollower = followers.any { it.uid == userId }

    val nickname by userFeedViewModel.userNickname.collectAsState()
    val profileImageUrl by userFeedViewModel.userProfileImageUrl.collectAsState()
    val clothes by userFeedViewModel.userClothes.collectAsState()
    val isBlocked = blockedUserViewModel.blockedUsers.collectAsState()
        .value.any { it.uid == userId }

    var showProfileImage by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    var showBlockDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val handle = navController.currentBackStackEntry?.savedStateHandle
    val isReported = handle?.get<Boolean>("is_reported")

    Log.e("isReported", isReported.toString())

    LaunchedEffect(Unit) {
        isReported?.let {
            if(isReported) {
                userFeedViewModel.loadUserFeed(userId)
            }
        }
    }

    LaunchedEffect(userId) {
        userFeedViewModel.loadUserFeed(userId)
    }

    LaunchedEffect(Unit) {
        blockedUserViewModel.loadBlockedUserIds()
    }

    DisposableEffect(Unit) {
        onDispose {
            userFeedViewModel.clearUserFeedState()
        }
    }

    BackHandler {
        navController.previousBackStackEntry?.savedStateHandle?.set("is_blocked", isBlocked)
        navController.previousBackStackEntry?.savedStateHandle?.set("is_reported", isReported)
        navController.popBackStack()
    }

    Column {
        UserFeedHeader(
            profileImageUrl = profileImageUrl,
            nickname = nickname,
            isFollowing = isFollowing,
            onFollowClick = {
                if (isFollowing) {
                    profileViewModel.unfollow(userId)
                } else {
                    profileViewModel.follow(userId) {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            onProfileImageClick = { showProfileImage = true }
        )

        UserFeedActionBar(
            isBlocked = isBlocked,
            onReportClick = { showReportDialog = true },
            onBlockClick = { showBlockDialog = true }
        )

        UserFeedContent(
            isBlocked = isBlocked,
            clothes = clothes,
            onClick = { id ->
                navController.navigate("clothes_detail/$id")
            },
            onLoadMore = {
                userFeedViewModel.loadUserClothesPage(userId)
            }
        )

        UserFeedDialogs(
            profileImageUrl = profileImageUrl,
            showProfileImage = showProfileImage,
            showReportDialog = showReportDialog,
            showBlockDialog = showBlockDialog,
            isBlocked = isBlocked,
            onDismissImage = { showProfileImage = false },
            onDismissReport = { showReportDialog = false },
            onDismissBlock = { showBlockDialog = false },
            onReportConfirm = { reason ->
                reportViewModel.reportUser(userId, reason)
                onReport()
                showReportDialog = false
            },
            onBlockConfirm = {
                if (isBlocked) {
                    blockedUserViewModel.unblockUser(userId)
                    userFeedViewModel.deleteBlockedUser(userId)
                    userFeedViewModel.clearUserFeedState()
                    userFeedViewModel.loadUserFeed(userId)
                } else {
                    if (isFollowing) profileViewModel.unfollow(userId)
                    if (isFollower) profileViewModel.removeFollower(userId)
                    blockedUserViewModel.blockUser(userId)
                }

                otherClothesViewModel.refreshBlockedUsers()
                otherClothesViewModel.refreshOthersClothesList()

                showBlockDialog = false
            }
        )
    }
}
