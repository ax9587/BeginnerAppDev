package com.example.beginnerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.beginnerapp.screens.CheckoutScreen
import com.example.beginnerapp.screens.Greeting
import com.example.beginnerapp.screens.Login
import com.example.beginnerapp.screens.ProductsListScreen
import com.example.beginnerapp.screens.ShoppingCartScreen

@Composable
fun NavGraph(navController:NavHostController){
    NavHost(navController=navController, startDestination = Routes.ProductLists.routes) {
        composable(Routes.Greeting.routes){
            Greeting(navController)
        }
        composable(Routes.Login.routes){
            Login(navController)
        }
        composable(Routes.ProductLists.routes){
            ProductsListScreen(
                navController,
                onCartClick = {
                    navController.navigate((Routes.ShoppingCart.routes))
                }
            )
        }
        composable(Routes.ShoppingCart.routes){
            ShoppingCartScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onCheckoutClick = {
                    navController.navigate(Routes.Checkout.routes)
                }
            )
        }
        composable(Routes.Checkout.routes) {
            CheckoutScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onCheckoutComplete = {
                    navController.navigate(Routes.ProductLists.routes) {
                        popUpTo(Routes.ProductLists.routes) { inclusive = true }
                    }
                }
            )
        }

    }
}
