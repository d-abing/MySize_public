package com.aube.mysize.presentation.ui.component.ocr

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aube.mysize.R
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun PreviewImage(uri: Uri?) {
    uri?.let {
        Spacer(Modifier.height(8.dp))
        AsyncImage(
            model = it,
            contentDescription = stringResource(R.string.ocr_preview),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImagePreview() {
    MySizeTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            PreviewImage(
                uri = Uri.parse("https://example.com/image.jpg")
            )
        }
    }
}