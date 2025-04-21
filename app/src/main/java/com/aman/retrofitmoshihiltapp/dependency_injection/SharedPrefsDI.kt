package com.aman.retrofitmoshihiltapp.dependency_injection

import android.content.Context
import android.content.SharedPreferences
import com.aman.retrofitmoshihiltapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsDI {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences =
        context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAppSharedPreferences(
        sharedPreferences: SharedPreferences
    ) = AppUserPreferences(sharedPreferences)


}