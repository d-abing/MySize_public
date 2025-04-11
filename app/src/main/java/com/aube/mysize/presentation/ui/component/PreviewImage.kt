package com.aube.mysize.presentation.ui.component

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PreviewImage(uri: Uri?) {
    uri?.let {
        Spacer(Modifier.height(8.dp))
        AsyncImage(
            model = it,
            contentDescription = "미리보기",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
        )
    }
}