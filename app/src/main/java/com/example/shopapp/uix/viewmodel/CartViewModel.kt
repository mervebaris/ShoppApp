package com.example.shopapp.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopapp.data.entity.Carts
import com.example.shopapp.data.repo.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {

    var cartProductsList = MutableLiveData<List<Carts>>()
    var totalPrice = MutableLiveData<Double>()
    var subtotal = MutableLiveData<Double>()

    fun loadCart(userName: String) {
        CoroutineScope(Dispatchers.Main).launch  {
            cartProductsList.value = cartRepository.loadCart(userName)
        }
    }

    fun deleteProductFromCart(cartId: Int, userName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            cartRepository.deleteProductFromCart(cartId, userName)
            // Sepet güncellendikten sonra hesaplamalar tekrar yapıldı
            loadCart(userName)
            calculateTotalPriceWithShipping(userName)
            calculateSubtotal(userName)
        }
    }

    fun calculateSubtotal(userName: String) {
        CoroutineScope(Dispatchers.Main).launch  {
            subtotal.value = cartRepository.calculateSubtotal(userName)
        }
    }

    fun calculateTotalPriceWithShipping(userName: String) {
        CoroutineScope(Dispatchers.Main).launch  {
            totalPrice.value = cartRepository.calculateTotalPriceWithShipping(userName)
        }
    }
}
























/*
    try {
                cartProductsList.value = cartRepository.loadCart(userName)
            }catch (e:Exception){
                cartProductsList.value = listOf<Carts>()

            }
*/