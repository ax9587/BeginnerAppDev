package com.example.beginnerapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beginnerapp.model.Product
import com.example.beginnerapp.network.dto.ProductsResponse
import com.example.beginnerapp.network.dto.toProductModel
import com.example.beginnerapp.network.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel@Inject constructor(
    private val http: ApiService,
    @ApplicationContext private val context: Context,
) : ViewModel(){

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init{
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = http.fetchProducts()
                print("DEBUG-The ProductsAPIresponse:${response}")
                _uiState.value = UiState.Success
                _products.value = response.map{
                    p->p.toProductModel()
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun onAddToCart(product: Product){

    }
}
sealed class UiState {
    object Initial : UiState()
    object Loading : UiState()
    object Success: UiState()
    data class Error(val message: String) : UiState()
}