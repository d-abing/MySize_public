package com.aube.mysize.presentation.ui.screens.closet.user_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun UserFeedActionBar(
    isBlocked: Boolean,
    onReportClick: () -> Unit,
    onBlockClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.2f))
            .padding(top = 4.dp, bottom = 4.dp, end = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Row(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onReportClick() }
        ) {
            Icon(
                painter = painterResource(R.drawable.siren),
                contentDescription = stringResource(R.string.report_action),
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)
            )
            Text(
                text = stringResource(R.string.report_action),
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Row(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onBlockClick() }
        ) {
            Icon(
                imageVector = if (isBlocked) Icons.Default.AccountCircle else Icons.Default.NoAccounts,
                contentDescription = stringResource(
                    if (isBlocked) R.string.action_unblock else R.string.action_block
                ),
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)
            )
            Text(
                text = stringResource(
                    if (isBlocked) R.string.action_unblock else R.string.action_block
                ),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserFeedActionBarPreview() {
    MySizeTheme {
        UserFeedActionBar(
            isBlocked = false,
            onReportClick = {},
            onBlockClick = {}
        )
    }
}
