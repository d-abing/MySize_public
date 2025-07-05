package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.model.clothes.Visibility
import com.aube.mysize.presentation.model.clothes.toUi
import com.aube.mysize.presentation.model.size.toUi
import com.aube.mysize.presentation.ui.component.BodySizeCard
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ColumnScope.ClothesPrivacyIndicators(clothes: Clothes) {
    if (clothes.createUserId != Firebase.auth.currentUser?.uid) return

    var showBodyTooltip by remember { mutableStateOf(false) }
    var showVisibilityTooltip by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp)
            .align(Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (clothes.visibility == Visibility.PUBLIC) {
            Box {
                Icon(
                    imageVector = Icons.Default.Accessibility,
                    contentDescription = stringResource(R.string.text_body_info_public_title),
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(12.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { showBodyTooltip = true }
                )

                DropdownMenu(
                    expanded = showBodyTooltip,
                    onDismissRequest = { showBodyTooltip = false },
                    offset = DpOffset(x = 0.dp, y = 8.dp),
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentHeight()
                ) {
                    val bodyUi = clothes.bodySize?.toUi()
                    BodySizeCard(
                        containerColor = MaterialTheme.colorScheme.background,
                        title = stringResource(R.string.text_body_info_public_title),
                        imageVector = Icons.Default.Person,
                        sharedBodyFields = clothes.sharedBodyFields.map { it.displayName }.toSet(),
                        description = bodyUi?.description,
                        selectedKeys = clothes.sharedBodyFields.map { it.displayName }.toSet(),
                    )
                }
            }
        }

        Box {
            Icon(
                imageVector = clothes.visibility.toUi().icon,
                contentDescription = clothes.visibility.toUi().label,
                tint = Color.Gray,
                modifier = Modifier
                    .size(12.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { showVisibilityTooltip = true }
            )

            DropdownMenu(
                expanded = showVisibilityTooltip,
                onDismissRequest = { showVisibilityTooltip = false },
                offset = DpOffset(x = 0.dp, y = 8.dp),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .wrapContentHeight()
            ) {
                Text(
                    text = clothes.visibility.toUi().label,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}