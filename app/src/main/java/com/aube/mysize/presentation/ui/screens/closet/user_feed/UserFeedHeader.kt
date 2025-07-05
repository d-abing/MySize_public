package com.aube.mysize.presentation.ui.screens.closet.user_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aube.mysize.R

@Composable
fun UserFeedHeader(
    profileImageUrl: String,
    nickname: String,
    isFollowing: Boolean,
    onFollowClick: () -> Unit,
    onProfileImageClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            AsyncImage(
                model = profileImageUrl,
                contentDescription = stringResource(R.string.label_profile_image),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onProfileImageClick() },
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(text = nickname, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.wrapContentHeight(),
            colors = if (isFollowing) ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary.copy(0.2f),
                contentColor = MaterialTheme.colorScheme.onTertiary.copy(0.7f)
            ) else ButtonDefaults.buttonColors(),
            onClick = onFollowClick
        ) {
            Text(
                text = if (isFollowing)
                    stringResource(R.string.action_unfollow)
                else
                    stringResource(R.string.action_follow),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

