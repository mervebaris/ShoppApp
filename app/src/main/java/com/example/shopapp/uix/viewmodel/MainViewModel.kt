package com.example.shopapp.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopapp.data.entity.Products
import com.example.shopapp.data.repo.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var productsRepository: ProductsRepository) : ViewModel() {
    var productsList = MutableLiveData<List<Products>>()
    var categoriesList = MutableLiveData<List<String>>()

    init {
        loadProducts()
        loadCategories()
    }

    fun loadProducts(){
        CoroutineScope(Dispatchers.Main).launch {
            productsList.value = productsRepository.loadProducts()
        }
    }

    fun searchProduct(searchWord:String){
        CoroutineScope(Dispatchers.Main).launch {
            productsList.value = productsRepository.searchProduct(searchWord)
        }
    }

    fun sortProductsByPrice(ascending: Boolean){
        CoroutineScope(Dispatchers.Main).launch {
            productsList.value = productsRepository.sortProductsByPrice(ascending)
        }
    }
    private fun loadCategories() {
        CoroutineScope(Dispatchers.Main).launch {
            val categories = productsRepository.loadProducts().map { it.kategori }.distinct()
            categoriesList.value = categories
        }
    }

    fun filterProductsByCategory(category: String) {
        CoroutineScope(Dispatchers.Main).launch {
            productsList.value = productsRepository.filterProductsByCategory(category)
        }
    }
}