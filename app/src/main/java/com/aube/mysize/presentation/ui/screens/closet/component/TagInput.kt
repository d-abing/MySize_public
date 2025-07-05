package com.aube.mysize.presentation.ui.screens.closet.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagInput(
    tags: Set<String>,
    onFocusChanged: (Boolean) -> Unit,
    onTagAdd: (String) -> Unit,
    onTagRemove: (String) -> Unit,
) {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            tags.forEach { tag ->
                AssistChip(
                    onClick = { onTagRemove(tag) },
                    label = {
                        Text(
                            text = stringResource(R.string.format_tag, tag),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    modifier = Modifier.height(32.dp)
                )
            }

            if (tags.size < 10) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(32.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "#",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    BasicTextField(
                        value = input,
                        onValueChange = {
                            if (it.length <= 10) input = it
                        },
                        modifier = Modifier.onFocusChanged { focusState ->
                            onFocusChanged(focusState.isFocused)
                        },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            if (input.isNotBlank()) {
                                onTagAdd(input)
                                input = ""
                            }
                        }),
                        decorationBox = { innerTextField ->
                            if (input.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.placeholder_tag_input),
                                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagInputPreview() {
    var tags by remember { mutableStateOf(setOf("여름", "파스텔", "웜톤")) }

    MaterialTheme {
        TagInput(
            tags = tags,
            onFocusChanged = {},
            onTagAdd = { newTag ->
                val trimmed = newTag.trim()
                if (trimmed.isNotEmpty() && trimmed !in tags && tags.size < 10) {
                    tags = tags + trimmed
                }
            },
            onTagRemove = { tag ->
                tags = tags - tag
            }
        )
    }
}

