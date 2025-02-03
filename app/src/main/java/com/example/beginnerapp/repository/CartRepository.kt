package com.example.beginnerapp.repository

import com.example.beginnerapp.model.CartItem
import com.example.beginnerapp.model.Product
import com.example.beginnerapp.model.toEntity
import com.example.beginnerapp.room.dao.CartItemDao
import com.example.beginnerapp.room.dao.ProductDao
import com.example.beginnerapp.room.database.ShoppingCartDatabase
import com.example.beginnerapp.room.entity.CartItemEntity
import com.example.beginnerapp.room.entity.toDomain
import javax.inject.Inject
import javax.inject.Singleton

// Cart Repository with persistence
@Singleton
class CartRepository @Inject constructor(
    private val cartItemDao: CartItemDao,
    private val productDao: ProductDao
){

    suspend fun addToCart(product: Product, quantity: Int = 1) {
        // First ensure the product exists in the database
        productDao.insertProduct(product.toEntity())

        // Then check if the item is already in cart
        val existingCartItem = cartItemDao.getCartItemByProductId(product.id)
        if (existingCartItem != null) {
            // Update quantity if item exists
            cartItemDao.updateQuantity(product.id, existingCartItem.quantity + quantity)
        } else {
            // Add new cart item if it doesn't exist
            cartItemDao.insertCartItem(
                CartItemEntity(
                    productId = product.id,
                    quantity = quantity
                )
            )
        }
    }

    suspend fun removeFromCart(productId: Long) {
        cartItemDao.deleteCartItem(productId)
    }

    suspend fun updateQuantity(productId: Long, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
        } else {
            cartItemDao.updateQuantity(productId, quantity)
        }
    }

    suspend fun getCartItems(): List<CartItem> {
        return cartItemDao.getCartItemsWithProducts()
            .map { cartItemWithProduct ->
                CartItem(
                    product = cartItemWithProduct.product.toDomain(),
                    quantity = cartItemWithProduct.cartItem.quantity
                )
            }
    }

    suspend fun clearCart() {
        cartItemDao.clearCart()
    }
}