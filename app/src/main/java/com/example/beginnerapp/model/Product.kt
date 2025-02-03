package com.example.beginnerapp.model

import com.example.beginnerapp.room.entity.ProductEntity

data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String,
    val rating: Float,
    val isInStock: Boolean
)

// Extension functions for mapping between domain and database models
fun Product.toEntity() = ProductEntity(
    id = id,
    name = name,
    price = price,
    description = description,
    imageUrl = imageUrl,
    rating = rating,
    isInStock = isInStock
)
