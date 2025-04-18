package com.aube.mysize.presentation.ui.screens.add_size

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
import com.aube.mysize.presentation.ui.component.CategoryChip
import com.aube.mysize.presentation.ui.component.addsize.SaveButton
import com.aube.mysize.presentation.ui.nav.SizeCategory
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
    snackbarHostState: SnackbarHostState,
    bodyViewModel: BodySizeViewModel = hiltViewModel(),
    topViewModel: TopSizeViewModel = hiltViewModel(),
    bottomViewModel: BottomSizeViewModel = hiltViewModel(),
    outerViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessoryViewModel: AccessorySizeViewModel = hiltViewModel(),
    onSaved: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf(SizeCategory.BODY) }

    var isMandatoryFieldsFilled by remember { mutableStateOf(false) }
    var isAllFieldsValid by remember { mutableStateOf(false) }
    var saveRequest: (() -> Unit)? = null

    val listState = rememberLazyListState()

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
                .padding(top = 12.dp),
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
                Spacer(modifier = Modifier.height(12.dp))
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
                                onSaved()
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
                                topViewModel.insert(topSize)
                                onSaved()
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
                                bottomViewModel.insert(bottomSize)
                                onSaved()
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
                                outerViewModel.insert(outerSize)
                                onSaved()
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
                                onePieceViewModel.insert(onePieceSize)
                                onSaved()
                            }
                        }
                    )

                    SizeCategory.SHOES -> ShoeSizeInputForm(
                        viewModel = shoeViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { shoeSize ->
                            saveRequest = {
                                shoeViewModel.insert(shoeSize)
                                onSaved()
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
                                accessoryViewModel.insert(accessorySize)
                                onSaved()
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