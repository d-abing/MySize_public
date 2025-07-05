package com.aube.mysize.presentation.ui.screens.my_size.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun ColumnScope.Section(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.75f)
            .align(Alignment.CenterHorizontally)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun SectionPreview() {
    MySizeTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            with(this) {
                Section(title = "섹션 제목") {
                    Text("내용 A")
                    Text("내용 B")
                }
            }
        }
    }
}
