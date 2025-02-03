package com.example.beginnerapp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.beginnerapp.model.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String,
    val rating: Float,
    val isInStock: Boolean
)

// Extension functions for mapping between database models and domain
fun ProductEntity.toDomain() = Product(
    id = id,
    name = name,
    price = price,
    description = description,
    imageUrl = imageUrl,
    rating = rating,
    isInStock = isInStock
)
