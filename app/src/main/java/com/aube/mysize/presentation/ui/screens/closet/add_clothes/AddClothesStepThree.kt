package com.aube.mysize.presentation.ui.screens.closet.add_clothes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.toUi
import com.aube.mysize.presentation.model.Visibility
import com.aube.mysize.presentation.ui.component.SelectOptionCard
import com.aube.mysize.presentation.ui.component.mysize.BodySizeCard

@Composable
fun ColumnScope.AddClothesStepThree(
    bodySize: BodySize?,
    selectedKeys: Set<String>,
    onSelectionChanged: (Set<String>) -> Unit,
    onAddNewBodySize: () -> Unit,
    selectedVisibility: Visibility,
    onVisibilityChanged: (Visibility) -> Unit,
    onPrevious: () -> Unit,
    onComplete: () -> Unit,
) {
    val bodySizeCard = bodySize?.toUi()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "공개할 신체 정보", style = MaterialTheme.typography.bodyLarge)

            bodySizeCard?.let {
                BodySizeCard(
                    title = "신체",
                    imageVector = it.imageVector,
                    description = it.description,
                    selectableKeys = it.description.keys,
                    selectedKeys = selectedKeys,
                    onSelectionChanged = onSelectionChanged
                )
            } ?:
            TextButton(
                onClick = { onAddNewBodySize() },
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .wrapContentHeight(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "새로운 사이즈 등록하기")
            }

            Column(modifier = Modifier.padding(top = 24.dp)) {
                Text("공개 대상", style = MaterialTheme.typography.bodyLarge)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Visibility.entries.forEach { option ->
                        SelectOptionCard(
                            text = option.displayName,
                            icon = option.icon,
                            selected = selectedVisibility == option,
                            onClick = { onVisibilityChanged(option) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Button(
                onClick = onPrevious,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("이전")
            }
            Button(
                onClick = onComplete,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("등록")
            }
        }
    }

}