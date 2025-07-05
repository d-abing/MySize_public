package com.aube.mysize.presentation.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.aube.mysize.R
import com.aube.mysize.presentation.model.report.ReportReason
import com.aube.mysize.presentation.model.report.ReportType
import com.aube.mysize.presentation.ui.component.button.MSSmallButton
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun ReportDialog(
    type: ReportType,
    onDismiss: () -> Unit,
    onConfirm: (ReportReason) -> Unit,
) {
    var selectedReason by remember { mutableStateOf<ReportReason?>(null) }

    val reasons = ReportReason.forType(type)

    val titleText = stringResource(
        R.string.report_title,
        type.displayName
    )

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 360.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = titleText,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = stringResource(R.string.report_description),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Column {
                reasons.forEach { reason ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { selectedReason = reason }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedReason == reason,
                            onClick = { selectedReason = reason }
                        )
                        Text(text = reason.displayName)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MSSmallButton(
                    text = stringResource(R.string.action_cancel),
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.2f),
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                MSSmallButton(
                    text = stringResource(R.string.report_action),
                    onClick = { selectedReason?.let { onConfirm(it) } },
                    enabled = selectedReason != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = Color.White,
                        disabledContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.3f),
                        disabledContentColor = Color.White.copy(alpha = 0.5f)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportDialogPreview() {
    MySizeTheme {
        ReportDialog(
            type = ReportType.USER,
            onDismiss = {},
            onConfirm = {}
        )
    }
}