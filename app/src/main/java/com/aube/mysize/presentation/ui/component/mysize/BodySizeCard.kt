package com.aube.mysize.presentation.ui.component.mysize

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BodySizeCard(
    title: String,
    imageVector: ImageVector,
    description: Map<String, String?>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = imageVector,
                    contentDescription = "Body Size Icon",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            val group1Keys = listOf("키", "몸무게", "성별")
            val group2Keys = listOf("가슴둘레", "허리둘레", "엉덩이둘레")
            val group3Keys = listOf("목둘레", "어깨너비", "팔 길이", "다리 안쪽 길이")

            val groups = listOf(group1Keys, group2Keys, group3Keys)

            val validGroups = groups.filter { groupKeys ->
                groupKeys.any { key -> description[key] != null }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                validGroups.forEach { groupKeys ->
                    val groupItems = groupKeys.mapNotNull { key ->
                        description[key]?.let { value -> "$key: $value" }
                    }

                    if (groupItems.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            groupItems.forEach { text ->
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}