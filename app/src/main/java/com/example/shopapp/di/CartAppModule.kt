package com.example.shopapp.di

import com.example.shopapp.data.datasource.CartDataSource
import com.example.shopapp.data.repo.CartRepository
import com.example.shopapp.retrofit.ApiUtils
import com.example.shopapp.retrofit.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CartAppModule {
    @Provides
    @Singleton
    fun provideCartRepository (cartDataSource:CartDataSource) : CartRepository {
        return CartRepository(cartDataSource)
    }

    @Provides
    @Singleton
    fun provideCartDataSource(cartDao:CartDao) : CartDataSource {
        return CartDataSource(cartDao)
    }

    @Provides
    @Singleton
    fun provideCartDao() : CartDao {
        return ApiUtils.getCartDao()
    }
}
