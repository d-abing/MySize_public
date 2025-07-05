package com.aube.mysize.presentation.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun SizeButton(
    modifier: Modifier = Modifier,
    title: String,
    sizeLabel: String,
    isSaved: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = sizeLabel,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (isSaved) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = stringResource(R.string.saved_size),
                tint = Color(0xFFF22959),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SizeButtonPreview() {
    MySizeTheme {
        Row(modifier = Modifier.padding(16.dp)) {
            SizeButton(
                modifier = Modifier.size(80.dp),
                title = "상의",
                sizeLabel = "S",
                isSaved = true,
                onClick = {}
            )
            Spacer(modifier = Modifier.width(8.dp))
            SizeButton(
                modifier = Modifier.size(80.dp),
                title = "하의",
                sizeLabel = "M",
                isSaved = false,
                onClick = {}
            )
        }
    }
}
