package com.aube.mysize.presentation.ui.screens.closet.add_clothes

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.ui.component.closet.TagInput

@Composable
fun ColumnScope.AddClothesStepOne(
    selectedColor: Color?,
    selectedImage: Uri?,
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

    Column(
        modifier = Modifier
            .weight(1f),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = memo,
            onValueChange = onMemoChange,
            placeholder = { Text("메모 추가") },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
                .weight(1f)
                .padding(bottom = 4.dp)
            ,
            textStyle = MaterialTheme.typography.bodyMedium,
            minLines = 3,
            maxLines = 10,
            singleLine = false,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White.copy(alpha = 0.7f),
                focusedContainerColor = Color.White.copy(alpha = 0.7f),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        TagInput(
            tags = tags,
            onTagAdd = onTagAdd,
            onTagRemove = onTagRemove,
            onFocusChanged = { isFocused = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = (selectedColor != null && selectedImage != null)
        ) {
            Text(
                text =
                    if (selectedImage == null) "이미지를 추가해주세요"
                    else if (selectedColor == null) "이미지의 원하는 부분을 클릭해 대표색을 추출해주세요"
                    else "다음"
            )
        }
    }
}
