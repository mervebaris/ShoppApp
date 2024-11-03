package com.example.shopapp.data.repo

import com.example.shopapp.data.datasource.ProductsDataSource
import com.example.shopapp.data.entity.Products

class ProductsRepository(var productsDataSource: ProductsDataSource)  {
    suspend fun loadProducts() : List<Products> = productsDataSource.loadProducts()
    suspend fun searchProduct(searchWord: String) : List<Products> = productsDataSource.searchProduct(searchWord)
    suspend fun sortProductsByPrice(ascending: Boolean) : List<Products> = productsDataSource.sortProductsByPrice(ascending)
    suspend fun filterProductsByCategory(category: String) : List<Products> = productsDataSource.filterProductsByCategory(category)
}