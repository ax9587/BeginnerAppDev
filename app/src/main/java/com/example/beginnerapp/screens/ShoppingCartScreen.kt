package com.example.beginnerapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.beginnerapp.viewmodels.ShoppingCartViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beginnerapp.component.CartSummary
import com.example.beginnerapp.component.ShoppingCart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShoppingCartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ShoppingCart(
                cartItems = uiState.items,
                onUpdateQuantity = { product, quantity ->
                    viewModel.updateQuantity(product.id, quantity)
                },
                onRemoveItem = { product ->
                    viewModel.removeFromCart(product.id)
                },
                onCheckout = onCheckoutClick,
                modifier = Modifier.weight(1f)
            )

            if (uiState.items.isNotEmpty()) {
                CartSummary(
                    cartItems = uiState.items,
                    onCheckout=onCheckoutClick
                )
            }
        }
    }
}
