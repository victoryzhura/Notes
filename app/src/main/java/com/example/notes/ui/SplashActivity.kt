package com.example.notes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.notes.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val auth = FirebaseAuth.getInstance()
        val intentMain = Intent(this, MainActivity::class.java)
        val intent = Intent(this, RegisterActivity::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            if (auth.currentUser != null) {
                startActivity(intentMain)
                finish()
            } else {
                startActivity(intent)
                finish()
            }
        }
    }
}