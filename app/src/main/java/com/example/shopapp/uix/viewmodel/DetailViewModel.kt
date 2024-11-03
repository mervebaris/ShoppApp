package com.example.shopapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shopapp.data.repo.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(var cartRepository:CartRepository) : ViewModel() {
    fun addProductToCart(name:String,image:String,category:String,price:Int,brand:String,orderQuantity:Int,userName:String){
        CoroutineScope(Dispatchers.Main).launch {
            cartRepository.addProductToCart(name,image,category,price,brand,orderQuantity,userName)
        }
    }
}