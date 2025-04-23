package com.aman.retrofitmoshihiltapp.dependency_injection

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkRepositoryModule {
    @Singleton
    @Binds
    abstract fun provideNetworkRepository(
        networkApiRepositoryImpl: NetworkApiRepositoryImpl
    ): NetworkApiRepository



}