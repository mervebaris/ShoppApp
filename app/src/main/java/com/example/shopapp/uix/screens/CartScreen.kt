package com.example.shopapp.uix.screens

import android.graphics.Color.rgb
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.ui.theme.robotobold
import com.example.shopapp.ui.theme.robotomedium
import com.example.shopapp.ui.theme.robotoregular
import com.example.shopapp.uix.viewmodel.CartViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartViewModel: CartViewModel) {
    val cartProductsList = cartViewModel.cartProductsList.observeAsState(listOf())
    val totalPrice = cartViewModel.totalPrice.observeAsState(0.0)
    val subtotal = cartViewModel.subtotal.observeAsState(0.0)
    val shippingCost = 100.0

    LaunchedEffect(key1 = true) {
        cartViewModel.loadCart(userName = "merve_baris")
        cartViewModel.calculateTotalPriceWithShipping(userName = "merve_baris")
        cartViewModel.calculateSubtotal(userName = "merve_baris")
    }

    Column {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Sepetim",
                        fontWeight = FontWeight.Bold,
                        color = Color(rgb(95, 111, 82))
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_icon),
                        contentDescription = "Back",
                        tint = Color(rgb(95, 111, 82))
                    )
                }
            }
        )

        if (cartProductsList.value.isEmpty()) {
            Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Sepetiniz boş",
                    fontSize = 16.sp,
                    fontFamily = robotobold,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
        } else {
            Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                LazyColumn(modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    items(cartProductsList.value.count()) { index ->
                        val cartProduct = cartProductsList.value[index]
                        Card(modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(0.5.dp, Color(rgb(231, 232, 216))),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White)
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    // Product Image
                                    Box(modifier = Modifier.size(70.dp)
                                    ) {
                                        val url = "http://kasimadalan.pe.hu/urunler/resimler/${cartProduct.resim}"
                                        GlideImage(imageModel = url,
                                            modifier = Modifier.fillMaxSize(),
                                            contentDescription = ""
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    // Column for Brand and Product Name
                                    Column(modifier = Modifier
                                            .weight(1f)
                                            .padding(2.dp)
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = cartProduct.ad,
                                            fontFamily = robotobold,
                                            fontSize = 16.sp,
                                            color = Color(rgb(95, 111, 82))
                                        )
                                        Text(text = cartProduct.marka,
                                            fontFamily = robotobold,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = Color(170, 179, 150)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    // Column for Quantity and Price
                                    Column(modifier = Modifier
                                            .weight(1f)
                                            .padding(2.dp)
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = "${cartProduct.fiyat} ₺",
                                            fontFamily = robotobold,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(170, 179, 150))
                                        Text(text = "${cartProduct.siparisAdeti} adet",
                                            fontFamily = robotobold,
                                            fontSize = 12.sp,
                                            color = Color.LightGray
                                        )
                                    }
                                }
                                // Delete Icon
                                IconButton(onClick = {
                                        cartViewModel.deleteProductFromCart(
                                            cartId = cartProduct.sepetId,
                                            userName = "merve_baris"
                                        )
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                ) {
                                    Icon(painter = painterResource(id = R.drawable.close_icon),
                                        contentDescription = "",
                                        tint = Color(rgb(95, 111, 82)),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Ara Toplam:",
                            fontSize = 16.sp,
                            fontFamily = robotomedium,
                            color = Color(rgb(95, 111, 82))
                        )
                        Text(text = "${subtotal.value} ₺",
                            fontSize = 16.sp,
                            fontFamily = robotomedium,
                            color = Color(rgb(95, 111, 82))
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Kargo Ücreti:",
                            fontSize = 16.sp,
                            fontFamily = robotomedium,
                            color = Color(rgb(95, 111, 82))
                        )
                        Text(text = "${shippingCost} ₺",
                            fontSize = 16.sp,
                            fontFamily = robotomedium,
                            color = Color(rgb(95, 111, 82))
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Toplam Tutar:",
                            fontSize = 16.sp,
                            fontFamily = robotobold,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(rgb(95, 111, 82))
                        )
                        Text(text = "${totalPrice.value} ₺",
                            fontSize = 16.sp,
                            fontFamily = robotobold,
                            fontWeight = FontWeight.Bold,
                            color = Color(rgb(95, 111, 82))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = { /* Navigate to payment screen */ },
                        modifier = Modifier
                            .height(40.dp)
                            .width(120.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(rgb(95, 111, 82))
                        )
                    ) {
                        Text(text = "Ödeme Yap",
                            fontSize = 14.sp,
                            fontFamily = robotobold,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}



/*val shippingCost = 100.0 // Kargo ücreti
    val total = totalPrice.value + shippingCost //total*/