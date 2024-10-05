package com.example.travelbuddy.modules

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.travelbuddy.databinding.ActivityCreateEditReviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class CreateEditReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEditReviewBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var reviewId: String? = null
    private lateinit var placeId: String
    private lateinit var placeName: String
    private lateinit var placeLocation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEditReviewBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Get the place and review details passed via Intent
        placeId = intent.getStringExtra("placeId") ?: ""
        placeName = intent.getStringExtra("placeName") ?: ""
        placeLocation = intent.getStringExtra("placeLocation") ?: ""
        reviewId = intent.getStringExtra("reviewId")

        if (reviewId != null) {
            loadReviewForEdit(reviewId!!)
        }

        // Save Review button click
        binding.saveReviewButton.setOnClickListener {
            val rating = binding.ratingBar.rating
            val reviewText = binding.reviewEditText.text.toString()

            if (reviewId != null) {
                updateReview(reviewId!!, rating, reviewText)
            } else {
                createReview(placeId, rating, reviewText)
            }
        }
    }

    private fun loadReviewForEdit(reviewId: String) {
        db.collection("reviews").document(reviewId)
            .get()
            .addOnSuccessListener { document ->
                binding.reviewEditText.setText(document.getString("reviewText"))
                binding.ratingBar.rating = document.getDouble("rating")?.toFloat() ?: 0f
            }
    }

    private fun createReview(placeId: String, rating: Float, reviewText: String) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val fullName = documentSnapshot.getString("fullName") ?: "Anonymous"

                val review = hashMapOf(
                    "placeId" to placeId,
                    "userId" to userId,
                    "username" to fullName,
                    "rating" to rating,
                    "reviewText" to reviewText,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )

                db.collection("reviews")
                    .add(review)
                    .addOnSuccessListener {
                        setResult(RESULT_OK) // Set result to refresh the list
                        Toast.makeText(this, "Review created!", Toast.LENGTH_SHORT).show()
                        finish() // Close the activity
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to create review", Toast.LENGTH_SHORT).show()
                    }
            }
    }

    private fun updateReview(reviewId: String, rating: Float, reviewText: String) {
        val updatedReview = hashMapOf(
            "reviewText" to reviewText,
            "rating" to rating,
            "updatedAt" to FieldValue.serverTimestamp()
        )

        db.collection("reviews").document(reviewId)
            .update(updatedReview)
            .addOnSuccessListener {
                setResult(RESULT_OK) // Set result to refresh the list
                Toast.makeText(this, "Review updated!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,ReviewActivity::class.java)
                intent.putExtra("placeId",placeId)
                startActivity(intent)
                finish() // Close the activity
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update review", Toast.LENGTH_SHORT).show()
            }
    }
}
