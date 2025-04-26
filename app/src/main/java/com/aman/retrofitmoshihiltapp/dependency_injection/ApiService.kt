package com.aman.retrofitmoshihiltapp.dependency_injection

import com.aman.retrofitmoshihiltapp.models.UserResponseItem
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @GET("users")
    suspend fun hitGetApi(
        /*@Path("path", encoded = true) path: String*/): ApiResponse<List<UserResponseItem>>
}