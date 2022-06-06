package com.example.notes

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class App : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        var mAuth: FirebaseAuth? = null
    }

}