package com.aube.mysize.presentation.ui.screens.closet.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun ClosetViewModeTabs(
    closetViewModes: List<ImageVector>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
            )
        },
        divider = {}
    ) {
        closetViewModes.forEachIndexed { index, icon ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = closetViewModes.toString(),
                        tint = if (selectedTabIndex == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClosetViewModeTabsPreview() {
    MySizeTheme {
        var selectedTabIndex by remember { mutableStateOf(0) }

        val closetViewModes = listOf(
            Icons.Default.GridView,       // 사진 보기
            Icons.Default.Straighten,     // 사이즈 보기
            Icons.Default.Palette,        // 색상 보기
            Icons.Default.Tag             // 태그 보기
        )

        ClosetViewModeTabs(
            closetViewModes = closetViewModes,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it }
        )
    }
}
