package com.aube.mysize.presentation.ui.screens.closet.edit_clothes

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
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
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.ui.screens.closet.add_clothes.AddClothesStepOne
import com.aube.mysize.presentation.ui.screens.closet.add_clothes.AddClothesStepThree
import com.aube.mysize.presentation.ui.screens.closet.add_clothes.AddClothesStepTwo
import com.aube.mysize.presentation.ui.screens.closet.add_clothes.setSaver
import com.aube.mysize.presentation.ui.screens.closet.component.ImageBox
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
fun EditClothesScreen(
    clothesId: Int,
    navController: NavController,
    clothesViewModel: ClothesViewModel = hiltViewModel(),
    bodyViewModel: BodySizeViewModel = hiltViewModel(),
    topViewModel: TopSizeViewModel = hiltViewModel(),
    bottomViewModel: BottomSizeViewModel = hiltViewModel(),
    outerViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessoryViewModel: AccessorySizeViewModel = hiltViewModel(),
    onAddNewBodySize: () -> Unit,
    onAddNewSize: (SizeCategory) -> Unit
) {
    val bodySizes by bodyViewModel.sizes.collectAsState()
    val topSizes by topViewModel.sizes.collectAsState()
    val bottomSizes by bottomViewModel.sizes.collectAsState()
    val outerSizes by outerViewModel.sizes.collectAsState()
    val onePieceSizes by onePieceViewModel.sizes.collectAsState()
    val shoeSizes by shoeViewModel.sizes.collectAsState()
    val accessorySizes by accessoryViewModel.sizes.collectAsState()

    var clothes by remember { mutableStateOf<Clothes?>(null) }

    LaunchedEffect(clothesId) {
        clothes = clothesViewModel.getById(clothesId)
    }

    if (clothes == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        EditClothesScreen(
            clothes = clothes!!,
            navController = navController,
            bodySize = bodySizes.maxByOrNull { it.id },
            topSizes = topSizes,
            bottomSizes = bottomSizes,
            outerSizes = outerSizes,
            onePieceSizes = onePieceSizes,
            shoeSizes = shoeSizes,
            accessorySizes = accessorySizes,
            onAddNewBodySize = onAddNewBodySize,
            onAddNewSize = onAddNewSize,
            onClothesSaved = { newClothes -> clothesViewModel.insert(newClothes) }
        )
    }
}

