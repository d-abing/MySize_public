package com.aube.mysize.presentation.ui.screens.closet.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun BigSizeButton(
    modifier: Modifier = Modifier,
    title: String,
    sizeDetail: String? = null,
    titleMaxLines: Int = 2,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(0.4f)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = titleMaxLines,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            if (sizeDetail != null) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    text = sizeDetail,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BigSizeButtonPreview() {
    MySizeTheme {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            BigSizeButton(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                title = "종류 S - 브랜드",
                sizeDetail = "25.06.08\n" +
                        "어깨 너비: 00.0cm\n" +
                        "가슴 단면: 00.0cm",
                isSelected = false,
                onClick = {}
            )
            Spacer(modifier = Modifier.width(8.dp))
            BigSizeButton(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                title = "선택됨",
                isSelected = true,
                onClick = {}
            )
        }
    }
}