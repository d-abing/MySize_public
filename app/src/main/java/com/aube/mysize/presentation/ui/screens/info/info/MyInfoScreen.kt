package com.aube.mysize.presentation.ui.screens.info.info

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.component.ad.BannerAdView
import com.aube.mysize.presentation.viewmodel.settings.SettingsViewModel
import com.aube.mysize.presentation.viewmodel.user.AuthViewModel
import com.aube.mysize.presentation.viewmodel.user.ProfileViewModel

@Composable
fun MyInfoScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    onFollowClick: () -> Unit,
    onEditClick: () -> Unit,
    onBodySizeSelected: (Set<String>) -> Unit,
    onLanguageSettingSelected: () -> Unit,
    onBlockedUserClick: () -> Unit,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val user = profileViewModel.userInfo
    val followersCount by profileViewModel.followersCount
    val followingsCount by profileViewModel.followingsCount
    val isUploading by profileViewModel.isUploading.collectAsState()
    val isNetworkConnected by profileViewModel.isConnected.collectAsState()
    val selectedKeys by settingsViewModel.bodyFields.collectAsState()

    var showProfileImage by remember { mutableStateOf(false) }
    var bodySizeSettingExpanded by remember { mutableStateOf(false) }

    if (showProfileImage && user.profileImageUrl.isNotEmpty()) {
        Dialog(onDismissRequest = { showProfileImage = false }) {
            AsyncImage(
                model = ImageRequest.Builder(context).data(user.profileImageUrl).build(),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clip(CircleShape)
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                ProfileHeader(
                    user = user,
                    isUploading = isUploading,
                    isNetworkConnected = isNetworkConnected,
                    followersCount = followersCount,
                    followingsCount = followingsCount,
                    onFollowClick = onFollowClick,
                    onEditClick = onEditClick,
                    onProfileImageClick = { showProfileImage = true }
                )
            }

            item {
                SettingsSection(
                    context = context,
                    selectedKeys = selectedKeys,
                    bodySizeSettingExpanded = bodySizeSettingExpanded,
                    onToggleBodySetting = { bodySizeSettingExpanded = !bodySizeSettingExpanded },
                    onBodySizeSelected = onBodySizeSelected,
                    onBlockedUserClick = onBlockedUserClick,
                    onLanguageSettingClick = onLanguageSettingSelected
                )
            }
        }

        OutlinedButton(
            onClick = {
                authViewModel.signOut(
                    context = context,
                    onSuccess = {
                        Toast.makeText(context, context.getString(R.string.text_log_out_success), Toast.LENGTH_SHORT).show()
                        onSignOut()
                    },
                    onFailure = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.action_log_out))
        }

        Spacer(modifier = Modifier.height(8.dp))
        BannerAdView(adUnitId = stringResource(R.string.ad_unit_id_banner))
    }
}
