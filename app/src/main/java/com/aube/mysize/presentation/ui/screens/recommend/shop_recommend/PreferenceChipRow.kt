package com.aube.mysize.presentation.ui.screens.recommend.shop_recommend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.model.recommend.AgeGroup
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.PriceRange
import com.aube.mysize.presentation.model.recommend.Style
import com.aube.mysize.presentation.model.recommend.UserPreference
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun PreferenceChipRow(
    userPreference: UserPreference,
    onChipClick: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        item {
            PreferenceChip(
                label = userPreference.gender.displayName,
                color = MaterialTheme.colorScheme.tertiary.copy(0.3f),
                onClick = { onChipClick("Gender") }
            )
        }
        items(userPreference.styles.toList()) {
            PreferenceChip(it.displayName, MaterialTheme.colorScheme.primaryContainer.copy(0.3f)) {
                onChipClick("Styles")
            }
        }
        items(userPreference.ageGroups.toList().sorted()) {
            PreferenceChip(it.displayName, MaterialTheme.colorScheme.secondaryContainer.copy(0.3f)) {
                onChipClick("AgeGroup")
            }
        }
        items(userPreference.priceRanges.toList().sorted()) {
            PreferenceChip(it.displayName, MaterialTheme.colorScheme.tertiaryContainer.copy(0.3f)) {
                onChipClick("PriceRange")
            }
        }
    }
}

@Composable
fun PreferenceChip(label: String, color: Color, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text(text = label, style = MaterialTheme.typography.bodySmall) },
        border = null,
        colors = AssistChipDefaults.assistChipColors().copy(containerColor = color)
    )
}

@Preview(showBackground = true)
@Composable
fun PreferenceChipRowPreview() {
    MySizeTheme {
        PreferenceChipRow(
            userPreference = UserPreference(
                gender = Gender.FEMALE,
                styles = listOf(Style.CASUAL, Style.STREET),
                ageGroups = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
                priceRanges = listOf(PriceRange.LOW, PriceRange.MEDIUM)
            ),
            onChipClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreferenceChipPreview() {
    MySizeTheme {
        PreferenceChip(
            label = "캐주얼",
            color = MaterialTheme.colorScheme.primaryContainer.copy(0.3f),
            onClick = {}
        )
    }
}
