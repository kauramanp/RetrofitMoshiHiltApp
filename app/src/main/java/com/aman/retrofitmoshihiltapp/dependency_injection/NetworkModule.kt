package com.aman.retrofitmoshihiltapp.dependency_injection

import android.os.Build
import com.aman.retrofitmoshihiltapp.BuildConfig
import com.aman.retrofitmoshihiltapp.utils.Constants
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL


    @Singleton
    @Provides
    fun provideConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create(
            Moshi.Builder()
                .build()
        )

    @Singleton
    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @TokenInterceptor tokenInterceptor: Interceptor,
        @LangInterceptor langInterceptor: Interceptor,
        @Named("DeviceInfoInterceptor") deviceInfoInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder().apply {
                readTimeout(120, TimeUnit.SECONDS)
                connectTimeout(120, TimeUnit.SECONDS)
                writeTimeout(120, TimeUnit.SECONDS)
                addInterceptor(deviceInfoInterceptor)
                addInterceptor(langInterceptor)
                addInterceptor(tokenInterceptor)
                addInterceptor(httpLoggingInterceptor)
            }
            .build()
    }

    @Provides
    @Singleton
    @LangInterceptor
    fun provideLangInterceptor(
        appUserPreferences: AppUserPreferences
    ) =
        Interceptor {
            val request = it.request()
            it.proceed(
                when (request.method) {
                    "GET" -> request.addLangToGetRequest(appUserPreferences)
                    "POST" -> request.addLangToPostRequest(appUserPreferences)
                    else -> request
                }

            )
        }

    private fun Request.addLangToGetRequest(appUserPreferences: AppUserPreferences): Request {
        val url = this.url
         return this.newBuilder()
             .url(
                 url.newBuilder()
                     .addQueryParameter(
                         "X-localization",
                        "en"
                     )
                     .build()
             )
             .build()
    }

    private fun Request.addLangToPostRequest(appUserPreferences: AppUserPreferences): Request {
        return this.newBuilder().addHeader(
            "X-localization",
            "en"
        ).build()
    }

    @Provides
    @Singleton
    @TokenInterceptor
    fun providesAuthInterceptor(
        appUserPreferences: AppUserPreferences
    ) =
        Interceptor {
            val request = it.request()
            it.proceed(
                if (appUserPreferences.getBooleanValue(Constants.KEY_IS_LOGGED_IN)) {
                    request.newBuilder().addHeader("authorization", "Bearer Token").build()
                } else {
                    request
                }
            )
        }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }
    }

    @Singleton
    @Provides
    @Named("DeviceInfoInterceptor")
    fun providesDeviceInfoInterceptor(): Interceptor = Interceptor {
        val request = it.request()
        it.proceed(
                request.newBuilder().apply {
                    addHeader("os_type", "android")
                    addHeader("app_version_code", BuildConfig.VERSION_CODE.toString())
                    addHeader("app_version_name", BuildConfig.VERSION_NAME)
                    addHeader("os_version", Build.VERSION.SDK_INT.toString())
                    addHeader("phone_manufacturer", Build.MANUFACTURER)
                    addHeader("model", Build.MODEL)
                }.build()

        )
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}