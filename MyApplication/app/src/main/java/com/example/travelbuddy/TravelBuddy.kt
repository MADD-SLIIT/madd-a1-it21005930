package com.example.travelbuddy

import android.app.Application
import com.google.firebase.FirebaseApp

class TravelBuddy : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}