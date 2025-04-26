package com.aube.mysize.presentation.ui.component.mysize.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.ClothesSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.TopSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizePreviewBottomSheet(
    size: ClothesSize,
    onEdit: (ClothesSize) -> Unit,
    onDelete: (ClothesSize) -> Unit,
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onEdit(size) },
                        contentDescription = "Edit"
                    )
                    Icon(
                        imageVector = Icons.Default.Delete,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onDelete(size) },
                        contentDescription = "Delete"
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            when (size) {
                is TopSize -> {
                    Section("📏 치수") {
                        size.shoulder?.let { LabelValue("어깨 너비", "${it}cm") }
                        size.chest?.let { LabelValue("가슴 단면", "${it}cm") }
                        size.sleeve?.let { LabelValue("소매 길이", "${it}cm") }
                        size.length?.let { LabelValue("총장", "${it}cm") }
                    }
                    Section("🧥 스타일") {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section("📝 참고사항") {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is BottomSize -> {
                    Section("📏 치수") {
                        size.waist?.let { LabelValue("허리 단면", "${it}cm") }
                        size.rise?.let { LabelValue("밑위 길이", "${it}cm") }
                        size.hip?.let { LabelValue("엉덩이 단면", "${it}cm") }
                        size.thigh?.let { LabelValue("허벅지 단면", "${it}cm") }
                        size.hem?.let { LabelValue("밑단 단면", "${it}cm") }
                        size.length?.let { LabelValue("총장", "${it}cm") }
                    }
                    Section("🧥 스타일") {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section("📝 참고사항") {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is OuterSize -> {
                    Section("📏 치수") {
                        size.shoulder?.let { LabelValue("어깨 너비", "${it}cm") }
                        size.chest?.let { LabelValue("가슴 단면", "${it}cm") }
                        size.sleeve?.let { LabelValue("소매 길이", "${it}cm") }
                        size.length?.let { LabelValue("총장", "${it}cm") }
                    }
                    Section("🧥 스타일") {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section("📝 참고사항") {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is OnePieceSize -> {
                    Section("📏 치수") {
                        size.shoulder?.let { LabelValue("어깨 너비", "${it}cm") }
                        size.chest?.let { LabelValue("가슴 단면", "${it}cm") }
                        size.waist?.let { LabelValue("허리 단면", "${it}cm") }
                        size.hip?.let { LabelValue("엉덩이 단면", "${it}cm") }
                        size.sleeve?.let { LabelValue("소매 길이", "${it}cm") }
                        size.rise?.let { LabelValue("밑위 길이", "${it}cm") }
                        size.thigh?.let { LabelValue("허벅지 단면", "${it}cm") }
                        size.hem?.let { LabelValue("밑단 단면", "${it}cm") }
                        size.length?.let { LabelValue("총장", "${it}cm") }
                    }
                    Section("🧥 스타일") {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section("📝 참고사항") {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is ShoeSize -> {
                    Section("📏 치수") {
                        size.footLength?.let { LabelValue("발 길이", "${it}cm") }
                        size.footWidth?.let { LabelValue("발볼 너비", "${it}cm") }
                    }
                    Section("🧥 스타일") {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section("📝 참고사항") {
                        size.note?.let { LabelValue(value = it) }
                    }
                }

                is AccessorySize -> {
                    Section("📏 치수") {
                        size.bodyPart?.let { LabelValue("부위", it) }
                    }
                    Section("🧥 스타일") {
                        size.fit?.let { LabelValue(value = it) }
                    }
                    Section("📝 참고사항") {
                        size.note?.let { LabelValue(value = it) }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
