package com.aman.retrofitmoshihiltapp.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class UserResponse : ArrayList<UserResponseItem>()

@JsonClass(generateAdapter = true)
data class UserResponseItem(
    @Json(name = "email")
    val email: String? = "",
    @Json(name = "gender")
    val gender: String? = "",
    @Json(name = "id")
    val id: Int? = 0,
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "status")
    val status: String? = ""
)
