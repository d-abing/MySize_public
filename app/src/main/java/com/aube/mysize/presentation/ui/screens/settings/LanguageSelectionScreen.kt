package com.aube.mysize.presentation.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LanguageSelectionScreen(
    onLanguageSelected: (String) -> Unit,
) {
    val languages = listOf(
        "English" to "en",
        "العربية" to "ar",
        "Deutsch" to "de",
        "Español" to "es",
        "Français" to "fr",
        "हिन्दी" to "hi",
        "Bahasa Indonesia" to "id",
        "Italiano" to "it",
        "日本語" to "ja",
        "한국어" to "ko",
        "Polski" to "pl",
        "Português" to "pt",
        "ภาษาไทย" to "th",
        "Türkçe" to "tr",
        "Tiếng Việt" to "vi",
        "中文 (简体)" to "zh"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(languages) { (displayName, localeCode) ->
                Button(
                    onClick = { onLanguageSelected(localeCode) },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = displayName, fontSize = 18.sp)
                }
            }
        }
    }
}