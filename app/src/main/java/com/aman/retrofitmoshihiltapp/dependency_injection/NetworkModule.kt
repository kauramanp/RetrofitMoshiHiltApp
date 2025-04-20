package com.aman.retrofitmoshihiltapp.dependency_injection

import com.aman.retrofitmoshihiltapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL


    @Singleton
    @Provides
    fun setupRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

}