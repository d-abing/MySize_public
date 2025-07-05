package com.aube.mysize

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null || !currentUser.isEmailVerified) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }
    }
}
