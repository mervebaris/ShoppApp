package com.example.shopapp.data.datasource

import com.example.shopapp.data.entity.Carts
import com.example.shopapp.retrofit.CartDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartDataSource(private val cartDao: CartDao) {
    val shippingCost = 100.0

    // Load Cart
    suspend fun loadCart(userName: String): List<Carts> = withContext(Dispatchers.IO) {
        try {
            return@withContext cartDao.loadCart(userName).urunler_sepeti
        } catch (e: Exception) {
            return@withContext listOf()
        }
    }

    // Add Cart
    suspend fun addProductToCart(
        name: String,
        image: String,
        category: String,
        price: Int,
        brand: String,
        orderQuantity: Int,
        userName: String
    ) = withContext(Dispatchers.IO) {
        // Sepet ürünlerini yükle
        val cartProducts = loadCart(userName)

        // Aynı isimde ürün olup olmadığını kontrol et
        val existingProduct = cartProducts.find { it.ad == name }

        if (existingProduct != null) {
            // Ürün zaten sepette var, adet güncellenmeli
            val updatedQuantity = existingProduct.siparisAdeti + orderQuantity

            // Mevcut ürünü sil (id'sine göre)
            deleteProductFromCart(existingProduct.sepetId, userName)

            // Güncellenmiş adet ile ürünü tekrar ekle
            cartDao.addProductToCart(name, image, category, price, brand, updatedQuantity, userName)
        } else {
            // Ürün sepette yok, yeni kayıt ekleniyor
            cartDao.addProductToCart(name, image, category, price, brand, orderQuantity, userName)
        }
    }

    // Chart Delete
    suspend fun deleteProductFromCart(cartId: Int, userName: String) {
        cartDao.deleteProductFromCart(cartId, userName)
    }

    // Cart Subtotal
    suspend fun calculateSubtotal(userName: String): Double {
        val cartProducts = loadCart(userName)
        var subtotal = 0.0
        for (cartProduct in cartProducts) {
            val orderQuantity = cartProduct.siparisAdeti
            val price = cartProduct.fiyat
            subtotal += orderQuantity * price
        }
        return subtotal
    }

    // Cart Total
    suspend fun calculateTotalPriceWithShipping(userName: String): Double {
        val subtotal = calculateSubtotal(userName)
        return subtotal + shippingCost
    }
}




/* // Add product to cart
    suspend fun addProductToCart(
        name: String,
        image: String,
        category: String,
        price: Int,
        brand: String,
        orderQuantity: Int,
        userName: String
    ) = withContext(Dispatchers.IO) {
        val cartProducts = loadCart(userName)
        val existingProduct = cartProducts.find { it.ad == name }

        if (existingProduct != null) {
            // Ürün zaten sepette var, adet güncellenmeli
            val updatedQuantity = existingProduct.siparisAdeti + orderQuantity
            // Önce ürünü siliyoruz
            deleteProductFromCart(existingProduct.sepetId, userName)
            // Yeni adet ile tekrar ekliyoruz
            cartDao.addProductToCart(name, image, category, price, brand, updatedQuantity, userName)
        } else {
            // Ürün sepette yok, yeni kayıt ekleniyor
            cartDao.addProductToCart(name, image, category, price, brand, orderQuantity, userName)
        }
    }
*/


