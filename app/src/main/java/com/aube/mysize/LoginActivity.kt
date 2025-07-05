package com.aube.mysize

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aube.mysize.presentation.ui.nav.StatusBar
import com.aube.mysize.presentation.ui.screens.info.login.LoginFlowScreen
import com.aube.mysize.ui.theme.MySizeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MySizeTheme {
                StatusBar()
                LoginFlowScreen(
                    onLoginSuccess = {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
