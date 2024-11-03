package com.example.shopapp.uix.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shopapp.data.entity.Products
import com.example.shopapp.uix.viewmodel.CartViewModel
import com.example.shopapp.uix.viewmodel.DetailViewModel
import com.example.shopapp.uix.viewmodel.MainViewModel
import com.google.gson.Gson

@Composable
fun ScreenTransitions(
    selectedScreen: String,
    mainViewModel: MainViewModel,
    detailViewModel: DetailViewModel,
    cartViewModel: CartViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = selectedScreen) {

        composable("mainScreen") {
            MainScreen(navController = navController, mainViewModel = mainViewModel)
        }

        composable(
            "detailScreen/{product}",
            arguments = listOf(
                navArgument("product") { type = NavType.StringType }
            )
        ) {
            val json = it.arguments?.getString("product")
            val product = Gson().fromJson(json, Products::class.java)

            DetailScreen(product, navController = navController, detailViewModel = detailViewModel)
        }

        composable("cartScreen") {
            CartScreen(cartViewModel = cartViewModel)
        }

        composable("favoritesScreen") {
            FavoritesScreen()
        }

        composable("settingsScreen") {
            SettingsScreen()
        }
    }
}
