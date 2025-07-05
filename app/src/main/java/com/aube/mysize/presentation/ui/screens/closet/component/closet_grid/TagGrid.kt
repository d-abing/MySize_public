package com.aube.mysize.presentation.ui.screens.closet.component.closet_grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.presentation.model.clothes.TagGridMode
import com.aube.mysize.presentation.ui.screens.closet.component.MyTagPictureGrid
import com.aube.mysize.presentation.ui.screens.closet.component.OtherTagPictureGrid

@Composable
fun TagGrid(
    clothesList: List<Clothes>,
    onClick: (Clothes) -> Unit,
    mode: TagGridMode = TagGridMode.LOCAL,
    setEmptyList: (() -> Unit)? = null,
    onSearch: ((String) -> Unit)? = null,
    onLoadMore: ((String) -> Unit)? = null
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val trimmedQuery = searchQuery.trim()

    val keyboardController = LocalSoftwareKeyboardController.current

    if (mode == TagGridMode.REMOTE && searchQuery.isBlank()) {
        LaunchedEffect(Unit) {
            setEmptyList?.invoke()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(stringResource(R.string.placeholder_tag_search), style = MaterialTheme.typography.labelLarge) },
            textStyle = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.action_search)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    if (mode == TagGridMode.REMOTE && searchQuery.isNotBlank()) {
                        onSearch?.invoke(trimmedQuery)
                    }
                }
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        )

        if (mode == TagGridMode.REMOTE) {
            when {
                searchQuery.isBlank() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.text_tag_search),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                clothesList.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.text_tag_search_no_result),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    OtherTagPictureGrid(
                        clothesList = clothesList,
                        onClick = onClick,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        onLoadMore = { onLoadMore?.invoke(trimmedQuery) }
                    )
                }
            }
        } else {
            MyTagPictureGrid(
                clothesList = clothesList,
                searchQuery = searchQuery,
                onClick = onClick
            )
        }
    }
}