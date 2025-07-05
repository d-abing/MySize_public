package com.aube.mysize.presentation.ui.screens.recommend.shop_recommend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.constants.AnimationConstants
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun RecommendationFailure(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Animation(
                name = AnimationConstants.BUBBLE_EFFECT_ANIMATION,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecommendationFailurePreview() {
    MySizeTheme {
        RecommendationFailure(
            message = "추천할 수 있는 쇼핑몰이 없습니다.\n선호 설정을 변경해보세요."
        )
    }
}
