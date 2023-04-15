package com.giacomoparisi.data.repositories.beer

import com.giacomoparisi.data.datasource.network.getApiService
import com.giacomoparisi.data.repositories.beer.network.BeerApi
import com.giacomoparisi.domain.usecases.beer.BeerRepository
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BeerModule {

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): BeerApi =
        getApiService(moshi = moshi, baseUrl = "https://api.punkapi.com")

}

@Module
@InstallIn(SingletonComponent::class)
abstract class BeerBindModule {

    @Binds
    abstract fun bindRepository(repository: BeerRepositoryImpl): BeerRepository

}