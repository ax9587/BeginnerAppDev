package com.example.beginnerapp.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String?,
    val rating: Float,
    val isInStock: Boolean
)
