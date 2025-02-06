package com.example.beginnerapp.screens

import com.example.beginnerapp.component.ProductCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.beginnerapp.viewmodels.ProductViewModel
import com.example.beginnerapp.viewmodels.ShoppingCartViewModel
import com.example.beginnerapp.viewmodels.UiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsListScreen(navController: NavHostController,
                       onCartClick: () -> Unit,
                       viewModel: ProductViewModel = hiltViewModel(),
                       viewModelCart: ShoppingCartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateCart by viewModelCart.uiState.collectAsState()
    val products by viewModel.products.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                actions = {
                    IconButton(onClick = onCartClick) {
                        BadgedBox(
                            badge = {
                                if (uiStateCart.items.isNotEmpty()) {
                                    Badge(
                                        modifier = Modifier.offset(x = -10.dp, y = (-2).dp)
                                    ) {
                                        Text(uiStateCart.items.size.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Cart"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            when (val state = uiState) {
                is UiState.Initial -> {
                    // Show initial state

                }
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                }
                is UiState.Success -> {
                    LazyColumn {
                        items(products ?: emptyList()) { product ->
                            //Text(text = product.name)
                            ProductCard(product=product, onAddToCart =viewModelCart::addToCart)
                            //:: function reference as lambda
                        }
                    }
                }
                is UiState.Error -> {
                    Text(
                        "Error: ${state.message}",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                }
            }





        }

    }


}