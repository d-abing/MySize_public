package com.aube.mysize.presentation.ui.component.ocr

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.ui.component.guide.GuideButton
import com.aube.mysize.presentation.ui.component.lottie.Animation

@Composable
fun SizeOcrCard(
    onGuideButtonClick: () -> Unit,
    onRecommendationButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.Gray.copy(0.2f), RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "사이즈 추천 받기",
                fontWeight = FontWeight.SemiBold
            )

            Column {
                GuideButton(text = "캡쳐화면 가이드 보기", onClick = { onGuideButtonClick() })
            }
        }

        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "캡쳐한 사이즈표로"
            )
            Text(
                text = "추천 사이즈를 받아보세요!",
                color = MaterialTheme.colorScheme.primary.copy(0.6f)
            )
        }

        Animation(
            name = "recommend.json",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
        )

        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp),
            shape = MaterialTheme.shapes.small,
            onClick = onRecommendationButtonClick
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.ThumbUp,
                        contentDescription = "바로 가기",
                    )
                    Spacer(Modifier.width(12.dp))
                    Text("바로 가기")
                }
            }
        }
    }

}