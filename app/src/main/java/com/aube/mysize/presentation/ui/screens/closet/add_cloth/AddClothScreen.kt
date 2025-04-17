package com.aube.mysize.presentation.ui.screens.closet.add_cloth

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.domain.model.Cloth
import com.aube.mysize.presentation.ui.component.MSIconButton
import com.aube.mysize.presentation.ui.component.closet.ImageBox
import com.aube.mysize.presentation.viewmodel.cloth.ClothViewModel
import com.aube.mysize.utils.generateMD5Hash
import com.aube.mysize.utils.getBitmapFromUri
import com.aube.mysize.utils.toBytes
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun AddClothScreen(
    snackbarHostState: SnackbarHostState,
    clothViewModel: ClothViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedColor by remember { mutableStateOf<Color?>(null) }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val cropLauncher = rememberLauncherForActivityResult(
        CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            result.uriContent?.let { croppedUri ->
                selectedImage = croppedUri
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            photoUri?.let {
                cropLauncher.launch(
                    CropImageContractOptions(
                        it,
                        CropImageOptions().apply {
                            fixAspectRatio = true
                            aspectRatioX = 1
                            aspectRatioY = 1
                        }
                    )
                )
            }
        }
        photoUri = null
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            cropLauncher.launch(
                CropImageContractOptions(
                    it,
                    CropImageOptions().apply {
                        fixAspectRatio = true
                        aspectRatioX = 1
                        aspectRatioY = 1
                    }
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(selectedColor ?: MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            ImageBox(
                selectedImage = selectedImage,
                context = context,
                onColorPicked = { color ->
                    if (selectedImage != null && color.alpha != 0.0f) {
                        selectedColor = color
                    }
                },
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            MSIconButton(Icons.Default.CameraAlt, "카메라") {
                photoUri = createImageUri(context, "my_size_photo")
                photoUri?.let {
                    cameraLauncher.launch(it)
                }
            }

            Spacer(Modifier.width(8.dp))

            MSIconButton(Icons.Default.Image, "갤러리") {
                galleryLauncher.launch("image/*")
            }
        }

        if (selectedColor != null && selectedImage != null) {
            Button(
                onClick = {
                    if (selectedImage != null && selectedColor != null) {
                        coroutineScope.launch {
                            val imageBytes = (context.getBitmapFromUri(selectedImage!!)).toBytes()
                            clothViewModel.insert(
                                Cloth(
                                    imageBytes = imageBytes,
                                    dominantColor = selectedColor!!.toArgb(),
                                    hash = generateMD5Hash(imageBytes),
                                    linkedSizeId = 1,
                                    sizeCategory = "상의",
                                    date = LocalDate.now(),
                                    memo = "",
                                    isPublic = true,
                                )
                            )
                            snackbarHostState.showSnackbar("옷이 저장되었습니다!")
                        }
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("이미지와 색상을 먼저 선택해주세요.")
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 24.dp)
            ) {
                Text("저장")
            }
        }

    }
}

fun createImageUri(context: Context, displayName: String): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
    )
}
