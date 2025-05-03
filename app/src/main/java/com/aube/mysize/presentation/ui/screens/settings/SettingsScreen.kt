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
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.ui.component.BodySizeCard
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore.clearUserPreference
import com.aube.mysize.presentation.ui.screens.settings.component.LanguageDropdown
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun SettingsScreen(
    onLanguageSelected: (String) -> Unit,
    onBodySizeSelected: (Set<String>) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val selectedLanguageCode by produceState(initialValue = "en") {
        SettingsDataStore.getLanguage(context).collect { lang ->
            value = lang
        }
    }
    val selectedKeys by produceState(initialValue = setOf<String>()) {
        SettingsDataStore.getBodyFields(context).collect { keys ->
            value = keys
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
            title = "기본 공개 신체 정보 설정",
            description = "옷장에서 전체 공개 시 공개할 신체 정보를 선택합니다",
            content = {
                BodySizeCard(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(0.2f),
                    selectableKeys = setOf(
                        "키",
                        "몸무게",
                        "가슴 둘레",
                        "허리 둘레",
                        "엉덩이 둘레",
                        "목 둘레",
                        "어깨 너비",
                        "팔 길이",
                        "다리 안쪽 길이"
                    ),
                    selectedKeys = selectedKeys,
                    onSelectionChanged = {
                        onBodySizeSelected(it)
                    }
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

        /* TODO 나중에 삭제 할 것 */
        HorizontalDivider()

        val bodySizeViewModel = hiltViewModel<BodySizeViewModel>()

        SettingItem(
            title = "더미 데이터 추가",
            description = "더미 데이터 추가",
            content = {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            bodySizeViewModel.insert(
                                BodySize(
                                    gender = "남성",
                                    height = 175f,
                                    weight = 70f,
                                    chest = 90f,
                                    waist = 75f,
                                    hip = 95f,
                                    neck = 38f,
                                    shoulder = 42f,
                                    arm = 60f,
                                    leg = 80f,
                                    footLength = 230f,
                                    footWidth = 100f,
                                    date = LocalDate.now()
                                )
                            )
                        }
                    ) {
                        Text("남성")
                    }

                    Button(
                        onClick = {
                            bodySizeViewModel.insert(
                                BodySize(
                                    gender = "여성",
                                    height = 165f,
                                    weight = 60f,
                                    chest = 85f,
                                    waist = 65f,
                                    hip = 90f,
                                    neck = 35f,
                                    shoulder = 38f,
                                    arm = 55f,
                                    leg = 75f,
                                    footLength = 220f,
                                    footWidth = 95f,
                                    date = LocalDate.now()
                                )
                            )
                        }
                    ) {
                        Text("여성")
                    }
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                clearUserPreference(context)
                            }
                        }
                    ) {
                        Text("UserPref 초기화")
                    }
                }
            }
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
