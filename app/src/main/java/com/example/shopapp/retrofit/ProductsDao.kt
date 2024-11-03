package com.example.shopapp.retrofit

import com.example.shopapp.data.entity.ProductsResponse
import retrofit2.http.GET

interface ProductsDao {
    //http://kasimadalan.pe.hu/urunler/tumUrunleriGetir.php
    //http://kasimadalan.pe.hu/ -> base url
    //urunler/tumUrunleriGetir.php -> api url

    @GET("urunler/tumUrunleriGetir.php")
    suspend fun loadProducts() : ProductsResponse
}