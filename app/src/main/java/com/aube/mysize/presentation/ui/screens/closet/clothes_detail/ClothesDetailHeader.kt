package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aube.mysize.R
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun ClothesDetailHeader(
    nickname: String,
    dateText: String,
    imageUrl: String?,
    isOwner: Boolean,
    isConnected: Boolean,
    onReport: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onProfileClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { if(!isOwner) onProfileClick() }
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = stringResource(R.string.label_profile_image),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = nickname, style = MaterialTheme.typography.labelMedium)
                Text(text = dateText, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            }
        }

        if (isConnected) {
            Box {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.label_more),
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    if (isOwner) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    expanded = false
                                    onEdit()
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ModeEdit,
                                contentDescription = stringResource(R.string.action_edit),
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(12.dp)
                            )
                            Text(
                                text = stringResource(R.string.action_edit),
                                style = MaterialTheme.typography.labelMedium.copy(color = Color.DarkGray),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    expanded = false
                                    onDelete()
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.action_delete),
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(12.dp)
                            )
                            Text(
                                text = stringResource(R.string.action_delete),
                                style = MaterialTheme.typography.labelMedium.copy(color = Color.DarkGray),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    expanded = false
                                    onReport()
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.siren),
                                contentDescription = stringResource(R.string.report_action),
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(12.dp)
                            )
                            Text(
                                text = stringResource(R.string.report_action),
                                style = MaterialTheme.typography.labelMedium.copy(color = Color.DarkGray),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClothesDetailHeaderOwnerPreview() {
    MySizeTheme {
        ClothesDetailHeader(
            nickname = "윤세아",
            dateText = "2025.06.13 14:42 (수정됨)",
            imageUrl = "https://via.placeholder.com/150",
            isOwner = true,
            isConnected = true,
            onReport = {},
            onDelete = {},
            onEdit = {},
            onProfileClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClothesDetailHeaderOtherUserPreview() {
    MySizeTheme {
        ClothesDetailHeader(
            nickname = "도윤",
            dateText = "2025.06.12 22:10",
            imageUrl = "https://via.placeholder.com/150",
            isOwner = false,
            isConnected = true,
            onReport = {},
            onDelete = {},
            onEdit = {},
            onProfileClick = {}
        )
    }
}

