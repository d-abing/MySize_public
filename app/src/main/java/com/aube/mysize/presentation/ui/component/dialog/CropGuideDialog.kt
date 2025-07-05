package com.aube.mysize.presentation.ui.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.ui.theme.MySizeTheme


@Composable
fun CropGuideDialog(onDismiss: () -> Unit) {
    GuideDialog(
        onDismiss = onDismiss,
        title = stringResource(R.string.guide_crop_title),
        content = {
            Text(
                text = stringResource(R.string.guide_crop_description),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.guide),
                contentDescription = stringResource(R.string.guide_crop_title),
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CropGuideDialogPreview() {
    MySizeTheme {
        CropGuideDialog(onDismiss = {})
    }
}