package com.example.shopapp.data.datasource

import com.example.shopapp.data.entity.Products
import com.example.shopapp.retrofit.ProductsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsDataSource(var productsDao : ProductsDao) {
    suspend fun loadProducts() : List<Products> = withContext(Dispatchers.IO){
        return@withContext productsDao.loadProducts().urunler
    }

    suspend fun searchProduct(searchWord: String): List<Products> = withContext(Dispatchers.IO) {
        val allProducts = productsDao.loadProducts().urunler

        return@withContext allProducts.filter {
            it.ad.contains(searchWord, ignoreCase = true)
        }
    }

    suspend fun sortProductsByPrice(ascending: Boolean): List<Products> = withContext(Dispatchers.IO) {
        val allProducts = productsDao.loadProducts().urunler
        return@withContext if (ascending) {
            allProducts.sortedBy { it.fiyat }
        } else {
            allProducts.sortedByDescending { it.fiyat }
        }
    }

    suspend fun filterProductsByCategory(category: String): List<Products> = withContext(Dispatchers.IO) {
        try {
            val allProducts = productsDao.loadProducts().urunler
            return@withContext if (category == "All" || category.isBlank()) {
                allProducts
            } else {
                allProducts.filter { it.kategori == category }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}