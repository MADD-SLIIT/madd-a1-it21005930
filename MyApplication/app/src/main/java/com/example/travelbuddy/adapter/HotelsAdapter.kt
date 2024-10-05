package com.example.travelbuddy.modules

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbuddy.R
import com.example.travelbuddy.databinding.ItemHotelBinding
import com.example.travelbuddy.model.Places

class HotelsAdapter : RecyclerView.Adapter<HotelsAdapter.HotelViewHolder>() {

    private val hotels = listOf(
        Places(
            "Sigiriya",
            "Dambulla, Sri Lanka",
            4.8f,
            R.drawable.hotel01,
            R.drawable.sigiriya_image,
            "Sigiriya, often referred to as \"Lion Rock,\" is an ancient rock fortress and a UNESCO World Heritage site located in the heart of Sri Lanka. Rising majestically to a height of 200 meters (660 feet), this awe-inspiring rock formation dominates the surrounding landscape with its sheer vertical walls and stunning views of lush forests and serene water gardens below.\n" +
                    "\n" +
                    "Historically, Sigiriya was used as a royal palace complex during the reign of King Kashyapa in the 5th century AD. The king, driven by fear of rebellion, moved his capital to this remote location and built a fortress on the summit of the rock. Today, visitors can see the remains of the palace, which include royal baths, intricately designed gardens, and an impressive system of water management that was centuries ahead of its time.\n"),
        Places(
            "Temple of the Tooth",
            "Kandy, Sri Lanka",
            4.5f,
            R.drawable.hotel02,
            R.drawable.temple_image,
            "The Temple of the Tooth, known locally as Sri Dalada Maligawa, is one of the most sacred Buddhist temples in the world. Situated in the historic city of Kandy, this temple houses one of the most revered relics in Buddhism—the left canine tooth of Lord Buddha. The relic has immense cultural and religious significance, and pilgrims from around the globe visit the temple to pay their respects.\n" +
                    "\n" +
                    "The temple complex is a masterpiece of Sri Lankan architecture, with its ivory-colored walls and gold-plated roof shimmering under the tropical sun. As you approach the temple, you pass through the sacred moat and the grand entrance, adorned with traditional Kandyan art and sculpture. Inside, the relic chamber is intricately decorated with gold and precious stones, and though the tooth itself is rarely displayed to the public, it is housed in a golden casket during important ceremonies."
        ),
        Places(
            "Nine Archs Bridge",
            "Ella, Sri Lanka",
            4.7f,
            R.drawable.featured_location,
            R.drawable.nine_arches_image,
            "The Nine Arches Bridge, also known as the \"Bridge in the Sky,\" is one of Sri Lanka's most iconic and picturesque landmarks. Situated in the small, idyllic town of Ella, this colonial-era railway bridge is nestled amidst lush green tea plantations and misty mountain ranges, offering an incredible view that has captivated the hearts of both locals and travelers alike.\n" +
                    "\n" +
                    "Constructed during the British colonial period in 1921, the Nine Arches Bridge stands as a testament to the engineering marvels of its time. What makes this bridge truly remarkable is that it is built entirely of solid stone and bricks, without the use of steel, a construction technique that was rare for its era. Stretching across a 91-meter length and standing 24 meters high, the bridge's elegant arches gracefully curve over a deep valley, creating a stunning visual spectacle that blends seamlessly with the surrounding natural beauty."
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val binding = ItemHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = hotels[position]
        holder.bind(hotel)

        // Handle click event to open DetailActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("hotelName", hotel.name)
                putExtra("hotelLocation", hotel.location)
                putExtra("hotelImageResId", hotel.imageResId)
                putExtra("hotelDescription", hotel.description)
                putExtra("hotelPlaceholderImg", hotel.placeHolderImg)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = hotels.size

    inner class HotelViewHolder(private val binding: ItemHotelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Places) {
            binding.hotelName.text = hotel.name
            binding.hotelLocation.text = hotel.location
            binding.hotelRating.text = hotel.rating.toString()
            // Set the image using the drawable resource ID
            binding.hotelImage.setImageResource(hotel.imageResId)
        }
    }
}
