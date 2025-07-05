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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.domain.model.clothes.MemoVisibility
import com.aube.mysize.domain.model.clothes.Visibility
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.clothes.toUi
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.size.toUi
import com.aube.mysize.presentation.ui.component.BodySizeCard
import com.aube.mysize.presentation.ui.component.button.MSSelectOptionButton
import com.aube.mysize.presentation.viewmodel.settings.SettingsViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.AddClothesStepThree(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    bodySize: BodySize?,
    selectedKeys: Set<String>,
    onSelectionChanged: (Set<String>) -> Unit,
    onAddNewBodySize: () -> Unit,
    selectedVisibility: Visibility,
    onVisibilityChanged: (Visibility) -> Unit,
    selectedMemoVisibility: MemoVisibility,
    onMemoVisibilityChanged: (MemoVisibility) -> Unit,
    onPrevious: () -> Unit,
    isUploading: Boolean,
    onComplete: () -> Unit,
) {
    val bodySizeCard = bodySize?.toUi()
    var rememberChoice by remember { mutableStateOf(false) }

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
            Text(
                text = stringResource(R.string.text_visibility_target_title),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Visibility.entries.forEach { option ->
                    val (label, icon) = option.toUi()
                    MSSelectOptionButton(
                        text = label,
                        icon = icon,
                        selected = selectedVisibility == option,
                        onClick = { onVisibilityChanged(option) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.text_public_body_info_title),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            bodySizeCard?.let { card ->
                BodySizeCard(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                    description = card.description,
                    selectableKeys = card.description.keys,
                    selectedKeys = selectedKeys,
                    onSelectionChanged = onSelectionChanged
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable {
                            rememberChoice = !rememberChoice
                        }
                ) {
                    Icon(
                        imageVector = if (rememberChoice) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                        contentDescription =
                            if (rememberChoice) stringResource(R.string.label_checked)
                            else stringResource(R.string.label_unchecked),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.text_remember_this_choice),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            } ?: run {
                TextButton(
                    onClick = onAddNewBodySize,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.action_add))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(R.string.action_register_new_size))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.text_memo_visibility_title),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MemoVisibility.entries.forEach { option ->
                    val (label, icon) = option.toUi()
                    MSSelectOptionButton(
                        text = label,
                        icon = icon,
                        selected = selectedMemoVisibility == option,
                        onClick = { onMemoVisibilityChanged(option) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onPrevious,
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text(stringResource(R.string.action_previous))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (rememberChoice) {
                        settingsViewModel.saveBodyFields(selectedKeys)
                    }
                    onComplete()
                },
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                if(isUploading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Text(stringResource(R.string.action_submit))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddClothesStepThreePreview() {
    val dummyBodySize = BodySize(
        id = "1",
        uid = "user1",
        gender = Gender.FEMALE,
        height = 162.0f,
        weight = 48.0f,
        chest = 82.0f,
        waist = 63.0f,
        hip = 90.0f,
        neck = 30.0f,
        shoulder = 39.0f,
        arm = 30.0f,
        leg = 30.0f,
        footLength = 270.0f,
        footWidth = 30.0f,
        date = LocalDateTime.now(),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        AddClothesStepThree(
            bodySize = dummyBodySize,
            selectedKeys = setOf("키", "몸무게", "가슴둘레"),
            onSelectionChanged = {},
            onAddNewBodySize = {},
            selectedVisibility = Visibility.PRIVATE,
            onVisibilityChanged = {},
            selectedMemoVisibility = MemoVisibility.PUBLIC,
            onMemoVisibilityChanged = {},
            onPrevious = {},
            isUploading = true,
            onComplete = {}
        )
    }
}
