package com.example.shopapp.uix.screens

import android.graphics.Color.rgb
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.shopapp.R
import com.example.shopapp.data.entity.Products
import com.example.shopapp.ui.theme.robotobold
import com.example.shopapp.ui.theme.robotomedium
import com.example.shopapp.uix.viewmodel.DetailViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailScreen(
    product: Products,
    navController: NavController,
    detailViewModel: DetailViewModel
) {
    val orderQuantity = remember { mutableStateOf(1) }

    Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(rgb(231, 232, 216))),  // Changed background to a lighter shade
    ) {
        Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(painter = painterResource(id = R.drawable.arrow_back_icon),
                    contentDescription = "",
                    tint = Color(95, 111, 82)
                )
            }
            IconButton(onClick = { /* Kalp icon click event */ }) {
                Icon(painter = painterResource(id = R.drawable.heart_icon),
                    contentDescription = "",
                    tint = Color(95, 111, 82)
                )
            }
        }
        //Product Image
        Box(modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val url = "http://kasimadalan.pe.hu/urunler/resimler/${product.resim}"
            GlideImage(imageModel = url,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(16.dp)) // Köşeleri yuvarlatılmış resim
            )
        }
        Box(modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 1.dp)
            ) {
                Box(modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // First Row
                        Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = product.marka,
                                fontFamily = robotomedium,
                                fontSize = 15.sp,
                                color = Color(95, 111, 82),
                            )
                            Text(text = "₺${product.fiyat * 1.2}",
                                fontFamily = robotomedium,
                                color = Color.Gray,
                                fontSize = 16.sp,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        //Second Row
                        Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = product.ad,
                                fontFamily = robotobold,
                                fontSize = 22.sp,
                                color = Color(95, 111, 82),
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "₺${product.fiyat}",
                                fontFamily = robotobold,
                                color = Color(95, 111, 82),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        // Star Rating
                        Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Icon(painter = painterResource(id = R.drawable.star_icon),
                                contentDescription = null,
                                tint = Color(255, 215, 0),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(text = "4.6", fontFamily = robotobold, color = Color(95, 111, 82))
                        }
                        // Product Description
                        Text(text = "Apple bilgisayarlar, yüksek performansı, zarif tasarımları ve kullanıcı dostu macOS işletim sistemi ile tanınır.",
                            fontFamily = robotobold,
                            fontSize = 13.sp,
                            color = Color(170, 179, 150),
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        // Order Quantity and Add to Cart Button
                        Row(modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Quantity Adjustment
                            Box(modifier = Modifier
                                    .size(100.dp, 30.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        BorderStroke(1.dp, Color(rgb(231, 232, 216))),
                                        RoundedCornerShape(15.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = {
                                            if (orderQuantity.value > 1) {
                                                orderQuantity.value -= 1
                                            }
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Text(text = "-",
                                            fontFamily = robotobold,
                                            fontSize = 18.sp,
                                            color = Color(95, 111, 82)
                                        )
                                    }
                                    Text(text = "${orderQuantity.value}",
                                        fontFamily = robotobold,
                                        fontSize = 20.sp,
                                        color = Color(95, 111, 82)
                                    )
                                    IconButton(onClick = {
                                            orderQuantity.value += 1
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Text(text = "+",
                                            fontFamily = robotobold,
                                            fontSize = 18.sp,
                                            color = Color(95, 111, 82)
                                        )
                                    }
                                }
                            }
                            Row {
                                // Cart Add Button
                                Button(onClick = {
                                        detailViewModel.addProductToCart(
                                            name = product.ad,
                                            image = product.resim,
                                            category = product.kategori,
                                            price = product.fiyat,
                                            brand = product.marka,
                                            orderQuantity = orderQuantity.value,
                                            userName = "merve_baris"
                                        )
                                        navController.navigate("cartscreen")
                                    },
                                    modifier = Modifier.size(150.dp, 40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor =  Color(rgb(95, 111, 82)),
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(text = "Sepete Ekle",
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



