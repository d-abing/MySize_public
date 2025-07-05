package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.domain.model.clothes.BodyField
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.model.clothes.MemoVisibility
import com.aube.mysize.domain.model.clothes.Visibility
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.size.toUi
import com.aube.mysize.presentation.ui.component.BodySizeCard
import com.aube.mysize.ui.theme.MySizeTheme
import java.time.LocalDateTime

@Composable
fun BodySizeCardSection(clothes: Clothes) {
    val bodyUi = clothes.bodySize?.toUi() ?: return

    BodySizeCard(
        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
        title = stringResource(R.string.text_body_info_public_title),
        imageVector = Icons.Default.Person,
        sharedBodyFields = clothes.sharedBodyFields.map { it.displayName }.toSet(),
        description = bodyUi.description,
        selectedKeys = clothes.sharedBodyFields.map { it.displayName }.toSet(),
    )

    Spacer(modifier = Modifier.height(12.dp))
}

@Preview(showBackground = true)
@Composable
fun BodySizeCardSectionPreview() {
    MySizeTheme {
        val dummyClothes = Clothes(
            id = "dummy",
            imageUrl = "",
            tags = emptySet(),
            createUserId = "user1",
            createUserProfileImageUrl = "",
            bodySize = BodySize(
                id = "1",
                uid = "user1",
                gender = Gender.FEMALE,
                height = 162.0f,
                weight = 48.0f,
                chest = 82.0f,
                waist = 63.0f,
                hip = 90.0f,
                neck = 30.0f,
                shoulder = 39.0f,
                arm = 30.0f,
                leg = 30.0f,
                footLength = 270.0f,
                footWidth = 30.0f,
                date = LocalDateTime.now(),
            ),
            sharedBodyFields = setOf(
                BodyField.HEIGHT,
                BodyField.WEIGHT,
                BodyField.CHEST,
                BodyField.SHOULDER
            ),
            visibility = Visibility.PUBLIC,
            memo = null,
            memoVisibility = MemoVisibility.PRIVATE,
            linkedSizes = emptyList(),
            dominantColor = 0xFFE0E0E0.toInt(),
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )

        BodySizeCardSection(clothes = dummyClothes)
    }
}
