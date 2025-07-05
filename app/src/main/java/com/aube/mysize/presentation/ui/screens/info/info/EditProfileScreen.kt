package com.aube.mysize.presentation.ui.screens.info.info

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.component.button.MSSmallButton
import com.aube.mysize.presentation.ui.component.dialog.WarningDialog
import com.aube.mysize.presentation.viewmodel.user.AuthViewModel
import com.aube.mysize.presentation.viewmodel.user.ProfileViewModel
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions

@Composable
fun EditProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    onMoveToMyInfoScreen: () -> Unit,
    onPasswordChangeButtonClick: () -> Unit,
    onDeleteAccountButtonClick: () -> Unit,
    onDeleteAccount: () -> Unit,
) {
    val context = LocalContext.current
    val user = profileViewModel.user
    val userInfo = profileViewModel.userInfo
    var nickname by remember { mutableStateOf("") }
    val isNicknameValid = nickname.length in 2..12
    var showDeleteDialog by remember { mutableStateOf(false) }

    val isProfileImageDeleting by profileViewModel.isProfileImageDeleting.collectAsState()

    val cropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            result.uriContent?.let { croppedUri ->
                profileViewModel.uploadProfileImage(croppedUri, onMove = onMoveToMyInfoScreen)
            }
        }
    }

    val cropRequest = CropImageContractOptions(
        uri = null,
        cropImageOptions = CropImageOptions().apply {
            imageSourceIncludeCamera = true
            imageSourceIncludeGallery = true
            fixAspectRatio = true
            aspectRatioX = 1
            aspectRatioY = 1
        }
    )

    LaunchedEffect(userInfo.nickname) {
        nickname = userInfo.nickname
    }

    val isPasswordUser = user?.providerData?.any { it.providerId == "password" } == true

    user?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(stringResource(R.string.text_edit_profile_title), style = MaterialTheme.typography.bodyLarge)

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.label_change_profile_image_title), style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .clickable { cropLauncher.launch(cropRequest) },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isProfileImageDeleting) {
                              CircularProgressIndicator(
                                  modifier = Modifier.size(20.dp),
                                  color = MaterialTheme.colorScheme.onBackground,
                                  strokeWidth = 2.dp
                              )
                            } else if (userInfo.profileImageUrl.isNotEmpty()) {
                                AsyncImage(
                                    model = userInfo.profileImageUrl,
                                    contentDescription = stringResource(R.string.label_profile_image),
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.ModeEdit,
                                    contentDescription = stringResource(R.string.action_edit),
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        if (userInfo.profileImageUrl.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.action_delete),
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(16.dp)
                                    .align(Alignment.TopEnd)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        profileViewModel.deleteProfileImage()
                                    }
                            )
                        }
                    }
                }
            }

            Column {
                Text(stringResource(R.string.text_change_nickname_title), style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(8.dp))
                Row {
                    TextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        placeholder = { Text(stringResource(R.string.placeholder_nickname)) },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        isError = !isNicknameValid && nickname.isNotEmpty(),
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions.Default,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent
                        )
                    )
                    MSSmallButton(
                        text = stringResource(R.string.action_edit),
                        modifier = Modifier.padding(start = 8.dp),
                        enabled = isNicknameValid,
                        onClick = {
                            profileViewModel.updateNickname(nickname, onMove = onMoveToMyInfoScreen)
                        }
                    )
                }
            }

            if (isPasswordUser) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onPasswordChangeButtonClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Password, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.text_change_password_title), style = MaterialTheme.typography.bodyMedium)
                }
            }

            Text(
                text = stringResource(R.string.text_delete_account),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (isPasswordUser) onDeleteAccountButtonClick()
                        else showDeleteDialog = true
                    }
            )
        }

        if (showDeleteDialog) {
            WarningDialog(
                title = stringResource(R.string.text_delete_account_title),
                description = stringResource(R.string.text_delete_account_description),
                confirmText = stringResource(R.string.action_delete_account),
                onDismiss = { showDeleteDialog = false },
                onConfirm = {
                    showDeleteDialog = false
                    authViewModel.deleteGoogleUser(
                        context = context,
                        onSuccess = {
                            Toast.makeText(context, context.getString(R.string.text_delete_account_success), Toast.LENGTH_SHORT).show()
                            onDeleteAccount()
                        },
                        onFailure = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                    )
                }
            )
        }
    }
}
