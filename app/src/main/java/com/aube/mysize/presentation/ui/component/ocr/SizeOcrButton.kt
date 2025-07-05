package com.aube.mysize.presentation.ui.component.ocr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.component.button.MSMiddleButton
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun SizeOcrButton(
    text: String,
    onClick: () -> Unit
) {
    MSMiddleButton(
        icon = Icons.Filled.Straighten,
        text = text,
        onClick = onClick,
        minHeight = 80.dp,
        iconRotation = 45f
    )
}

@Preview(showBackground = true)
@Composable
fun SizeOcrButtonPreview() {
    MySizeTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            SizeOcrButton(
                text = stringResource(R.string.ocr_auto_fill_from_capture),
                onClick = {}
            )
        }
    }
}