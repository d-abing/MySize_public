package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.constants.AnimationConstants
import com.aube.mysize.presentation.ui.component.lottie.Animation
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun EmptyListAnimation(name: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Animation(
            name = name,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyListAnimationPreview() {
    MySizeTheme {
        EmptyListAnimation(name = AnimationConstants.CLOSET_EMPTY_ANIMATION)
    }
}