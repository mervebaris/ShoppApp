package com.example.shopapp.data.repo

import com.example.shopapp.data.datasource.CartDataSource
import com.example.shopapp.data.entity.Carts

class CartRepository(var cartDataSource: CartDataSource)  {
    suspend fun loadCart(userName:String) : List<Carts> = cartDataSource.loadCart(userName)
    suspend fun addProductToCart(name:String,image:String,category:String,price:Int,brand:String,orderQuantity:Int,userName:String) = cartDataSource.addProductToCart(name,image,category,price,brand,orderQuantity,userName)
    suspend fun deleteProductFromCart(cartId:Int,userName:String) = cartDataSource.deleteProductFromCart(cartId,userName)
    suspend fun calculateSubtotal(userName: String) : Double = cartDataSource.calculateSubtotal(userName)
    suspend fun calculateTotalPriceWithShipping(userName: String) :Double = cartDataSource.calculateTotalPriceWithShipping(userName)
}
