package com.aube.mysize.presentation.ui.screens.recommend.shop_recommend

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.recommend.RecommendedShop
import com.aube.mysize.presentation.model.recommend.AgeGroup
import com.aube.mysize.presentation.model.recommend.BodyType
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.PriceRange
import com.aube.mysize.presentation.model.recommend.Style
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun RecommendedShopCard(shop: RecommendedShop) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(shop.shopUrl))
            context.startActivity(intent)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = shop.shopName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = shop.shopUrl,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendedShopCardPreview() {
    MySizeTheme {
        RecommendedShopCard(
            shop = RecommendedShop(
                shopName = "핏온미 스토어",
                shopUrl = "https://fitonme.example.com",
                styles = listOf(Style.CASUAL, Style.STREET),
                ageGroup = listOf(AgeGroup.TWENTIES),
                priceRange = PriceRange.MEDIUM,
                gender = Gender.FEMALE,
                body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.SLIM_AVERAGE)
            )
        )
    }
}

