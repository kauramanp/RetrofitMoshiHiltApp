package com.aman.retrofitmoshihiltapp.dependency_injection

import kotlinx.coroutines.flow.Flow

interface NetworkApiRepository {
    fun  <T> getApi(endPoint: String, queryParameters: HashMap<String, Any>?, dataClass: Class<T>): Flow<UiState<T>>

}