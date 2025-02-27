package com.example.beginnerapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beginnerapp.model.CartItem
import com.example.beginnerapp.model.Product
import com.example.beginnerapp.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel with Hilt injection
@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val items = cartRepository.getCartItems()
                updateCartState(items)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load cart items: ${e.message}"
                    )
                }
            }
        }
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        println("DEBUG-ADDTOCART")
        viewModelScope.launch {
            try {
                cartRepository.addToCart(product, quantity)
                val updatedItems = cartRepository.getCartItems()
                updateCartState(updatedItems)
            } catch (e: Exception) {
                println("ERROR:${e.message}")
                _uiState.update {
                    it.copy(error = "Failed to add item to cart: ${e.message}")
                }
            }
        }
    }

    fun removeFromCart(productId: Long) {
        viewModelScope.launch {
            try {
                cartRepository.removeFromCart(productId)
                val updatedItems = cartRepository.getCartItems()
                updateCartState(updatedItems)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Failed to remove item from cart: ${e.message}")
                }
            }
        }
    }

    fun updateQuantity(productId: Long, quantity: Int) {
        viewModelScope.launch {
            try {
                cartRepository.updateQuantity(productId, quantity)
                val updatedItems = cartRepository.getCartItems()
                updateCartState(updatedItems)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Failed to update quantity: ${e.message}")
                }
            }
        }
    }

    private fun updateCartState(items: List<CartItem>) {
        val subtotal = items.sumOf { it.product.price * it.quantity }
        val tax = subtotal * 0.1
        val total = subtotal + tax

        _uiState.update {
            it.copy(
                items = items,
                subtotal = subtotal,
                tax = tax,
                total = total,
                isLoading = false,
                error = null
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

// State classes
data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val total: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)