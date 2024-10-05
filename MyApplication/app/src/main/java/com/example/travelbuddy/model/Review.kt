package com.example.travelbuddy.model

import java.util.*

data class Review(
    val id: String,
    val userId: String,
    val username: String,
    val rating: Float,
    val reviewText: String,
    val createdAt: Date? = null,
    val updatedAt: Date? = null
)
