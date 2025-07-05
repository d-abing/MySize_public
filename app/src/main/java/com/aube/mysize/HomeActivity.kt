package com.aube.mysize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aube.mysize.presentation.ui.nav.MySizeApp
import com.aube.mysize.presentation.ui.nav.StatusBar
import com.aube.mysize.ui.theme.MySizeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /*val language = runBlocking {
            SettingsDataStore.getLanguage(applicationContext).first()
        }
        setAppLocale(this, language)*/

        setContent {
            MySizeTheme {
                StatusBar()
                MySizeApp()
            }
        }
    }
}