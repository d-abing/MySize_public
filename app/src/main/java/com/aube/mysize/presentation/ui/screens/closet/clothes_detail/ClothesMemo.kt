package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.ui.component.MemoWithLinks
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun ClothesMemo(memo: String?) {
    if (memo.isNullOrBlank()) return

    MemoWithLinks(
        text = memo,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ClothesMemoPreview() {
    MySizeTheme {
        ClothesMemo(
            memo = "이 옷은 여름철 데일리로 입기 좋아요!\nhttps://example.com 에서 샀어요 :)"
        )
    }
}