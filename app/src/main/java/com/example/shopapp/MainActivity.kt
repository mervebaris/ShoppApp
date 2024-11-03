package com.example.shopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.uix.screens.BottomBarScreen
import com.example.shopapp.uix.viewmodel.CartViewModel
import com.example.shopapp.uix.viewmodel.DetailViewModel
import com.example.shopapp.uix.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val mainViewModel : MainViewModel by viewModels()
    val detailViewModel : DetailViewModel by viewModels()
    val cartViewModel : CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopAppTheme {
                BottomBarScreen(mainViewModel,detailViewModel,cartViewModel)
            }
        }
    }
}




