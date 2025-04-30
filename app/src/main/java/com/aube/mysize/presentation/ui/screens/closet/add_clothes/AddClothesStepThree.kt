package com.aube.mysize.presentation.ui.screens.closet.add_clothes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.toUi
import com.aube.mysize.presentation.model.MemoVisibility
import com.aube.mysize.presentation.model.Visibility
import com.aube.mysize.presentation.ui.component.BodySizeCard
import com.aube.mysize.presentation.ui.component.button.SelectOptionButton
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.AddClothesStepThree(
    bodySize: BodySize?,
    selectedKeys: Set<String>,
    onSelectionChanged: (Set<String>) -> Unit,
    onAddNewBodySize: () -> Unit,
    selectedVisibility: Visibility,
    onVisibilityChanged: (Visibility) -> Unit,
    selectedMemoVisibility: MemoVisibility,
    onMemoVisibilityChanged: (MemoVisibility) -> Unit,
    onPrevious: () -> Unit,
    onComplete: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val bodySizeCard = bodySize?.toUi()
    var isChecked: Boolean by remember { mutableStateOf(false) }

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
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(bottom = 24.dp)) {
                Text("공개 대상", style = MaterialTheme.typography.bodyLarge)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Visibility.entries.forEach { option ->
                        SelectOptionButton(
                            text = option.displayName,
                            icon = option.icon,
                            selected = selectedVisibility == option,
                            onClick = { onVisibilityChanged(option) }
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(bottom = 24.dp)) {
                Text(text = "전체 공개 시 공개할 신체 정보", style = MaterialTheme.typography.bodyLarge)

                bodySizeCard?.let {
                    BodySizeCard(
                        description = it.description,
                        selectableKeys = it.description.keys,
                        selectedKeys = selectedKeys,
                        onSelectionChanged = onSelectionChanged
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked -> isChecked = checked },
                            modifier = Modifier.scale(0.6f)
                        )
                        Text(
                            text = "현재 선택을 다음에도 사용합니다",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.align(Alignment.CenterVertically)
                                .clickable { isChecked = !isChecked }
                        )
                    }
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
            }

            Column(modifier = Modifier.padding(bottom = 24.dp)) {
                Text(text = "사이즈의 메모 공개 여부", style = MaterialTheme.typography.bodyLarge)

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    MemoVisibility.entries.forEach { option ->
                        SelectOptionButton(
                            text = option.displayName,
                            icon = option.icon,
                            selected = selectedMemoVisibility == option,
                            onClick = { onMemoVisibilityChanged(option) }
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
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (isChecked) {
                        coroutineScope.launch {
                            SettingsDataStore.saveBodyFields(context, selectedKeys)
                        }
                    }
                    onComplete()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("등록")
            }
        }
    }

}