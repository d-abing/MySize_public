package com.aube.mysize.presentation.ui.screens.add_size

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.ui.component.chip_tap.CategoryChip
import com.aube.mysize.presentation.ui.screens.add_size.component.SaveButton
import com.aube.mysize.presentation.ui.screens.add_size.input_form.AccessorySizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.BodySizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.BottomSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.OnePieceSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.OuterSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.ShoeSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.TopSizeInputForm
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel

@Composable
fun AddSizeScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState,
    bodyViewModel: BodySizeViewModel = hiltViewModel(),
    topViewModel: TopSizeViewModel = hiltViewModel(),
    bottomViewModel: BottomSizeViewModel = hiltViewModel(),
    outerViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessoryViewModel: AccessorySizeViewModel = hiltViewModel(),
    onNavigateToMySizeScreen: () -> Unit
) {
    var isMandatoryFieldsFilled by remember { mutableStateOf(false) }
    var isAllFieldsValid by remember { mutableStateOf(false) }
    var saveRequest: (() -> Unit)? = null

    val listState = rememberLazyListState()

    // *Route*
    // 모든 사이즈 수정 시, 옷 사이즈 추가 시 (="해당 카테고리")
    // 신체 사이즈 추가 시 (="ADDBODY")
    // else ("BODY")
    val category = backStackEntry.arguments?.getString("category") ?: "BODY"
    val categories =
        when (category) {
            "ADDBODY" -> SizeCategory.entries.filter { it == SizeCategory.BODY }
            "BODY" -> SizeCategory.entries.toList()
            else -> SizeCategory.entries.filter { it != SizeCategory.BODY }
        }
    var selectedCategory by remember { mutableStateOf(SizeCategory.BODY) }

    // *Route*
    // 모든 사이즈 수정 시 (!=-1)
    // else (=-1)
    val id = backStackEntry.arguments?.getInt("id") ?: -1
    var oldSizeId by remember { mutableStateOf<Int?>(null) }

    BackHandler {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("new_size_id", -1)
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        if (category != "ADDBODY") {
            selectedCategory = SizeCategory.valueOf(category)
        }
        if (id != -1) {
            oldSizeId = id
        }
    }

    val isAtBottom by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem != null && lastVisibleItem.index == totalItems - 1
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            state = listState,
        ) {
            // ───── 선택 chip ─────
            item {
                CategoryChip(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onClick = { category -> selectedCategory = category }
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                // ───── 카테고리별 입력 UI 분기 ─────
                when (selectedCategory) {
                    SizeCategory.BODY -> BodySizeInputForm(
                        oldSizeId = oldSizeId,
                        viewModel = bodyViewModel,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { bodySize ->
                            saveRequest = {
                                if (id != -1) { // my size -> add size && recommend -> add size
                                    bodyViewModel.insert(bodySize.copy(id = id))
                                    navController.popBackStack()
                                } else {
                                    bodyViewModel.insert(bodySize)
                                    if (category != "BODY") navController.popBackStack() // closet -> add size
                                    else onNavigateToMySizeScreen() // add size
                                }
                            }
                        }
                    )
                    SizeCategory.TOP -> TopSizeInputForm(
                        oldSizeId = id,
                        viewModel = topViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { topSize ->
                            saveRequest = {
                                if (id != -1) {
                                    topViewModel.insert(topSize.copy(id = id)) {}
                                    navController.popBackStack()
                                } else {
                                    topViewModel.insert(topSize) { newId ->
                                        if (category != "BODY") {
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_id", newId)
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_category", selectedCategory)
                                            navController.popBackStack()
                                        } else {
                                            onNavigateToMySizeScreen()
                                        }
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.BOTTOM -> BottomSizeInputForm(
                        oldSizeId = id,
                        viewModel = bottomViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { bottomSize ->
                            saveRequest = {
                                if (id != -1) {
                                    bottomViewModel.insert(bottomSize.copy(id = id)) {}
                                    navController.popBackStack()
                                } else {
                                    bottomViewModel.insert(bottomSize) { newId ->
                                        if (category != "BODY") {
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_id", newId)
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_category", selectedCategory)
                                            navController.popBackStack()
                                        } else {
                                            onNavigateToMySizeScreen()
                                        }
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.OUTER -> OuterSizeInputForm(
                        oldSizeId = id,
                        viewModel = outerViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { outerSize ->
                            saveRequest = {
                                if (id != -1) {
                                    outerViewModel.insert(outerSize.copy(id = id)) {}
                                    navController.popBackStack()
                                } else {
                                    outerViewModel.insert(outerSize) { newId ->
                                        if (category != "BODY") {
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_id", newId)
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_category", selectedCategory)
                                            navController.popBackStack()
                                        } else {
                                            onNavigateToMySizeScreen()
                                        }
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.ONE_PIECE -> OnePieceSizeInputForm(
                        oldSizeId = id,
                        viewModel = onePieceViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { onePieceSize ->
                            saveRequest = {
                                if (id != -1) {
                                    onePieceViewModel.insert(onePieceSize.copy(id = id)) {}
                                    navController.popBackStack()
                                } else {
                                    onePieceViewModel.insert(onePieceSize) { newId ->
                                        if (category != "BODY") {
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_id", newId)
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_category", selectedCategory)
                                            navController.popBackStack()
                                        } else {
                                            onNavigateToMySizeScreen()
                                        }
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.SHOE -> ShoeSizeInputForm(
                        oldSizeId = id,
                        viewModel = shoeViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { shoeSize ->
                            saveRequest = {
                                if (id != -1) {
                                    shoeViewModel.insert(shoeSize.copy(id = id)) {}
                                    navController.popBackStack()
                                } else {
                                    shoeViewModel.insert(shoeSize) { newId ->
                                        if (category != "BODY") {
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_id", newId)
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_category", selectedCategory)
                                            navController.popBackStack()
                                        } else {
                                            onNavigateToMySizeScreen()
                                        }
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.ACCESSORY -> AccessorySizeInputForm(
                        oldSizeId = id,
                        viewModel = accessoryViewModel,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { accessorySize ->
                            saveRequest = {
                                if (id != -1) {
                                    accessoryViewModel.insert(accessorySize.copy(id = id)) {}
                                    navController.popBackStack()
                                } else {
                                    accessoryViewModel.insert(accessorySize) { newId ->
                                        if (category != "BODY") {
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_id", newId)
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_size_category", selectedCategory)
                                            navController.popBackStack()
                                        } else {
                                            onNavigateToMySizeScreen()
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        val animatedPadding by animateDpAsState(
            targetValue = if (isAtBottom) 12.dp else 20.dp,
            label = "ButtonHorizontalPadding"
        )

        SaveButton(
            enabled = isAllFieldsValid,
            icon = if (id != -1) Icons.Filled.ModeEdit else Icons.Filled.Add,
            text = if (isAtBottom) null else when {
                !isMandatoryFieldsFilled -> "필수 입력 미완료"
                !isAllFieldsValid -> "입력값 확인"
                (id != -1) -> "수정"
                else -> "추가"
            },
            contentPadding = PaddingValues(horizontal = animatedPadding, vertical = 14.dp),
            onClick = {
                saveRequest?.invoke()
            }
        )
    }
}