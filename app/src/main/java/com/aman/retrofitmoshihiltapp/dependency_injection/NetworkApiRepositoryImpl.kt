package com.aman.retrofitmoshihiltapp.dependency_injection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOn

class NetworkApiRepositoryImpl  @Inject constructor(
    private val apiService: ApiService
) : NetworkApiRepository {

    override fun getApi(
        endPoint: String,
//        dataClass: Class<T>
    ) = flow {
       emit(UiState.Loading)
       emit(
           handleApiCall {
               apiService.hitGetApi(/*endPoint,*/ )
           }
       )
   }.flowOn(Dispatchers.IO)
}