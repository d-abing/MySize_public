package com.aube.mysize.presentation.ui.screens.recommend.shop_recommend

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.aube.mysize.presentation.model.recommend.UserPreference
import com.aube.mysize.presentation.ui.screens.recommend.component.UserPreferenceBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceBottomSheet(
    sheetState: SheetState,
    userPreference: UserPreference,
    preferenceType: String,
    onDismiss: () -> Unit,
    onSave: (UserPreference) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = null
    ) {
        UserPreferenceBottomSheet(
            currentPreference = userPreference,
            preferenceType = preferenceType,
            onSave = onSave
        )
    }
}
