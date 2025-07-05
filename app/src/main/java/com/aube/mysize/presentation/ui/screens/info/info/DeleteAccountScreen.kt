package com.aube.mysize.presentation.ui.screens.info.info

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.component.button.MSSmallButton
import com.aube.mysize.presentation.viewmodel.user.AuthViewModel

@Composable
fun DeleteAccountScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onDeleteAccount: () -> Unit
) {
    val context = LocalContext.current
    var currentPassword by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val isDeleting by authViewModel.isDeleting.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = stringResource(R.string.text_delete_account),
            style = MaterialTheme.typography.bodyLarge
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.text_delete_account),
                style = MaterialTheme.typography.bodySmall
            )

            TextField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                placeholder = { Text(stringResource(R.string.placeholder_current_password)) },
                textStyle = MaterialTheme.typography.bodyMedium,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                isError = currentPassword.isEmpty(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                )
            )

            if (hasError) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            MSSmallButton(
                text = stringResource(R.string.action_delete_account),
                enabled = currentPassword.isNotEmpty(),
                isLoading = isDeleting,
                onClick = {
                    authViewModel.deleteEmailUser(
                        context = context,
                        password = currentPassword,
                        onSuccess = {
                            Toast.makeText(
                                context,
                                context.getString(R.string.text_delete_account_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            hasError = false
                            onDeleteAccount()
                        },
                        onFailure = {
                            hasError = true
                            errorMessage = it
                        }
                    )
                },
            )
        }
    }
}

