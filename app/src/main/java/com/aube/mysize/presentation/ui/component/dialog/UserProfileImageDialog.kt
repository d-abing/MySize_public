package com.aube.mysize.presentation.ui.component.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.aube.mysize.R

@Composable
fun UserProfileImageDialog(
    profileImageUrl: String,
    onDismiss: () -> Unit
) {
    Dialog (
        onDismissRequest = onDismiss
    ) {
        AsyncImage(
            model = profileImageUrl,
            contentDescription = stringResource(R.string.label_profile_image),
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape)
        )
    }
} 