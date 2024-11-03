package com.example.shopapp.di


import com.example.shopapp.data.datasource.ProductsDataSource
import com.example.shopapp.data.repo.ProductsRepository
import com.example.shopapp.retrofit.ApiUtils
import com.example.shopapp.retrofit.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductsAppModule {
    @Provides
    @Singleton
    fun provideProductsRepository (productsDataSource:ProductsDataSource) : ProductsRepository {
        return  ProductsRepository(productsDataSource)
    }

    @Provides
    @Singleton
    fun provideProductsDataSource(productsDao:ProductsDao) : ProductsDataSource {
        return  ProductsDataSource(productsDao)
    }

    @Provides
    @Singleton
    fun provideProductsDao() : ProductsDao {
        return ApiUtils.getProductsDao()
    }
}
