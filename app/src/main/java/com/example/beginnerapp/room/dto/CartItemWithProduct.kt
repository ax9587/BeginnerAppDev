package com.example.beginnerapp.room.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.example.beginnerapp.model.CartItem
import com.example.beginnerapp.room.entity.CartItemEntity
import com.example.beginnerapp.room.entity.ProductEntity
import com.example.beginnerapp.room.entity.toDomain

// Data Transfer Object for joined cart item and product
data class CartItemWithProduct(
    @Embedded val cartItem: CartItemEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductEntity
)

fun CartItemWithProduct.toDomain(): CartItem = CartItem(
    product = product.toDomain(),
    quantity = cartItem.quantity
)
