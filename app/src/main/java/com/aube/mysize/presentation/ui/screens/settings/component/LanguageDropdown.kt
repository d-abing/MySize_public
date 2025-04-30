package com.aube.mysize.presentation.ui.screens.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdown(
    selectedLocaleCode: String,
    onLanguageSelected: (String) -> Unit
) {
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

    var expanded by remember { mutableStateOf(false) }

    var selectedLanguage by remember(selectedLocaleCode) {
        mutableStateOf(
            languages.find { it.second == selectedLocaleCode } ?: languages.first()
        )
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = "${selectedLanguage.third} ${selectedLanguage.first}",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            languages.forEach { (displayName, localeCode, flag) ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = flag, fontSize = 16.sp)
                            Text(
                                text = " $displayName",
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 16.sp
                            )
                        }
                    },
                    onClick = {
                        selectedLanguage = Triple(displayName, localeCode, flag)
                        expanded = false
                        onLanguageSelected(localeCode)
                    }
                )
            }
        }
    }
}
