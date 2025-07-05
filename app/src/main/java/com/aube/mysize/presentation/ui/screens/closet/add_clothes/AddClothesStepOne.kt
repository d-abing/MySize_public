package com.aube.mysize.presentation.ui.screens.closet.add_clothes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.screens.closet.component.TagInput
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun AddClothesStepOne(
    selectedColor: Color?,
    selectedImage: Any?,
    memo: String,
    onMemoChange: (String) -> Unit,
    tags: Set<String>,
    onTagAdd: (String) -> Unit,
    onTagRemove: (String) -> Unit,
    onNext: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    BackHandler(enabled = isFocused) {
        focusManager.clearFocus()
        isFocused = false
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(0.2f), RoundedCornerShape(16.dp))
                .background(
                    color = selectedColor ?: Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        )

        OutlinedTextField(
            value = memo,
            onValueChange = onMemoChange,
            placeholder = { Text(stringResource(R.string.placeholder_memo_add)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(0.2f), RoundedCornerShape(16.dp)),
            textStyle = MaterialTheme.typography.bodyMedium,
            minLines = 1,
            maxLines = 10,
            singleLine = false,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        TagInput(
            tags = tags,
            onFocusChanged = { isFocused = it },
            onTagAdd = onTagAdd,
            onTagRemove = onTagRemove
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp),
            enabled = selectedColor != null && selectedImage != null
        ) {
            val text = when {
                selectedImage == null -> stringResource(R.string.error_required_image_incomplete)
                selectedColor == null -> stringResource(R.string.error_required_color_incomplete)
                else -> stringResource(R.string.action_next)
            }
            Text(textAlign = TextAlign.Center, text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddClothesStepOnePreview() {
    MySizeTheme {
        AddClothesStepOne(
            selectedColor = Color(0xFFBB86FC),
            selectedImage = "https://via.placeholder.com/300",
            memo = "이 옷은 특별한 날 입었어요!",
            onMemoChange = {},
            tags = setOf("봄", "데이트룩", "화이트"),
            onTagAdd = {},
            onTagRemove = {},
            onNext = {}
        )
    }
}
