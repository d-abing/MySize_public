package com.aube.mysize.presentation.ui.screens.info.info

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
fun ChangePasswordScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onPasswordUpdated: () -> Unit,
) {
    val context = LocalContext.current
    val isChanging by authViewModel.isChanging.collectAsState()

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val isPasswordValid = newPassword.length >= 8 &&
            newPassword.any { it.isDigit() } &&
            newPassword.any { it.isLetter() }

    val isConfirmValid = newPassword == confirmPassword

    val canSubmit = currentPassword.isNotBlank() && isPasswordValid && isConfirmValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Text(
            text = stringResource(R.string.text_edit_profile_title),
            style = MaterialTheme.typography.bodyLarge
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.text_change_password_title),
                style = MaterialTheme.typography.bodySmall
            )

            PasswordInputField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                placeholder = stringResource(R.string.placeholder_current_password),
                isError = currentPassword.isEmpty()
            )

            PasswordInputField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = stringResource(R.string.placeholder_new_password),
                isError = newPassword.isNotEmpty() && !isPasswordValid
            )

            PasswordInputField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = stringResource(R.string.placeholder_confirm_password),
                isError = confirmPassword.isNotEmpty() && !isConfirmValid
            )

            Text(
                text = stringResource(R.string.password_requirement),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )

            if (showError) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            MSSmallButton(
                text = stringResource(R.string.action_edit),
                enabled = canSubmit && !isChanging,
                isLoading = isChanging,
                onClick = {
                    authViewModel.changePassword(
                        context = context,
                        current = currentPassword,
                        new = newPassword,
                        onSuccess = {
                            Toast.makeText(
                                context,
                                context.getString(R.string.text_password_changed_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            showError = false
                            onPasswordUpdated()
                        },
                        onFailure = {
                            showError = true
                            errorMessage = it
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        textStyle = MaterialTheme.typography.bodyMedium,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        isError = isError,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
        )
    )
}

