package com.aube.mysize.presentation.ui.screens.closet.user_feed

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aube.mysize.R
import com.aube.mysize.presentation.model.report.ReportType
import com.aube.mysize.presentation.ui.component.dialog.ReportDialog
import com.aube.mysize.presentation.ui.component.dialog.UserProfileImageDialog
import com.aube.mysize.presentation.ui.component.dialog.WarningDialog

@Composable
fun UserFeedDialogs(
    profileImageUrl: String,
    showProfileImage: Boolean,
    showReportDialog: Boolean,
    showBlockDialog: Boolean,
    isBlocked: Boolean,
    onDismissImage: () -> Unit,
    onDismissReport: () -> Unit,
    onDismissBlock: () -> Unit,
    onReportConfirm: (String) -> Unit,
    onBlockConfirm: () -> Unit
) {
    if (showProfileImage) {
        UserProfileImageDialog(profileImageUrl = profileImageUrl, onDismiss = onDismissImage)
    }

    if (showReportDialog) {
        ReportDialog(
            type = ReportType.USER,
            onDismiss = onDismissReport,
            onConfirm = { onReportConfirm(it.name) }
        )
    }

    if (showBlockDialog) {
        WarningDialog(
            title = stringResource(
                if (isBlocked) R.string.text_unblock_confirm_title else R.string.text_block_confirm_title
            ),
            description = stringResource(
                if (isBlocked) R.string.text_unblock_confirm_description else R.string.text_block_confirm_description
            ),
            confirmText = stringResource(
                if (isBlocked) R.string.action_unblock else R.string.action_block
            ),
            onDismiss = onDismissBlock,
            onConfirm = onBlockConfirm
        )
    }
}
