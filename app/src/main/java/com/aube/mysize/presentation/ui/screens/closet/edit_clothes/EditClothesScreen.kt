package com.aube.mysize.presentation.ui.screens.closet.edit_clothes

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aube.mysize.domain.model.clothes.LinkedSizeGroup
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.model.clothes.ClothesSaveModel
import com.aube.mysize.presentation.ui.screens.closet.add_clothes.AddClothesStepOne
import com.aube.mysize.presentation.ui.screens.closet.add_clothes.AddClothesStepThree
import com.aube.mysize.presentation.ui.screens.closet.add_clothes.AddClothesStepTwo
import com.aube.mysize.presentation.ui.screens.closet.component.ImageBox
import com.aube.mysize.presentation.viewmodel.clothes.ClothesDetailViewModel
import com.aube.mysize.presentation.viewmodel.clothes.MyClothesViewModel
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel
import com.aube.mysize.utils.getBitmapFromUri
import com.aube.mysize.utils.save.setSaver
import com.aube.mysize.utils.save.sizeMapSaver
import com.aube.mysize.utils.toBytes
import kotlinx.coroutines.launch

@Composable
fun EditClothesScreen(
    clothesDetailViewModel: ClothesDetailViewModel,
    myClothesViewModel: MyClothesViewModel,
    sizeViewModel: SizeViewModel,
    clothesId: String,
    navController: NavController,
    onAddNewBodySize: () -> Unit = {},
    onAddNewSize: (SizeCategory) -> Unit = {},
) {
    val allSizes by sizeViewModel.sizes.collectAsState()
    val clothesDetail by clothesDetailViewModel.clothesDetail.collectAsState()
    val clothes = clothesDetail?.clothes

    val coroutineScope = rememberCoroutineScope()
    var isUpdating by remember { mutableStateOf(false) }

    clothes?.let {
        val context = LocalContext.current
        var selectedStep by rememberSaveable { mutableIntStateOf(1) }
        var isOpenInFullMode by rememberSaveable { mutableStateOf(false) }
        var selectedColorInt by rememberSaveable { mutableStateOf<Int?>(clothes.dominantColor) }
        val selectedColor = selectedColorInt?.let { Color(it) }
        var oldImage: Uri? by rememberSaveable { mutableStateOf(Uri.parse(clothes.imageUrl)) }
        var selectedImageString by rememberSaveable { mutableStateOf<String?>(null) }
        val selectedImage = selectedImageString?.let { Uri.parse(it) }
        var memo by rememberSaveable { mutableStateOf(clothes.memo ?: "") }
        var tags by rememberSaveable(stateSaver = setSaver) { mutableStateOf(clothes.tags) }
        val selectedSizeIds = remember {
            mutableStateMapOf<SizeCategory, List<String>>().apply {
                clothes.linkedSizes.forEach { put(SizeCategory.valueOf(it.category), it.sizeIds) }
            }
        }
        val savedSelectedSizeIds =
            rememberSaveable(saver = sizeMapSaver) { mutableStateMapOf() }
        var selectedCategory by rememberSaveable { mutableStateOf(SizeCategory.TOP) }
        var sharedBodyFields by remember { mutableStateOf(clothes.sharedBodyFields.map { it.displayName }.toSet()) }
        var selectedVisibility by rememberSaveable { mutableStateOf(clothes.visibility) }
        var selectedMemoVisibility by rememberSaveable { mutableStateOf(clothes.memoVisibility) }

        LaunchedEffect(clothesId) {
            clothesDetailViewModel.getById(clothesId)
        }

        LaunchedEffect(Unit) {
            if (savedSelectedSizeIds.isNotEmpty()) {
                selectedSizeIds.clear()
                selectedSizeIds.putAll(savedSelectedSizeIds)
            }
            navController.currentBackStackEntryFlow.collect { backStackEntry ->
                val newId = backStackEntry.savedStateHandle.get<String>("new_size_id")
                val newCategory =
                    backStackEntry.savedStateHandle.get<SizeCategory>("new_size_category")
                if (!newId.isNullOrBlank() && newCategory != null) {
                    val existing = selectedSizeIds[newCategory].orEmpty()
                    selectedSizeIds[newCategory] = existing + newId
                }
                backStackEntry.savedStateHandle.remove<String>("new_size_id")
                backStackEntry.savedStateHandle.remove<SizeCategory>("new_size_category")
            }
        }

        val bodySize =
            allSizes[SizeCategory.BODY]?.filterIsInstance<BodySize>()?.maxByOrNull { it.id }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isOpenInFullMode) {
                ImageBox(
                    selectedImage = oldImage ?: selectedImage,
                    selectedStep = selectedStep,
                    onColorPicked = { if (it.alpha != 0f) selectedColorInt = it.toArgb() },
                    onDelete = {
                        if (oldImage != null) oldImage = null else selectedImageString = null
                        selectedColorInt = null
                    },
                    onPickImage = { selectedImageString = it }
                )
            }

            when (selectedStep) {
                1 -> AddClothesStepOne(
                    selectedColor = selectedColor,
                    selectedImage = oldImage ?: selectedImage,
                    memo = memo,
                    onMemoChange = { memo = it },
                    tags = tags,
                    onTagAdd = { tags += it },
                    onTagRemove = { tags -= it },
                    onNext = {
                        selectedStep = 2
                        isOpenInFullMode = true
                    }
                )

                2 -> AddClothesStepTwo(
                    allSizes = allSizes,
                    selectedCategory = selectedCategory,
                    selectedIds = selectedSizeIds,
                    onSelectedIdsChanged = {
                        selectedSizeIds.clear()
                        selectedSizeIds.putAll(it)
                    },
                    onSelectedCategoryChanged = { selectedCategory = it },
                    isOpenInFullMode = isOpenInFullMode,
                    onOpenInFullClick = { isOpenInFullMode = !isOpenInFullMode },
                    onAddNewSize = {
                        selectedCategory = it
                        savedSelectedSizeIds += selectedSizeIds
                        onAddNewSize(it)
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


                3 -> AddClothesStepThree(
                    bodySize = bodySize,
                    selectedKeys = sharedBodyFields,
                    onSelectionChanged = { sharedBodyFields = it.toSet() },
                    onAddNewBodySize = onAddNewBodySize,
                    selectedVisibility = selectedVisibility,
                    onVisibilityChanged = { selectedVisibility = it },
                    selectedMemoVisibility = selectedMemoVisibility,
                    onMemoVisibilityChanged = { selectedMemoVisibility = it },
                    onPrevious = {
                        selectedStep = 2
                        isOpenInFullMode = true
                    },
                    isUploading = isUpdating,
                    onComplete = {
                        val imageBytes =
                            selectedImage?.let { context.getBitmapFromUri(it).toBytes() }

                        coroutineScope.launch {
                            isUpdating = true
                            val newImageUrl = myClothesViewModel.insert(
                                ClothesSaveModel(
                                    id = clothes.id,
                                    imageBytes = imageBytes,
                                    originalImageUrl = if (oldImage != null) clothes.imageUrl else null,
                                    dominantColor = selectedColor!!.toArgb(),
                                    linkedSizes = selectedSizeIds.map { (category, ids) ->
                                        LinkedSizeGroup(category.name, ids)
                                    },
                                    bodySize = bodySize,
                                    sharedBodyFields = sharedBodyFields,
                                    tags = tags,
                                    memo = memo,
                                    createdAt = clothes.createdAt,
                                    updatedAt = null,
                                    createUserId = clothes.createUserId,
                                    createUserProfileImageUrl = clothes.createUserProfileImageUrl,
                                    visibility = selectedVisibility,
                                    memoVisibility = selectedMemoVisibility
                                )
                            )
                            isUpdating = false

                            newImageUrl?.let {
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("updated_image_url", it)
                            }

                            navController.popBackStack()
                            myClothesViewModel.loadMyClothesList()
                        }
                    }
                )
            }
        }
    }
}