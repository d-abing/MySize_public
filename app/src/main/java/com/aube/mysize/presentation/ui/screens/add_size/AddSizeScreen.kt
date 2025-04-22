package com.aube.mysize.presentation.ui.screens.add_size

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.ui.component.CategoryChip
import com.aube.mysize.presentation.ui.component.addsize.SaveButton
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

    BackHandler {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("new_size_id", -1)
        navController.popBackStack()
    }
    var selectedCategory by remember { mutableStateOf(SizeCategory.BODY)}
    val category = backStackEntry.arguments?.getString("category") ?: "BODY"
    if (category != "ADDBODY") {
        selectedCategory = SizeCategory.valueOf(category)
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
                    categories = SizeCategory.entries,
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
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { bodySize ->
                            saveRequest = {
                                bodyViewModel.insert(bodySize)

                                if (category != "BODY") navController.popBackStack()
                                else onNavigateToMySizeScreen()
                            }
                        }
                    )
                    SizeCategory.TOP -> TopSizeInputForm(
                        viewModel = topViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { topSize ->
                            saveRequest = {
                                topViewModel.insert(topSize) { newId ->
                                    if (category != "BODY") {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("new_size_id", newId)
                                        navController.popBackStack()
                                    } else {
                                        onNavigateToMySizeScreen()
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.BOTTOM -> BottomSizeInputForm(
                        viewModel = bottomViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { bottomSize ->
                            saveRequest = {
                                bottomViewModel.insert(bottomSize) { newId ->
                                    if (category != "BODY") {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("new_size_id", newId)
                                        navController.popBackStack()
                                    } else {
                                        onNavigateToMySizeScreen()
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.OUTER -> OuterSizeInputForm(
                        viewModel = outerViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { outerSize ->
                            saveRequest = {
                                outerViewModel.insert(outerSize) { newId ->
                                    if (category != "BODY") {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("new_size_id", newId)
                                        navController.popBackStack()
                                    } else {
                                        onNavigateToMySizeScreen()
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.ONE_PIECE -> OnePieceSizeInputForm(
                        viewModel = onePieceViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { onePieceSize ->
                            saveRequest = {
                                onePieceViewModel.insert(onePieceSize) { newId ->
                                    if (category != "BODY") {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("new_size_id", newId)
                                        navController.popBackStack()
                                    } else {
                                        onNavigateToMySizeScreen()
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.SHOE -> ShoeSizeInputForm(
                        viewModel = shoeViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { shoeSize ->
                            saveRequest = {
                                shoeViewModel.insert(shoeSize) { newId ->
                                    if (category != "BODY") {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("new_size_id", newId)
                                        navController.popBackStack()
                                    } else {
                                        onNavigateToMySizeScreen()
                                    }
                                }
                            }
                        }
                    )

                    SizeCategory.ACCESSORY -> AccessorySizeInputForm(
                        viewModel = accessoryViewModel,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { accessorySize ->
                            saveRequest = {
                                accessoryViewModel.insert(accessorySize) { newId ->
                                    if (category != "BODY") {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("new_size_id", newId)
                                        navController.popBackStack()
                                    } else {
                                        onNavigateToMySizeScreen()
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
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .wrapContentWidth()
                .defaultMinSize(minWidth = 56.dp),
            enabled = isAllFieldsValid,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            ),
            icon = Icons.Filled.Add,
            text = if (isAtBottom) null else when {
                !isMandatoryFieldsFilled -> "필수 입력 미완료"
                !isAllFieldsValid -> "입력값 확인"
                else -> "추가"
            },
            contentPadding = PaddingValues(horizontal = animatedPadding, vertical = 14.dp),
            onClick = {
                saveRequest?.invoke()
            }
        )
    }
}