@Composable
fun EditClothesScreen(
    clothes: Clothes,
    navController: NavController,
    bodySize: BodySize?,
    topSizes: List<TopSize>,
    bottomSizes: List<BottomSize>,
    outerSizes: List<OuterSize>,
    onePieceSizes: List<OnePieceSize>,
    shoeSizes: List<ShoeSize>,
    accessorySizes: List<AccessorySize>,
    onAddNewBodySize: () -> Unit = {},
    onAddNewSize: (SizeCategory) -> Unit = {},
    onClothesSaved: (Clothes) -> Unit = {}
) {

    val context = LocalContext.current

    var selectedStep by rememberSaveable { mutableIntStateOf(1) }
    var isOpenInFullMode by rememberSaveable { mutableStateOf(false) }

    var selectedColorInt by rememberSaveable { mutableStateOf<Int?>(clothes.dominantColor) }
    val selectedColor = selectedColorInt?.let { Color(it) }

    var oldImage: ByteArray? by rememberSaveable { mutableStateOf(clothes.imageBytes) }
    var selectedImageString by rememberSaveable { mutableStateOf<String?>(null) }
    val selectedImage: Uri? = selectedImageString?.let { Uri.parse(it) }

    var memo by rememberSaveable { mutableStateOf(clothes.memo ?: "") }
    var tags by rememberSaveable(stateSaver = setSaver) {
        mutableStateOf(clothes.tags)
    }
    val selectedSizeIds = remember { mutableStateMapOf(*clothes.linkedSizeIds.toList().toTypedArray()) }
    val savedSelectedSizeIds = rememberSaveable(saver = mapSaver()) {
        mutableStateMapOf()
    }

    var selectedCategory by rememberSaveable { mutableStateOf(SizeCategory.TOP) }

    var sharedBodyFields by remember { mutableStateOf(clothes.sharedBodyFields) }
    var selectedVisibility by rememberSaveable { mutableStateOf(clothes.visibility) }

    var selectedMemoVisibility by rememberSaveable { mutableStateOf(clothes.memoVisibility) }

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
        if (savedSelectedSizeIds.isNotEmpty()) {
            selectedSizeIds.clear()
            selectedSizeIds.putAll(savedSelectedSizeIds)
        }

        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val newId = backStackEntry.savedStateHandle.get<Int>("new_size_id")
            val newCategory = backStackEntry.savedStateHandle.get<SizeCategory>("new_size_category")

            if (newId != null) {
                if (newId != -1 && newCategory != null) {
                    selectedCategory = newCategory
                    selectedSizeIds[newCategory.name] = newId
                }
                backStackEntry.savedStateHandle.remove<Int>("new_size_id")
                backStackEntry.savedStateHandle.remove<SizeCategory>("new_size_category")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    selectedImage = if(oldImage != null) oldImage else selectedImage,
                    context = context,
                    selectedStep = selectedStep,
                    onColorPicked = { color ->
                        if (selectedImage != null && color.alpha != 0.0f) {
                            selectedColorInt = color.toArgb()
                        }
                    },
                )

                if (selectedImage == null && oldImage == null) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = "갤러리",
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
                        contentDescription = "삭제",
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.TopStart)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                if (oldImage != null) {
                                    oldImage = null
                                } else {
                                    selectedColorInt = null
                                    selectedImageString = null
                                }
                            }
                            .padding(8.dp)
                    )
                }
            }
        }

        when (selectedStep) {
            1 ->
                AddClothesStepOne(
                    selectedColor = selectedColor,
                    selectedImage = if(oldImage != null) oldImage else selectedImage,
                    memo = memo,
                    onMemoChange = { memo = it },
                    tags = tags,
                    onTagAdd = { newTag -> tags = tags + newTag },
                    onTagRemove = { removeTag ->
                        tags = tags - removeTag
                    },
                    onNext = {
                        selectedStep = 2
                        isOpenInFullMode = true
                    }
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
                    onSelectedIdsChanged = {
                        selectedSizeIds.clear()
                        selectedSizeIds.putAll(it)
                    },
                    onSelectedCategoryChanged = {
                        selectedCategory = it
                    },
                    isOpenInFullMode = isOpenInFullMode,
                    onOpenInFullClick = { isOpenInFullMode = !isOpenInFullMode },
                    onAddNewSize = { category ->
                        selectedCategory = category
                        savedSelectedSizeIds += selectedSizeIds
                        onAddNewSize(category)
                    },
                    onPrevious = {
                        selectedStep = 1
                        isOpenInFullMode = false
                    },
                    onNext = {
                        selectedStep = 3
                        isOpenInFullMode = true
                    }
                )
            3 ->
                AddClothesStepThree(
                    bodySize = bodySize,
                    selectedKeys = sharedBodyFields,
                    onSelectionChanged = { sharedBodyFields = it.toSet() },
                    onAddNewBodySize = { onAddNewBodySize() },
                    selectedVisibility = selectedVisibility,
                    onVisibilityChanged = { selectedVisibility = it },
                    selectedMemoVisibility = selectedMemoVisibility,
                    onMemoVisibilityChanged = { selectedMemoVisibility = it },
                    onPrevious = {
                        selectedStep = 2
                        isOpenInFullMode = true
                    },
                    onComplete = {
                        var imageBytes = oldImage
                        if (imageBytes == null && selectedImage != null) {
                            imageBytes = (context.getBitmapFromUri(selectedImage)).toBytes()

                            val randomUserId = listOf(1L, 2L).random()

                            onClothesSaved(
                                Clothes(
                                    id = clothes.id,
                                    imageBytes = imageBytes,
                                    hash = generateMD5Hash(imageBytes),
                                    dominantColor = selectedColor!!.toArgb(),
                                    linkedSizeIds = selectedSizeIds,
                                    tags = tags,
                                    memo = memo,
                                    sharedBodyFields = sharedBodyFields,
                                    bodySize = bodySize,
                                    createdAt = LocalDateTime.now(),
                                    updatedAt = null,
                                    createUserId = randomUserId,
                                    createUserProfileFilePath = "",
                                    visibility = selectedVisibility,
                                    memoVisibility = selectedMemoVisibility
                                )
                            )
                        }
                        navController.popBackStack()
                    }
                )
        }

    }
}


fun mapSaver() = Saver<MutableMap<String, Int>, Map<String, Int>>(
    save = { it.toMap() },
    restore = { it.toMutableMap() }
)