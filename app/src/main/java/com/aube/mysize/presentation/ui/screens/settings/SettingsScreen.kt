package com.aube.mysize.presentation.ui.screens.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore

@Composable
fun SettingsScreen(
    onLanguageSelected: (String) -> Unit,
) {
    val context = LocalContext.current
    val selectedLanguageCode by produceState(initialValue = "en") {
        SettingsDataStore.getLanguage(context).collect { lang ->
            value = lang
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SettingItem(
            title = "언어 설정",
            description = "앱 언어를 선택하세요",
            content = {
                LanguageDropdown(
                    selectedLocaleCode = selectedLanguageCode,
                    onLanguageSelected = onLanguageSelected
                )
            },
        )

        HorizontalDivider()

        SettingItem(
            title = "개인 정보 처리 방침",
            description = "개인 정보 처리 확인하기",
            onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://aubeco2.blogspot.com/2024/03/blog-post.html")
                )
                context.startActivity(intent)
            }
        )

        HorizontalDivider()

        SettingItem(
            title = "앱 버전",
            description = "현재 버전: ${getString(context, R.string.app_version)}",
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    description: String = "",
    content: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick)
                else Modifier
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            if (content != null) {
                Spacer(modifier = Modifier.height(8.dp))
                content()
            }
        }
    }
}
