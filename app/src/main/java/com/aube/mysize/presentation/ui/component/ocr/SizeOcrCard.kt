package com.aube.mysize.presentation.ui.component.ocr

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BuildCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.constants.AnimationConstants
import com.aube.mysize.presentation.ui.component.button.MSGuideButton
import com.aube.mysize.presentation.ui.component.button.MSMiddleButton
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun SizeOcrCard(
    onGuideButtonClick: () -> Unit,
    onRecommendationButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.Gray.copy(0.2f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = stringResource(R.string.text_recommendation_title),
                fontWeight = FontWeight.SemiBold
            )

            Column {
                MSGuideButton(text = stringResource(R.string.ocr_capture_guide_view), onClick = { onGuideButtonClick() })
            }
        }

        Column(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.text_recommendation_description1)
            )
            Text(
                text = stringResource(R.string.text_recommendation_description2),
                color = MaterialTheme.colorScheme.primary.copy(0.6f)
            )
        }

        Animation(
            name = AnimationConstants.RECOMMEND_ANIMATION,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
                .padding(bottom = 16.dp)
        )

        MSMiddleButton(
            icon = Icons.Filled.BuildCircle,
            text = stringResource(R.string.action_get_recommendation),
            onClick = onRecommendationButtonClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SizeOcrCardPreview() {
    MySizeTheme {
        SizeOcrCard(
            onGuideButtonClick = {},
            onRecommendationButtonClick = {}
        )
    }
}