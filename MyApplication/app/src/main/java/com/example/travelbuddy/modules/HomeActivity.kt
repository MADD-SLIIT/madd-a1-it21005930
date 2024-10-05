package com.example.travelbuddy.modules

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbuddy.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        binding.hotelsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.hotelsRecyclerView.adapter = HotelsAdapter()

        binding.logoutBtn.setOnClickListener {
            signOutUser()
        }
    }

    private fun signOutUser() {
        // Sign out the user using FirebaseAuth
        FirebaseAuth.getInstance().signOut()

        // Redirect to the LoginActivity
        val intent = Intent(this, LoginActivity::class.java)

        // Clear the back stack to prevent the user from going back to HomeActivity
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()  // Close the current activity
    }
}
