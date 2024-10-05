package com.example.travelbuddy.model

data class Places(
    val name: String,
    val location: String,
    val rating: Float,
    val imageResId: Int,
    val placeHolderImg: Int,
    val description: String
)