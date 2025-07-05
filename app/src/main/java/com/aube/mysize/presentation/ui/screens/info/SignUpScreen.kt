package com.aube.mysize.presentation.ui.screens.info

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.R
import com.aube.mysize.presentation.viewmodel.user.AuthViewModel

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onSignUpSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val isEmailValid by remember {
        derivedStateOf {
            android.util.Patterns.EMAIL_ADDRESS.matcher(viewModel.email).matches()
        }
    }

    val isNicknameValid by remember {
        derivedStateOf {
            viewModel.nickname.length in 2..12
        }
    }

    val isPasswordValid by remember {
        derivedStateOf {
            viewModel.password.length >= 8 &&
                    viewModel.password.any { it.isDigit() } &&
                    viewModel.password.any { it.isLetter() }
        }
    }

    val textFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
    )

    BackHandler(onBack = onBackClick)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = stringResource(R.string.label_app_logo),
            modifier = Modifier
                .height(80.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(stringResource(R.string.action_sign_up), style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text(stringResource(R.string.email)) },
            isError = viewModel.email.isNotEmpty() && !isEmailValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.nickname,
            onValueChange = { viewModel.nickname = it },
            label = { Text(stringResource(R.string.nickname)) },
            isError = viewModel.nickname.isNotEmpty() && !isNicknameValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text(stringResource(R.string.password)) },
            isError = viewModel.password.isNotEmpty() && !isPasswordValid,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (!viewModel.isLoading && isEmailValid && isNicknameValid && isPasswordValid) {
                        focusManager.clearFocus()
                        performSignUp(viewModel, context, onSignUpSuccess)
                    }
                }
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        Text(
            text = stringResource(R.string.password_requirement),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                performSignUp(viewModel, context, onSignUpSuccess)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = !viewModel.isLoading && isEmailValid && isNicknameValid && isPasswordValid
        ) {
            Text(stringResource(R.string.action_sign_up))
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

private fun performSignUp(
    viewModel: AuthViewModel,
    context: Context,
    onSignUpSuccess: () -> Unit
) {
    viewModel.signUpWithEmail(
        context = context,
        onSuccess = {
            viewModel.sendEmailVerification(
                onSent = {
                    Toast.makeText(context, context.getString(R.string.email_verification_sent), Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )
            onSignUpSuccess()
        },
        onFailure = { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    )
}
