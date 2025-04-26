package com.aman.retrofitmoshihiltapp.dependency_injection

import com.aman.retrofitmoshihiltapp.models.UserResponseItem
import kotlinx.coroutines.flow.Flow

interface NetworkApiRepository {
    fun getApi(endPoint: String, /*queryParameters: HashMap<String, Any>?,*//* dataClass: Class<T>*/): Flow<UiState<List<UserResponseItem>>>

}