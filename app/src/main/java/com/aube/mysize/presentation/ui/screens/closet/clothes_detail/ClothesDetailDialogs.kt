package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.aube.mysize.R
import com.aube.mysize.presentation.model.report.ReportReason
import com.aube.mysize.presentation.model.report.ReportType
import com.aube.mysize.presentation.ui.component.dialog.ReportDialog
import com.aube.mysize.presentation.ui.component.dialog.WarningDialog
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun ClothesDetailDialogs(
    showDeleteDialog: Boolean,
    showReportDialog: Boolean,
    onDismissDelete: () -> Unit,
    onDismissReport: () -> Unit,
    onDeleteConfirm: () -> Unit,
    onReportConfirm: (ReportReason) -> Unit
) {
    if (showDeleteDialog) {
        WarningDialog(
            title = stringResource(R.string.text_delete_confirm_title),
            description = stringResource(R.string.text_delete_confirm_description),
            confirmText = stringResource(R.string.action_delete),
            onDismiss = onDismissDelete,
            onConfirm = onDeleteConfirm
        )
    }

    if (showReportDialog) {
        ReportDialog(
            type = ReportType.CLOTHES,
            onDismiss = onDismissReport,
            onConfirm = { onReportConfirm(it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClothesDeleteDialogPreview() {
    MySizeTheme {
        ClothesDetailDialogs(
            showDeleteDialog = true,
            showReportDialog = false,
            onDismissDelete = {},
            onDismissReport = {},
            onDeleteConfirm = {},
            onReportConfirm = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClothesReportDialogPreview() {
    MySizeTheme {
        ClothesDetailDialogs(
            showDeleteDialog = false,
            showReportDialog = true,
            onDismissDelete = {},
            onDismissReport = {},
            onDeleteConfirm = {},
            onReportConfirm = {}
        )
    }
}