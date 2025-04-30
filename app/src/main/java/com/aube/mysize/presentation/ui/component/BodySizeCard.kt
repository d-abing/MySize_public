package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun BodySizeCard(
    containerColor: Color = MaterialTheme.colorScheme.background,
    title: String? = null,
    imageVector: ImageVector? = null,
    sharedBodyFields: Set<String> = emptySet(),
    description: Map<String, String?>? = null,
    selectableKeys: Set<String>? = null,
    selectedKeys: Set<String>? = null,
    onSelectionChanged: ((Set<String>) -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
) {
    val isSelectable = selectableKeys != null && selectedKeys != null && onSelectionChanged != null

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
                .fillMaxSize()
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
                        contentDescription = "Body Size Icon",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (title != null) {
                    Text(
                        style = MaterialTheme.typography.titleSmall,
                        text = title,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (onEdit != null && onDelete != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        modifier = Modifier.size(16.dp)
                            .clickable { onEdit() },
                        tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                        imageVector = Icons.Default.ModeEdit,
                        contentDescription = "edit"
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        modifier = Modifier.size(16.dp)
                            .clickable { onDelete() },
                        tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete"
                    )
                }
            }

            val group1Keys = listOf("키", "몸무게", "성별")
            val group2Keys = listOf("가슴둘레", "허리둘레", "엉덩이둘레")
            val group3Keys = listOf("목둘레", "어깨너비", "팔 길이", "다리 안쪽 길이")
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
                                                if (isSelectable) Modifier.clickable {
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
                                            if (isSelectable) Modifier.clickable {
                                                val updated =
                                                    if (isSelected) selectedKeys!! - key else selectedKeys!! + key
                                                onSelectionChanged?.invoke(updated)
                                            } else Modifier
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = key,
                                        style = MaterialTheme.typography.labelLarge.copy(
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
