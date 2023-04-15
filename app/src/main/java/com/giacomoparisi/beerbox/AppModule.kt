package com.giacomoparisi.beerbox

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.sync.Mutex

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun mutex(): Mutex = Mutex()

}