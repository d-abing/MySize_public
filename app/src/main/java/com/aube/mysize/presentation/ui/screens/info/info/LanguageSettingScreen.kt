package com.aube.mysize.presentation.ui.screens.info.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.presentation.viewmodel.settings.SettingsViewModel

@Composable
fun LanguageSettingScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onLanguageSelected: (String) -> Unit
) {
    val selectedLanguageCode by settingsViewModel.language.collectAsState()

    val languages = listOf(
        Triple("English", "en", "🇺🇸"),
        Triple("العربية", "ar", "🇸🇦"),
        Triple("Deutsch", "de", "🇩🇪"),
        Triple("Español", "es", "🇪🇸"),
        Triple("Français", "fr", "🇫🇷"),
        Triple("हिन्दी", "hi", "🇮🇳"),
        Triple("Bahasa Indonesia", "id", "🇮🇩"),
        Triple("Italiano", "it", "🇮🇹"),
        Triple("日本語", "ja", "🇯🇵"),
        Triple("한국어", "ko", "🇰🇷"),
        Triple("Polski", "pl", "🇵🇱"),
        Triple("Português", "pt", "🇵🇹"),
        Triple("ภาษาไทย", "th", "🇹🇭"),
        Triple("Türkçe", "tr", "🇹🇷"),
        Triple("Tiếng Việt", "vi", "🇻🇳"),
        Triple("中文 (简体)", "zh", "🇨🇳")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Text(
            text= "언어 설정",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.tertiary.copy(0.2f), RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            languages.forEach { (displayName, localeCode, flag) ->
                val isSelected = localeCode == selectedLanguageCode

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLanguageSelected(localeCode) }
                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = flag, fontSize = 16.sp)
                        Text(
                            text = " $displayName",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f).padding(start = 12.dp)
                        )
                        RadioButton(
                            selected = isSelected,
                            onClick = { onLanguageSelected(localeCode) }
                        )
                    }
                }
            }
        }
    }
}
