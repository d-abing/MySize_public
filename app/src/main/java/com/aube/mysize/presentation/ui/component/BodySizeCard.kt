package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.component.button.MSActionIconButton
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun BodySizeCard(
    isClickable: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.background,
    imageVector: ImageVector? = null,
    title: String? = null,
    sharedBodyFields: Set<String> = emptySet(),
    description: Map<String, String?>? = null,
    selectableKeys: Set<String>? = null,
    selectedKeys: Set<String>? = null,
    onSelectionChanged: ((Set<String>) -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
) {
    val isKeySelectable = selectableKeys != null && selectedKeys != null && onSelectionChanged != null

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (imageVector != null) {
                    Image(
                        imageVector = imageVector,
                        contentDescription = stringResource(R.string.body_size_title),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(16.dp)
                    )
                }

                if (title != null) {
                    Text(
                        style = MaterialTheme.typography.titleSmall,
                        text = title,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f)
                    )
                }

                if (onEdit != null && onDelete != null) {
                    MSActionIconButton(
                        imageVector = Icons.Default.ModeEdit,
                        contentDescription = stringResource(R.string.action_edit),
                        onClick = onEdit
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    MSActionIconButton(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.action_delete),
                        onClick = onDelete
                    )
                }
            }

            val group1Keys = listOf(
                stringResource(R.string.body_height),
                stringResource(R.string.body_weight),
                stringResource(R.string.body_foot_length),
                stringResource(R.string.body_foot_width)
            )

            val group2Keys = listOf(
                stringResource(R.string.body_gender),
                stringResource(R.string.body_chest),
                stringResource(R.string.body_waist),
                stringResource(R.string.body_hip)
            )

            val group3Keys = listOf(
                stringResource(R.string.body_neck),
                stringResource(R.string.body_shoulder),
                stringResource(R.string.body_arm_length),
                stringResource(R.string.body_leg_length)
            )

            val groups = listOf(group1Keys, group2Keys, group3Keys)

            if (description != null) {
                val validGroups = groups.filter { groupKeys ->
                    groupKeys.any { key -> description[key] != null }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    validGroups.forEachIndexed { index, groupKeys ->
                        val groupItems = groupKeys.mapNotNull { key ->
                            if (sharedBodyFields.isEmpty() || sharedBodyFields.contains(key)) {
                                description[key]?.let { value -> key to value }
                            } else {
                                null
                            }
                        }

                        if (groupItems.isNotEmpty()) {
                            Column(
                                modifier = Modifier.weight(if (index == 0) 0.8f else 1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                groupItems.forEach { (key, value) ->
                                    val isSelected = selectedKeys?.contains(key) ?: true

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 2.dp)
                                            .then(
                                                if (isKeySelectable) Modifier.clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = null
                                                ) {
                                                    val updated =
                                                        if (isSelected) selectedKeys!! - key else selectedKeys!! + key
                                                    onSelectionChanged?.invoke(updated)
                                                } else Modifier
                                            ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "$key: $value",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                textDecoration = if (!isSelected) TextDecoration.LineThrough else null,
                                                color = if (!isSelected) Color.Gray else MaterialTheme.colorScheme.onSurface
                                            ),
                                            modifier = Modifier.weight(0.8f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    groups.forEachIndexed { index, groupKeys ->
                        Column(
                            modifier = Modifier.weight(if (index == 0) 0.8f else 1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            groupKeys.forEach { key ->
                                val isSelected = selectedKeys?.contains(key) ?: true

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp)
                                        .then(
                                            if (isKeySelectable) Modifier.clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null
                                            ) {
                                                selectedKeys?.let {
                                                    val updated =
                                                        if (isSelected) it - key else it + key
                                                    onSelectionChanged?.invoke(updated)
                                                }
                                            } else Modifier
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = key,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            textDecoration = if (!isSelected) TextDecoration.LineThrough else null,
                                            color = if (!isSelected) Color.Gray else MaterialTheme.colorScheme.onSurface
                                        ),
                                        modifier = Modifier.weight(0.8f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BodySizeCardPreview() {
    MySizeTheme {
        BodySizeCard(
            isClickable = true,
            containerColor = MaterialTheme.colorScheme.tertiary.copy(0.4f),
            imageVector = Icons.Default.Accessibility,
            title = "신체",
            sharedBodyFields = setOf(
                stringResource(R.string.body_height),
                stringResource(R.string.body_weight),
                stringResource(R.string.body_chest)
            ),
            description = mapOf(
                stringResource(R.string.body_height) to "170cm",
                stringResource(R.string.body_weight) to "60kg",
                stringResource(R.string.body_chest) to "90cm",
                stringResource(R.string.body_waist) to "76cm"
            ),
            selectableKeys = setOf(
                stringResource(R.string.body_height),
                stringResource(R.string.body_weight),
                stringResource(R.string.body_chest)
            ),
            selectedKeys = setOf(
                stringResource(R.string.body_height),
                stringResource(R.string.body_chest)
            ),
            onSelectionChanged = {},
            onEdit = {},
            onDelete = {}
        )
    }
}
