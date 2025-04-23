package com.aman.retrofitmoshihiltapp.dependency_injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkRepositoryModule {
    @Singleton
    @Binds
    abstract fun provideUserRepository(
        userRepositoryImpl: NetworkApiRepositoryImpl
    ): NetworkApiRepository



}