package com.example.travelbuddy.modules

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbuddy.ReviewsAdapter
import com.example.travelbuddy.databinding.ActivityReviewBinding
import com.example.travelbuddy.model.Review
import com.google.firebase.firestore.FirebaseFirestore

class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var placeId: String
    private lateinit var adapter: ReviewsAdapter

    // Register a result launcher to refresh reviews on result
    private val reviewLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Reload the review list if the result was OK (both for creating and editing)
                loadReviews()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        // Get place ID passed from previous activity
        placeId = intent.getStringExtra("placeId") ?: ""

        // Setup RecyclerView
        adapter = ReviewsAdapter(mutableListOf(), placeId)
        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.reviewsRecyclerView.adapter = adapter

        // Load reviews from Firestore
        loadReviews()

        // Handle "Write Review" button click
        binding.writeReviewButton.setOnClickListener {
            val intent = Intent(this, CreateEditReviewActivity::class.java)
            intent.putExtra("placeId", placeId)
            reviewLauncher.launch(intent)  // Launch the Create/Edit Review activity
        }
    }

    private fun loadReviews() {
        db.collection("reviews")
            .whereEqualTo("placeId", placeId)
            .get()
            .addOnSuccessListener { documents ->
                val reviews = documents.map { doc ->
                    Review(
                        doc.id,
                        doc.getString("userId") ?: "",
                        doc.getString("username") ?: "",
                        doc.getDouble("rating")?.toFloat() ?: 0f,
                        doc.getString("reviewText") ?: "",
                        doc.getDate("createdAt"),
                        doc.getDate("updatedAt")
                    )
                }

                if (reviews.isNotEmpty()) {
                    binding.emptyStateTextView.visibility = View.GONE
                    adapter.updateReviews(reviews)
                } else {
                    binding.emptyStateTextView.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ReviewActivity", "Error getting reviews: ", exception)
            }
    }
}
