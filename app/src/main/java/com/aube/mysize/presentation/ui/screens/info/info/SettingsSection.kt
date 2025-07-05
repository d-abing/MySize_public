package com.aube.mysize.presentation.ui.screens.info.info

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.component.BodySizeCard

@Composable
fun SettingsSection(
    context: Context,
    selectedKeys: Set<String>,
    bodySizeSettingExpanded: Boolean,
    onToggleBodySetting: () -> Unit,
    onBodySizeSelected: (Set<String>) -> Unit,
    onBlockedUserClick: () -> Unit,
    onLanguageSettingClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        SettingItem(
            title = stringResource(R.string.text_default_body_setting_title),
            description = stringResource(R.string.text_default_body_setting_description),
            content = {
                if (bodySizeSettingExpanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    BodySizeCard(
                        containerColor = MaterialTheme.colorScheme.tertiary.copy(0.2f),
                        selectableKeys = setOf(
                            "키", "몸무게", "가슴 둘레", "허리 둘레", "엉덩이 둘레",
                            "목 둘레", "어깨 너비", "팔 길이", "다리 안쪽 길이"
                        ),
                        selectedKeys = selectedKeys,
                        onSelectionChanged = onBodySizeSelected
                    )
                }
            },
            onClick = onToggleBodySetting
        )

        HorizontalDivider()

        SettingItem(
            title = stringResource(R.string.text_blocked_users_list_title),
            onClick = onBlockedUserClick
        )

        HorizontalDivider()

        SettingItem(
            title = stringResource(R.string.text_privacy_policy_title),
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.text_privacy_policy_url))))
            }
        )

        HorizontalDivider()

        SettingItem(
            title = stringResource(R.string.text_kakao_support_title),
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.text_kakao_support_url))))
            }
        )

        HorizontalDivider()

        SettingItem(
            title = stringResource(R.string.text_app_version_title),
            description = stringResource(R.string.text_app_version_description, context.getString(R.string.app_version))
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    description: String? = null,
    content: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onClick() }
                else Modifier
            )
            .padding(vertical  = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.then(
                    if(description == null) Modifier.padding(vertical = 8.dp)
                    else Modifier
                )
            )
            if (!description.isNullOrEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (content != null) {
                content()
            }
        }
    }
}