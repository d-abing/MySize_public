package com.aube.mysize.presentation.ui.screens.info.login

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.presentation.viewmodel.user.AuthViewModel

@Composable
fun CheckVerificationScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onVerificationSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var emailVerified by remember { mutableStateOf(false) }

    BackHandler(onBack = onBackClick)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(48.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_mail),
            contentDescription = stringResource(R.string.email_verification_sent),
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (emailVerified) {
                    onVerificationSuccess()
                } else {
                    viewModel.checkEmailVerified(
                        onVerified = { emailVerified = true },
                        onNotVerified = {
                            Toast
                                .makeText(context, context.getString(R.string.error_email_not_verified), Toast.LENGTH_SHORT)
                                .show()
                        },
                        onFailure = {
                            Toast
                                .makeText(context, it, Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {
            Text(
                text = stringResource(
                    if (emailVerified) R.string.email_verification_success
                    else R.string.email_check_verification
                ),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.email_verification_resend),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    viewModel.sendEmailVerification(
                        onSent = {
                            Toast
                                .makeText(context, context.getString(R.string.email_verification_resent), Toast.LENGTH_SHORT)
                                .show()
                        },
                        onFailure = {
                            Toast
                                .makeText(context, context.getString(R.string.email_verification_failed), Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
        )
    }
}

