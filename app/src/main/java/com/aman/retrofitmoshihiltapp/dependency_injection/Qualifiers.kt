package com.aman.retrofitmoshihiltapp.dependency_injection

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class BaseUrl

@Qualifier
annotation class AuthInterceptor

@Qualifier
annotation class TokenInterceptor

@Qualifier
annotation class LangInterceptor