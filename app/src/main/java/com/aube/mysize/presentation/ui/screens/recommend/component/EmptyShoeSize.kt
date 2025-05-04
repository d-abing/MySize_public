package com.aube.mysize.presentation.ui.screens.recommend.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.RecommendedSizeResult
import com.aube.mysize.presentation.ui.component.lottie.Animation

@Composable
fun EmptyShoeSize(
    result: RecommendedSizeResult.Failure,
    onEditBodySize: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Animation(
                name = "bubble_effect.json",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Text(
                text = result.message,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            TextButton(
                onClick = { onEditBodySize() },
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "발 길이 등록하기"
                )
            }
        }
    }
}