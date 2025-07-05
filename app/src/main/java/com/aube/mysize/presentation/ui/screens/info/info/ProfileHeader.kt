package com.aube.mysize.presentation.ui.screens.info.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aube.mysize.R
import com.aube.mysize.presentation.model.user.UserInfo

@Composable
fun ProfileHeader(
    user: UserInfo,
    isUploading: Boolean,
    isNetworkConnected: Boolean,
    followersCount: Int,
    followingsCount: Int,
    onFollowClick: () -> Unit,
    onEditClick: () -> Unit,
    onProfileImageClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer.copy(0.3f))
            .padding(24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f)) {
                Text(user.nickname, style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = if (isNetworkConnected) {
                        stringResource(R.string.follow_follow_counts_format, followersCount, followingsCount)
                    } else {
                        stringResource(R.string.follow_followers_and_followings)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable(
                            enabled = isNetworkConnected,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onFollowClick() },
                    color = if (isNetworkConnected) Color.Unspecified else MaterialTheme.colorScheme.primary.copy(0.6f)
                )
            }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable { onProfileImageClick() },
                contentAlignment = Alignment.Center
            ) {
                if (user.profileImageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = user.profileImageUrl,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp).clip(CircleShape)
                    )
                }

                if (isUploading) {
                    Box(
                        modifier = Modifier.matchParentSize().background(Color(0x88000000)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(user.email, style = MaterialTheme.typography.bodySmall)
            Text(
                text = stringResource(R.string.text_edit_profile_title),
                style = MaterialTheme.typography.bodySmall,
                color = if (isNetworkConnected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(0.6f),
                modifier = Modifier.clickable(
                    enabled = isNetworkConnected,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onEditClick() }
            )
        }
    }
}
