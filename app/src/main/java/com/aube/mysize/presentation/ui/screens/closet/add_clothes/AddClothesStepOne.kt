package com.aube.mysize.presentation.ui.screens.closet.add_clothes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.ui.screens.closet.component.TagInput

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

    Box(modifier = Modifier
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
        placeholder = { Text("메모 추가") },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .wrapContentHeight()
            .padding(top = 8.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(0.2f), RoundedCornerShape(16.dp))
        ,
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
        onTagRemove = onTagRemove,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = onNext,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn(min = 50.dp),
        enabled = (selectedColor != null && selectedImage != null)
    ) {
        Text(
            textAlign = TextAlign.Center,
            text =
            if (selectedImage == null) "이미지를 추가해주세요"
            else if (selectedColor == null) "이미지를 클릭해 대표색을 추출해주세요"
            else "다음"
        )
    }
}
