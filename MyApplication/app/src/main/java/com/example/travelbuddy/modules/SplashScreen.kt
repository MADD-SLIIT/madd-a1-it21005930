package com.example.travelbuddy.modules;

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.travelbuddy.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        Log.d("SplashScreen", "FirebaseAuth initialization starting")

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Check if user is already logged in
        val currentUser = auth.currentUser

        // Use a handler to simulate a delay (for splash effect)
        Handler(Looper.getMainLooper()).postDelayed({
            if (currentUser != null) {
                // User is logged in,
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                // No user is logged in, redirect to LoginSignupPage
                startActivity(Intent(this, SignupActivity::class.java))
                finish()
            }
        }, 2500L)  // Delay for 2.5 seconds (optional for splash screen effect)
    }
}
