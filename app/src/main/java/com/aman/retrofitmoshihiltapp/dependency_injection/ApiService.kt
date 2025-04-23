package com.aman.retrofitmoshihiltapp.dependency_injection

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @GET("{path}")
    suspend fun hitGetApi(
        @Path("path", encoded = true) path: String,
        @QueryMap login: Map<String, @JvmSuppressWildcards Any>?): Response
}