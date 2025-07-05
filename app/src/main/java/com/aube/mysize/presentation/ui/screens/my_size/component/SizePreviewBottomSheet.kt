package com.aube.mysize.presentation.ui.screens.my_size.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.ClothesSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.presentation.ui.component.button.MSActionIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizePreviewBottomSheet(
    size: ClothesSize,
    onEdit: (ClothesSize) -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${size.type} / ${size.brand} / ${size.sizeLabel}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MSActionIconButton(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.action_edit),
                        onClick = { onEdit(size) }
                    )
                    MSActionIconButton(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.action_delete),
                        onClick = onDelete
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            when (size) {
                is TopSize -> {
                    Section(stringResource(R.string.text_dimension)) {
                        size.shoulder?.let { LabelValue(stringResource(R.string.shoulder_width), "${it}cm") }
                        size.chest?.let { LabelValue(stringResource(R.string.chest_width), "${it}cm") }
                        size.sleeve?.let { LabelValue(stringResource(R.string.sleeve_length), "${it}cm") }
                        size.length?.let { LabelValue(stringResource(R.string.total_length), "${it}cm") }
                    }
                    Section(stringResource(R.string.text_style)) {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section(stringResource(R.string.text_note)) {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is BottomSize -> {
                    Section(stringResource(R.string.text_dimension)) {
                        size.waist?.let { LabelValue(stringResource(R.string.waist_width), "${it}cm") }
                        size.rise?.let { LabelValue(stringResource(R.string.rise_length), "${it}cm") }
                        size.hip?.let { LabelValue(stringResource(R.string.hip_width), "${it}cm") }
                        size.thigh?.let { LabelValue(stringResource(R.string.thigh_width), "${it}cm") }
                        size.hem?.let { LabelValue(stringResource(R.string.hem_width), "${it}cm") }
                        size.length?.let { LabelValue(stringResource(R.string.total_length), "${it}cm") }
                    }
                    Section(stringResource(R.string.text_style)) {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section(stringResource(R.string.text_note)) {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is OuterSize -> {
                    Section(stringResource(R.string.text_dimension)) {
                        size.shoulder?.let { LabelValue(stringResource(R.string.shoulder_width), "${it}cm") }
                        size.chest?.let { LabelValue(stringResource(R.string.chest_width), "${it}cm") }
                        size.sleeve?.let { LabelValue(stringResource(R.string.sleeve_length), "${it}cm") }
                        size.length?.let { LabelValue(stringResource(R.string.total_length), "${it}cm") }
                    }
                    Section(stringResource(R.string.text_style)) {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section(stringResource(R.string.text_note)) {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is OnePieceSize -> {
                    Section(stringResource(R.string.text_dimension)) {
                        size.shoulder?.let { LabelValue(stringResource(R.string.shoulder_width), "${it}cm") }
                        size.chest?.let { LabelValue(stringResource(R.string.chest_width), "${it}cm") }
                        size.waist?.let { LabelValue(stringResource(R.string.waist_width), "${it}cm") }
                        size.hip?.let { LabelValue(stringResource(R.string.hip_width), "${it}cm") }
                        size.sleeve?.let { LabelValue(stringResource(R.string.sleeve_length), "${it}cm") }
                        size.rise?.let { LabelValue(stringResource(R.string.rise_length), "${it}cm") }
                        size.thigh?.let { LabelValue(stringResource(R.string.thigh_width), "${it}cm") }
                        size.hem?.let { LabelValue(stringResource(R.string.hem_width), "${it}cm") }
                        size.length?.let { LabelValue(stringResource(R.string.total_length), "${it}cm") }
                    }
                    Section(stringResource(R.string.text_style)) {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section(stringResource(R.string.text_note)) {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is ShoeSize -> {
                    Section(stringResource(R.string.text_dimension)) {
                        size.footLength?.let { LabelValue(stringResource(R.string.body_foot_length), "${it}cm") }
                        size.footWidth?.let { LabelValue(stringResource(R.string.body_foot_width), "${it}cm") }
                    }
                    Section(stringResource(R.string.text_style)) {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section(stringResource(R.string.text_note)) {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is AccessorySize -> {
                    Section(stringResource(R.string.text_dimension)) {
                        size.bodyPart?.let { LabelValue(stringResource(R.string.body_part), it) }
                    }
                    Section(stringResource(R.string.text_style)) {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section(stringResource(R.string.text_note)) {
                        size.note?.let { LabelValue(value = it) }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}