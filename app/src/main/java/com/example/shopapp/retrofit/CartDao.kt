package com.example.shopapp.retrofit

import com.example.shopapp.data.entity.CartsResponse
import com.example.shopapp.data.entity.CrudResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CartDao {
    //http://kasimadalan.pe.hu/urunler/sepettekiUrunleriGetir.php
    //http://kasimadalan.pe.hu/ -> base url
    //urunler/sepettekiUrunleriGetir.php -> api url
    //urunler/sepeteUrunEkle.php -> api url
    //urunler/sepettenUrunSil.php -> api url

    @POST("urunler/sepettekiUrunleriGetir.php")
    @FormUrlEncoded
    suspend fun loadCart(@Field("kullaniciAdi") userName:String) : CartsResponse

    @POST("urunler/sepeteUrunEkle.php")
    @FormUrlEncoded
    suspend fun addProductToCart(@Field("ad") name:String,
                                 @Field("resim") image:String,
                                 @Field("kategori") category:String,
                                 @Field("fiyat") price:Int,
                                 @Field("marka") brand:String,
                                 @Field("siparisAdeti") orderQuantity:Int,
                                 @Field("kullaniciAdi") userName:String,
                                 ) : CrudResponse

    @POST("urunler/sepettenUrunSil.php")
    @FormUrlEncoded
    suspend fun deleteProductFromCart(@Field("sepetId") cartId:Int,
                                      @Field("kullaniciAdi") userName:String) : CrudResponse
}


