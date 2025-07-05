package com.aube.mysize.presentation.ui.screens.info.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.screens.info.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginFlowScreen(
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    var showSignUp by remember { mutableStateOf(false) }
    var showEmailVerification by remember { mutableStateOf(false) }
    var showPasswordReset by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showSignUp = user != null
        showEmailVerification = user?.isEmailVerified == false
    }

    when {
        showSignUp && showEmailVerification -> {
            CheckVerificationScreen(
                onVerificationSuccess = onLoginSuccess,
                onBackClick = {
                    showSignUp = false
                    showEmailVerification = false
                }
            )
        }

        showSignUp -> {
            SignUpScreen(
                onSignUpSuccess = {
                    showEmailVerification = true
                },
                onBackClick = {
                    showSignUp = false
                }
            )
        }

        showPasswordReset -> {
            PasswordResetScreen(
                onBackClick = {
                    showPasswordReset = false
                }
            )
        }

        else -> {
            LogInScreen(
                onLoginSuccess = {
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    if (currentUser != null && currentUser.isEmailVerified) {
                        onLoginSuccess()
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_email_not_verified), Toast.LENGTH_SHORT).show()
                    }
                },
                onSignUpClick = { showSignUp = true },
                onFindPasswordClick = { showPasswordReset = true }
            )
        }
    }
}
