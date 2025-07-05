package com.aube.mysize.presentation.ui.screens.closet.add_clothes

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aube.mysize.domain.model.clothes.LinkedSizeGroup
import com.aube.mysize.domain.model.clothes.MemoVisibility
import com.aube.mysize.domain.model.clothes.Visibility
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.model.clothes.ClothesSaveModel
import com.aube.mysize.presentation.ui.screens.closet.component.ImageBox
import com.aube.mysize.presentation.viewmodel.clothes.MyClothesViewModel
import com.aube.mysize.presentation.viewmodel.settings.SettingsViewModel
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel
import com.aube.mysize.utils.getBitmapFromUri
import com.aube.mysize.utils.save.setSaver
import com.aube.mysize.utils.save.sizeMapSaver
import com.aube.mysize.utils.toBytes
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun AddClothesScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    sizeViewModel: SizeViewModel = hiltViewModel(),
    myClothesViewModel: MyClothesViewModel,
    navController: NavController,
    onAddNewBodySize: () -> Unit = {},
    onAddNewSize: (SizeCategory) -> Unit = {},
) {
    val context = LocalContext.current
    val allSizes by sizeViewModel.sizes.collectAsState()

    var selectedStep by rememberSaveable { mutableIntStateOf(1) }
    var isOpenInFullMode by rememberSaveable { mutableStateOf(false) }

    var selectedColorInt by rememberSaveable { mutableStateOf<Int?>(null) }
    val selectedColor = selectedColorInt?.let { Color(it) }
    var selectedImageString by rememberSaveable { mutableStateOf<String?>(null) }
    val selectedImage: Uri? = selectedImageString?.let { Uri.parse(it) }

    var memo by rememberSaveable { mutableStateOf("") }
    var tags by rememberSaveable(stateSaver = setSaver) { mutableStateOf(setOf()) }

    val selectedSizeIds = remember { mutableStateMapOf<SizeCategory, List<String>>() }
    val savedSelectedSizeIds = rememberSaveable(saver = sizeMapSaver) { mutableStateMapOf() }

    var selectedCategory by rememberSaveable { mutableStateOf(SizeCategory.TOP) }
    val sharedBodyFields by settingsViewModel.bodyFields.collectAsState()
    var selectedVisibility by rememberSaveable { mutableStateOf(Visibility.PRIVATE) }
    var selectedMemoVisibility by rememberSaveable { mutableStateOf(MemoVisibility.PRIVATE) }

    val coroutineScope = rememberCoroutineScope()
    var isUploading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (savedSelectedSizeIds.isNotEmpty()) {
            selectedSizeIds.clear()
            selectedSizeIds.putAll(savedSelectedSizeIds)
        }

        navController.currentBackStackEntryFlow.collect { entry ->
            val newId = entry.savedStateHandle.get<String>("new_size_id")
            val newCategory = entry.savedStateHandle.get<SizeCategory>("new_size_category")
            if (!newId.isNullOrBlank() && newCategory != null) {
                val existing = selectedSizeIds[newCategory].orEmpty()
                selectedSizeIds[newCategory] = existing + newId
            }
            entry.savedStateHandle.remove<String>("new_size_id")
            entry.savedStateHandle.remove<SizeCategory>("new_size_category")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isOpenInFullMode) {
            ImageBox(
                selectedImage = selectedImage,
                selectedStep = selectedStep,
                onColorPicked = { selectedColorInt = it.takeIf { c -> c.alpha != 0f }?.toArgb() },
                onDelete = {
                    selectedColorInt = null
                    selectedImageString = null
                },
                onPickImage = { selectedImageString = it }
            )
        }

        val bodySize = allSizes[SizeCategory.BODY]
            ?.filterIsInstance<BodySize>()
            ?.maxByOrNull { it.id }

        when (selectedStep) {
            1 -> AddClothesStepOne(
                selectedColor = selectedColor,
                selectedImage = selectedImage,
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
                onSelectionChanged = { settingsViewModel.saveBodyFields(it.toSet()) },
                onAddNewBodySize = onAddNewBodySize,
                selectedVisibility = selectedVisibility,
                onVisibilityChanged = { selectedVisibility = it },
                selectedMemoVisibility = selectedMemoVisibility,
                onMemoVisibilityChanged = { selectedMemoVisibility = it },
                onPrevious = {
                    selectedStep = 2
                    isOpenInFullMode = true
                },
                isUploading = isUploading,
                onComplete = {
                    selectedImage?.let {
                        val bytes = context.getBitmapFromUri(it).toBytes()
                        Firebase.auth.currentUser?.let { user ->
                            coroutineScope.launch {
                                isUploading = true
                                myClothesViewModel.insert(
                                    ClothesSaveModel(
                                        imageBytes = bytes,
                                        dominantColor = selectedColorInt!!,
                                        linkedSizes = selectedSizeIds.map { (category, ids) ->
                                            LinkedSizeGroup(
                                                category.name,
                                                ids
                                            )
                                        },
                                        bodySize = bodySize,
                                        sharedBodyFields = sharedBodyFields,
                                        tags = tags,
                                        memo = memo,
                                        createdAt = LocalDateTime.now(),
                                        updatedAt = null,
                                        createUserId = user.uid,
                                        createUserProfileImageUrl = user.photoUrl.toString(),
                                        visibility = selectedVisibility,
                                        memoVisibility = selectedMemoVisibility
                                    )
                                )
                                isUploading = false
                                navController.popBackStack()
                                myClothesViewModel.loadMyClothesList()
                            }
                        }
                    }
                }
            )
        }
    }
}
