package com.aube.mysize.presentation.ui.screens.recommend.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp

@Composable
fun EmptyBodySize(
    onAddNewBodySize: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "사이즈, 쇼핑몰 추천을 위해서\n" +
                    "신체 정보 등록이 필요합니다.\n",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "정확한 추천을 받고 싶다면\n" +
                    "모든 입력을 채워주세요. 😉",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                "신체 사이즈 등록하기"
            )
        }
    }
}