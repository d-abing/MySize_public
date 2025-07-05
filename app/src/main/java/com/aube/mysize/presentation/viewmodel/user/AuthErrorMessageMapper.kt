package com.aube.mysize.presentation.viewmodel.user

import android.content.Context
import com.aube.mysize.R
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

object AuthErrorMessageMapper {

    fun fromSignIn(e: Throwable, context: Context): String {
        val msg = e.message ?: return context.getString(R.string.error_unknown)
        return when {
            "password is invalid" in msg -> context.getString(R.string.error_invalid_password)
            "no user record" in msg -> context.getString(R.string.error_user_not_found)
            "network error" in msg.lowercase() -> context.getString(R.string.error_network)
            "badly formatted" in msg -> context.getString(R.string.error_invalid_email_format)
            else -> context.getString(R.string.error_signin_failed)
        }
    }

    fun fromSignUp(e: Throwable, context: Context): String {
        return when (e) {
            is FirebaseAuthUserCollisionException -> context.getString(R.string.error_email_already_exists)
            else -> e.message ?: context.getString(R.string.error_signup_failed)
        }
    }

    fun fromSignOut(e: Throwable, context: Context): String {
        val msg = e.message?.lowercase() ?: return context.getString(R.string.error_signout_failed)
        return when {
            "network" in msg -> context.getString(R.string.error_network)
            "google" in msg || "sign in" in msg -> context.getString(R.string.error_google_signout_failed)
            else -> context.getString(R.string.error_generic_signout_failed)
        }
    }

    fun fromChangePassword(e: Throwable, context: Context): String {
        val msg = e.message?.lowercase() ?: return context.getString(R.string.error_change_password_failed)
        return when {
            "auth/weak-password" in msg -> context.getString(R.string.error_weak_password)
            "auth/wrong-password" in msg -> context.getString(R.string.error_wrong_password)
            "network" in msg -> context.getString(R.string.error_network)
            else -> context.getString(R.string.error_change_password_failed)
        }
    }

    fun fromDeleteUser(e: Throwable, context: Context): String {
        val msg = e.message?.lowercase() ?: return context.getString(R.string.error_delete_account_failed)
        return when {
            "network" in msg -> context.getString(R.string.error_network)
            "requires-recent-login" in msg -> context.getString(R.string.error_requires_recent_login)
            else -> context.getString(R.string.error_delete_account_failed)
        }
    }

    fun fromResetPassword(e: Throwable?, context: Context): String {
        return when (e) {
            is FirebaseAuthInvalidUserException -> context.getString(R.string.error_invalid_user)
            is FirebaseAuthInvalidCredentialsException -> context.getString(R.string.error_invalid_email_format)
            is FirebaseAuthException -> when (e.errorCode) {
                "ERROR_TOO_MANY_REQUESTS" -> context.getString(R.string.error_too_many_requests)
                else -> context.getString(R.string.error_reset_password_failed, e.errorCode)
            }
            is FirebaseNetworkException -> context.getString(R.string.error_network)
            null -> context.getString(R.string.error_invalid_user)
            else -> context.getString(R.string.error_unknown)
        }
    }
}
