package com.example.travelbuddy.modules

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.travelbuddy.R
import com.example.travelbuddy.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Get the intent extras passed from the previous activity
        val hotelName = intent.getStringExtra("hotelName")
        val hotelLocation = intent.getStringExtra("hotelLocation")
        val hotelImageResId = intent.getIntExtra("hotelImageResId", R.drawable.sigiriya_image)
        val hotelDescription = intent.getStringExtra("hotelDescription")
        val hotelPlaceholderImg = intent.getIntExtra("hotelPlaceholderImg", R.drawable.sigiriya_image)

        // Set the data on the detail screen
        binding.hotelDetailName.text = hotelName
        binding.hotelDetailLocation.text = hotelLocation
        binding.hotelDetailImage.setImageResource(hotelPlaceholderImg)
        binding.hotelDetailDescription.text = hotelDescription

        // Handle review button click (optional)
        binding.reviewButton.setOnClickListener {
            // Pass the place details one by one using Intent
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("placeName", hotelName)
            intent.putExtra("placeLocation", hotelLocation)
            intent.putExtra("placeId", hotelName)  // Using hotel name as the ID (you can use any other unique identifier)
            startActivity(intent)
        }
    }
}
