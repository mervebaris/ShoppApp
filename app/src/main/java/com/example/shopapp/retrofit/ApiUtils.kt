package com.example.shopapp.retrofit


class ApiUtils {
    companion object{
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getProductsDao() : ProductsDao {
            return RetrofitClient.getClient(BASE_URL).create(ProductsDao::class.java)
        }

        fun getCartDao() : CartDao {
            return RetrofitClient.getClient(BASE_URL).create(CartDao::class.java)
        }
    }
}