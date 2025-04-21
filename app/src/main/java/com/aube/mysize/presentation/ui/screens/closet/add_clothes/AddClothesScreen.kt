package com.aube.mysize.presentation.ui.screens.closet.add_clothes

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.presentation.ui.component.closet.ImageBox
import com.aube.mysize.presentation.ui.nav.SizeCategory
import com.aube.mysize.presentation.viewmodel.clothes.ClothesViewModel
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel
import com.aube.mysize.utils.generateMD5Hash
import com.aube.mysize.utils.getBitmapFromUri
import com.aube.mysize.utils.toBytes
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import java.time.LocalDateTime

@Composable
fun AddClothesScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    clothesViewModel: ClothesViewModel = hiltViewModel(),
    bodyViewModel: BodySizeViewModel = hiltViewModel(),
    topViewModel: TopSizeViewModel = hiltViewModel(),
    bottomViewModel: BottomSizeViewModel = hiltViewModel(),
    outerViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessoryViewModel: AccessorySizeViewModel = hiltViewModel(),
    onAddNewSize: (SizeCategory) -> Unit
) {
    val bodySizes by bodyViewModel.sizes.collectAsState()
    val topSizes by topViewModel.sizes.collectAsState()
    val bottomSizes by bottomViewModel.sizes.collectAsState()
    val outerSizes by outerViewModel.sizes.collectAsState()
    val onePieceSizes by onePieceViewModel.sizes.collectAsState()
    val shoeSizes by shoeViewModel.sizes.collectAsState()
    val accessorySizes by accessoryViewModel.sizes.collectAsState()

    AddClothesScreen(
        navController = navController,
        snackbarHostState =  snackbarHostState,
        bodySize = bodySizes.maxByOrNull { it.id },
        topSizes = topSizes,
        bottomSizes = bottomSizes,
        outerSizes = outerSizes,
        onePieceSizes = onePieceSizes,
        shoeSizes = shoeSizes,
        accessorySizes = accessorySizes,
        onAddNewSize = onAddNewSize,
        onClothesSaved = { clothes -> clothesViewModel.insert(clothes) }
    )
}

@Composable
fun AddClothesScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    bodySize: BodySize?,
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    onePieceSizes: List<OnePieceSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onAddNewSize: (SizeCategory) -> Unit = {},
    onClothesSaved: (Clothes) -> Unit = {}
) {
    val context = LocalContext.current

    var selectedStep by rememberSaveable { mutableIntStateOf(1) }

    var isOpenInFullMode by rememberSaveable { mutableStateOf(false) }

    var selectedColorInt by rememberSaveable { mutableIntStateOf(Color.White.toArgb()) }
    val selectedColor = Color(selectedColorInt)

    var selectedImageString by rememberSaveable { mutableStateOf<String?>(null) }
    val selectedImage: Uri? = selectedImageString?.let { Uri.parse(it) }

    var memo by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf(setOf<String>()) }

    val selectedSizeIds = remember { mutableStateMapOf<String, Int>() }
    var selectedCategory by rememberSaveable { mutableStateOf(SizeCategory.TOP) }

    var sharedBodyFields by remember { mutableStateOf(setOf<String>()) }

    val cropLauncher = rememberLauncherForActivityResult(
        CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            result.uriContent?.let { croppedUri ->
                selectedImageString = croppedUri.toString()
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

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val newId = backStackEntry.savedStateHandle.get<Int>("new_size_id")
            if (newId != null) {
                if (newId != -1) {
                    selectedSizeIds[selectedCategory.name] = newId
                }
                backStackEntry.savedStateHandle.remove<Int>("new_size_id")
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(selectedColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (!isOpenInFullMode){
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
                    selectedStep = selectedStep,
                    onColorPicked = { color ->
                        if (selectedImage != null && color.alpha != 0.0f) {
                            selectedColorInt = color.toArgb()
                        }
                    },
                )

                if (selectedImage == null) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = "갤러리",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { cropLauncher.launch(cropRequest) }
                            .padding(150.dp)
                    )
                }
            }
        }

        when (selectedStep) {
            1 ->
                AddClothesStepOne(
                    selectedColor = selectedColor,
                    selectedImage = selectedImage,
                    memo = memo,
                    onMemoChange = { memo = it },
                    tags = tags,
                    onTagAdd = { newTag -> tags = tags + newTag },
                    onTagRemove = { removeTag ->
                        tags = tags - removeTag
                    },
                    onNext = { selectedStep = 2}
                )
            2 ->
                AddClothesStepTwo(
                    topSizes = topSizes,
                    bottomSizes = bottomSizes,
                    outerSizes = outerSizes,
                    onePieceSizes = onePieceSizes,
                    shoeSizes = shoeSizes,
                    accessorySizes = accessorySizes,
                    selectedCategory = selectedCategory,
                    selectedIds = selectedSizeIds,
                    onSelectionChanged = {
                        selectedSizeIds.clear()
                        selectedSizeIds.putAll(it)
                    },
                    isOpenInFullMode = isOpenInFullMode,
                    onOpenInFullClick = { isOpenInFullMode = !isOpenInFullMode },
                    onAddNewSize = { category ->
                        selectedCategory = category
                        onAddNewSize(category)
                    },
                    onPrevious = { selectedStep = 1 },
                    onNext = { selectedStep = 3 }
                )
            3 -> AddClothesStepThree(
                bodySize = bodySize,
                selectedKeys = sharedBodyFields,
                onSelectionChanged = { sharedBodyFields = it.toSet() },
                onPrevious = { selectedStep = 2 },
                onComplete = {
                    val imageBytes = (context.getBitmapFromUri(selectedImage!!)).toBytes()

                    onClothesSaved(
                        Clothes(
                            imageBytes = imageBytes,
                            hash = generateMD5Hash(imageBytes),
                            dominantColor = selectedColor.toArgb(),
                            linkedSizeIds = selectedSizeIds,
                            tags = tags,
                            memo = memo,
                            sharedBodyFields = sharedBodyFields,
                            createdAt = LocalDateTime.now(),
                            updatedAt = null,
                            createUserId = 1,
                            createUserProfileFilePath = ""
                        )
                    )
                    navController.popBackStack()
                }
            )
        }

    }
}
