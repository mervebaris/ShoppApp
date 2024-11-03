package com.example.shopapp.uix.screens

import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.ui.theme.robotobold
import com.example.shopapp.uix.viewmodel.CartViewModel
import com.example.shopapp.uix.viewmodel.DetailViewModel
import com.example.shopapp.uix.viewmodel.MainViewModel


@Composable
fun BottomBarScreen(
    mainViewModel: MainViewModel,
    detailViewModel: DetailViewModel,
    cartViewModel: CartViewModel
) {
    val selectedItem = remember { mutableStateOf(0) }
    val screens = listOf("mainScreen", "cartScreen", "favoritesScreen", "settingsScreen")


    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                content = {
                    NavigationBarItem(
                        selected = selectedItem.value == 0,
                        onClick = { selectedItem.value = 0 },
                        icon = { Icon(painter = painterResource(id = R.drawable.main_icon), contentDescription = "") },
                        label = { Text(text = "Anasayfa", fontFamily = robotobold ) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(rgb(95, 111, 82)), // Renk değişikliği
                            unselectedIconColor = (Color.LightGray),
                            selectedTextColor = Color(rgb(95, 111, 82)), // Renk değişikliği
                            unselectedTextColor = (Color.LightGray),
                            indicatorColor = Color.Transparent
                        )
                    )

                    NavigationBarItem(
                        selected = selectedItem.value == 1,
                        onClick = { selectedItem.value = 1 },
                        icon = { Icon(painter = painterResource(id = R.drawable.cart_icon), contentDescription = "") },
                        label = { Text(text = "Sepet", fontFamily = robotobold ) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(rgb(95, 111, 82)), // Renk değişikliği
                            unselectedIconColor = (Color.LightGray),
                            selectedTextColor = Color(rgb(95, 111, 82)), // Renk değişikliği
                            unselectedTextColor = (Color.LightGray),
                            indicatorColor = Color.Transparent
                        )
                    )

                    NavigationBarItem(
                        selected = selectedItem.value == 2,
                        onClick = { selectedItem.value = 2 },
                        icon = { Icon(painter = painterResource(id = R.drawable.favorites_icon), contentDescription = "") },
                        label = { Text(text = "Favoriler", fontFamily = robotobold )},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(rgb(95, 111, 82)), // Renk değişikliği
                            unselectedIconColor = (Color.LightGray),
                            selectedTextColor = Color(rgb(95, 111, 82)), // Renk değişikliği
                            unselectedTextColor = (Color.LightGray),
                            indicatorColor = Color.Transparent
                        )
                    )

                    NavigationBarItem(
                        selected = selectedItem.value == 3,
                        onClick = { selectedItem.value = 3 },
                        icon = { Icon(painter = painterResource(id = R.drawable.settings_icon), contentDescription = "") },
                        label = { Text(text = "Ayarlar", fontFamily = robotobold ) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(rgb(95, 111, 82)), // Renk değişikliği
                            unselectedIconColor =(Color.LightGray),
                            selectedTextColor = Color(rgb(95, 111, 82)), // Renk değişikliği
                            unselectedTextColor = (Color.LightGray),
                            indicatorColor = Color.Transparent,

                        )
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenTransitions(
                selectedScreen = screens[selectedItem.value],
                mainViewModel = mainViewModel,
                detailViewModel = detailViewModel,
                cartViewModel = cartViewModel
            )
        }
    }
}
