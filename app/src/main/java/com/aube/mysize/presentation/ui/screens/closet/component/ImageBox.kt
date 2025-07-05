package com.aube.mysize.presentation.ui.screens.closet.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.ui.theme.MySizeTheme
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions

@Composable
fun ImageBox(
    selectedImage: Uri?,
    selectedStep: Int,
    onColorPicked: (Color) -> Unit,
    onDelete: (() -> Unit),
    onPickImage: ((String) -> Unit)
) {
    val cropLauncher = rememberLauncherForActivityResult(
        CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            result.uriContent?.let { croppedUri ->
                onPickImage(croppedUri.toString())
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (selectedImage != null) {
            ImageForColorExtraction(
                selectedImage = selectedImage,
                onColorPicked = onColorPicked,
                selectedStep = selectedStep
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray, RoundedCornerShape(16.dp))
            )
        }

        if (selectedStep == 1) {
            if (selectedImage == null) {
                Icon(
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    imageVector = Icons.Default.PhotoLibrary,
                    contentDescription = stringResource(R.string.label_gallery),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { cropLauncher.launch(cropRequest) }
                        .padding(150.dp)
                )
            } else {
                Icon(
                    tint = MaterialTheme.colorScheme.onBackground,
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.action_delete),
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onDelete() }
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageBoxPreview() {
    MySizeTheme {
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp),
        ) {
            ImageBox(
                selectedImage = null,
                selectedStep = 1,
                onColorPicked = {},
                onDelete = {},
                onPickImage = {}
            )
        }
    }
}
