package com.example.shopapp.uix.screens


import android.graphics.Color.rgb
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.shopapp.R
import com.example.shopapp.ui.theme.robotobold
import com.example.shopapp.ui.theme.robotomedium
import com.example.shopapp.uix.viewmodel.MainViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel) {
    val productsList by mainViewModel.productsList.observeAsState(listOf())
    val categoriesList by mainViewModel.categoriesList.observeAsState(listOf())
    val searchProduct = rememberSaveable { mutableStateOf("") }
    val sortAscending = rememberSaveable { mutableStateOf(true) }
    val showDialog = remember { mutableStateOf(false) }
    val selectedCategory = rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        mainViewModel.loadProducts()
    }
    
    Column(modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Konum", color = Color(170, 179, 150))
                    Row(verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painter = painterResource(id = R.drawable.location_icon),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Color(rgb(95, 111, 82))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "İstanbul, TÜRKİYE",
                            fontFamily = robotomedium,
                            color = Color(rgb(95, 111, 82)))
                    }
                }
                Column {
                    Box(modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(rgb(231, 232, 216))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(painter = painterResource(id = R.drawable.notification_icon),
                            contentDescription = "",
                            modifier = Modifier.size(22.dp), // İkon boyutu
                            tint = Color(rgb(95, 111, 82)) // İkonun beyaz rengi
                        )
                    }
                }
            }
        }
        //Search Bar and Sorting Button
        Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = searchProduct.value,
                onValueChange = { searchQuery ->
                    searchProduct.value = searchQuery
                    mainViewModel.searchProduct(searchQuery)
                },
                label = { Text(text = "Ürün ara", fontFamily = robotobold, color = Color(170, 179, 150)) },
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(fontSize = 12.sp,fontFamily = robotobold, color = Color(170, 179, 150)),
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(rgb(231, 232, 216)),
                    unfocusedBorderColor = Color(rgb(231, 232, 216))
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Sort Button
            Box(modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(170, 179, 150))
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(painter = painterResource(id = R.drawable.filter_icon),
                        contentDescription = "Sıralama",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
            }
            // Popup Dialog
            if (showDialog.value) {
                Dialog(onDismissRequest = { showDialog.value = false }) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        ) {
                        Column(modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {

                            // Title and sort icon
                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Sıralama",
                                    fontSize = 22.sp,
                                    fontFamily = robotobold,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(rgb(95, 111, 82))
                                )
                                IconButton(onClick = { showDialog.value = false }) {
                                    Icon(imageVector = Icons.Default.Close,
                                        contentDescription = "",
                                        tint = Color(rgb(95, 111, 82))
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            // Sorting options by price
                            Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Increasing by price button
                                Button(
                                    onClick = {
                                        mainViewModel.sortProductsByPrice(ascending = true)
                                        sortAscending.value = true
                                        showDialog.value =
                                            false // Seçim yapıldıktan sonra diyalog kapanacak
                                    },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (sortAscending.value) Color(
                                            rgb(
                                                231, 232, 216
                                            )
                                        ) else Color.White, // Buton arka rengi
                                        contentColor = Color.Black // İçerik rengi
                                    )
                                ) {
                                    Text(text = "Fiyata Göre Artan",
                                        fontFamily = robotobold,
                                        fontSize = 16.sp,
                                        color = Color(rgb(95, 111, 82)) // Beyaz iç yazı
                                    )
                                }
                                // Decreasing button by price
                                Button(
                                    onClick = {
                                        mainViewModel.sortProductsByPrice(ascending = false)
                                        sortAscending.value = false
                                        showDialog.value = false
                                    },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (!sortAscending.value) Color(
                                            rgb(
                                                231, 232, 216
                                            )
                                        ) else Color.White,
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Text(text = "Fiyata Göre Azalan",
                                        fontFamily = robotobold,
                                        fontSize = 16.sp,
                                        color = Color(rgb(95, 111, 82)) // Beyaz iç yazı
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Categories Title
        Text(text = "Kategoriler", fontFamily = robotobold,
            fontSize = 24.sp,
            color = Color(rgb(95, 111, 82)),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 18.dp, bottom = 10.dp)
        )
        // Categories Chip
        LazyRow(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .clipToBounds() // İçeriği sınırlandırır ve kaydırmayı sağlar
        ) {
            // "All Products" button
            item {
                val isSelected = selectedCategory.value == "Tüm Ürünler"
                SuggestionChip(
                    onClick = {
                        selectedCategory.value = "Tüm Ürünler"
                        mainViewModel.loadProducts()
                    },
                    label = {
                        Text(text = "Tüm Ürünler",
                            fontFamily = robotobold,
                            color = if (isSelected) Color(170, 179, 150) else Color.White
                        )
                    },
                    border = BorderStroke(1.dp, Color(170, 179, 150)),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(32.dp),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = if (isSelected) Color.White else Color(170, 179, 150),
                        labelColor = if (isSelected) Color(170, 179, 150) else Color.White
                    )
                )
            }
            // Categories Chip
            items(categoriesList.size) { index ->
                val category = categoriesList[index]
                val isSelected = selectedCategory.value == category
                SuggestionChip(
                    onClick = {
                        selectedCategory.value = category
                        mainViewModel.filterProductsByCategory(category)
                    },
                    label = {
                        Text(text = category,
                            fontFamily = robotobold,
                            color = if (isSelected) Color(170, 179, 150) else Color.White
                        )
                    },
                    border = BorderStroke(1.dp, Color(170, 179, 150)),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(32.dp),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = if (isSelected) Color.White else Color(170, 179, 150),
                        labelColor = if (isSelected) Color(170, 179, 150) else Color.White
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Product List
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(productsList.size) { index ->
                val product = productsList[index]
                Card(modifier = Modifier
                        .padding(10.dp)
                        .size(width = 150.dp, height = 200.dp)
                        .clickable {
                            val productJson = Gson().toJson(product)
                            navController.navigate("detailScreen/$productJson")
                        },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        // Product Image
                        val url = "http://kasimadalan.pe.hu/urunler/resimler/${product.resim}"
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            GlideImage(imageModel = url,
                                modifier = Modifier
                                    .size(100.dp, 180.dp),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                        // Product Information
                        Column(modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Text(text = product.ad,
                                fontFamily = robotobold,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp,
                                color = Color(rgb(95, 111, 82)),
                                )
                            Text(text = product.marka,
                                fontFamily = robotomedium,
                                fontSize = 14.sp,
                                color = Color.LightGray,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        // Price and Add to Cart Button
                        Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "${product.fiyat} ₺",
                                fontFamily = robotobold,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(rgb(95, 111, 82)),
                            )
                            Box(modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        color = Color.White, shape = RoundedCornerShape(50)
                                    )
                                    .clickable {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("${product.ad} sepete eklendi")
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(rgb(231, 232, 216))),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(painter = painterResource(id = R.drawable.add_icon),
                                        contentDescription = "",
                                        modifier = Modifier.size(18.dp),
                                        tint = Color(rgb(95, 111, 82))
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
