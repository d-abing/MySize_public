package com.aube.mysize.presentation.ui.screens.my_size.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun ColumnScope.LabelValue(
    label: String? = null,
    value: String
) {
    Row(
        modifier = Modifier
            .align(Alignment.End)
            .padding(vertical = 2.dp)
    ) {
        label?.let {
            Text(
                text = stringResource(R.string.label_with_colon, it),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = if (label != null) 4.dp else 0.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LabelValuePreview() {
    MySizeTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            with(this) {
                LabelValue(label = "이름", value = "홍길동")
                LabelValue(label = "나이", value = "27")
                LabelValue(value = "단독값")
            }
        }
    }
}