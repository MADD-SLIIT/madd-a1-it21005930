package com.example.travelbuddy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbuddy.databinding.ItemReviewBinding
import com.example.travelbuddy.model.Review
import com.example.travelbuddy.modules.CreateEditReviewActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ReviewsAdapter(private var reviews: MutableList<Review>, private val placeId: String) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int = reviews.size

    fun updateReviews(newReviews: List<Review>) {
        reviews.clear()
        reviews.addAll(newReviews)
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.usernameTextView.text = review.username
            binding.ratingBar.rating = review.rating
            binding.reviewTextView.text = review.reviewText

            // Check if the review belongs to the current user
            if (review.userId == currentUserId) {
                // Show edit and delete buttons if the review is from the current user
                binding.editButton.visibility = View.VISIBLE
                binding.deleteButton.visibility = View.VISIBLE
            } else {
                // Hide the edit and delete buttons for other users' reviews
                binding.editButton.visibility = View.GONE
                binding.deleteButton.visibility = View.GONE
            }

            // Handle Edit Button click
            binding.editButton.setOnClickListener {
                // Launch CreateEditReviewActivity with reviewId to edit the review
                val context: Context = binding.root.context
                val intent = Intent(context, CreateEditReviewActivity::class.java)
                intent.putExtra("reviewId", review.id)  // Pass reviewId for editing
                intent.putExtra("placeId", placeId)  // Pass placeId as well
                context.startActivity(intent)
            }

            // Handle Delete Button click
            binding.deleteButton.setOnClickListener {
                deleteReview(review)
            }
        }

        private fun deleteReview(review: Review) {
            val db = FirebaseFirestore.getInstance()

            db.collection("reviews").document(review.id)
                .delete()
                .addOnSuccessListener {
                    // Remove the deleted review from the list and refresh the RecyclerView
                    reviews.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    Toast.makeText(binding.root.context, "Review deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(binding.root.context, "Failed to delete review", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
