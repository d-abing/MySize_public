package com.aube.mysize.presentation.ui.screens.info.follow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aube.mysize.R
import com.aube.mysize.domain.model.follow.UserSummary
import com.aube.mysize.presentation.viewmodel.user.ProfileViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun FollowListScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onUserClick: (String) -> Unit
) {
    val followers by profileViewModel.followers.collectAsState()
    val followings by profileViewModel.followings.collectAsState()

    LaunchedEffect(Unit) {
        val currentUid = Firebase.auth.currentUser?.uid.orEmpty()
        if (currentUid.isNotBlank()) {
            profileViewModel.loadFollowList(currentUid)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        FollowListColumn(
            title = stringResource(R.string.follow_followers),
            users = followers,
            onUserClick = onUserClick,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.5.dp)
                .background(Color.LightGray)
        )

        FollowListColumn(
            title = stringResource(R.string.follow_followings),
            users = followings,
            onUserClick = onUserClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FollowListColumn(
    title: String,
    users: List<UserSummary>,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        LazyColumn {
            items(users) { user ->
                UserRow(user = user, onUserClick = onUserClick)
            }
        }
    }
}

@Composable
fun UserRow(user: UserSummary, onUserClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onUserClick(user.uid) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (user.profileImageUrl.isBlank()) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
        } else {
            AsyncImage(
                model = user.profileImageUrl,
                contentDescription = stringResource(R.string.label_profile_image),
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = user.nickname, style = MaterialTheme.typography.bodyMedium)
    }
}
