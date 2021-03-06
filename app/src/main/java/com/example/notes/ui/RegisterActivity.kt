package com.example.notes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notes.App
import com.example.notes.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        App.mAuth = FirebaseAuth.getInstance()
        if (App.mAuth?.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
            finish();
        }
    }
}