package com.aman.retrofitmoshihiltapp.dependency_injection

import androidx.annotation.Keep
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Response
import retrofit2.HttpException
import java.net.UnknownHostException

class ApiUtils {
}

@Keep
data class ApiResponse<T>(
    val data: T? = null,
    val message: String?,
    val status: Int,
    val success: Boolean,
)

@Keep
class InvalidAuthorization: Exception()
@Keep
class NoInternetConnection: Exception()
@Keep
class JsonSyntaxException: Exception()

@Keep
sealed class UiState<out T> {

    data object Init : UiState<Nothing>()

    data object Loading : UiState<Nothing>()

    data object Pagination : UiState<Nothing>()

    data class Success<T>(
        val data: T?,
    ) : UiState<T>()

    data class Error(
        val exception: Throwable = Exception(),
        val errorMessage: String = "",
        val errorCode : Int = 0
    ) : UiState<Nothing>()

}


@Keep
suspend fun <T> handleApiCall(/*dataClass: Class<T>,*/ apiCall: suspend () -> ApiResponse<T>): UiState<T> {
    return try {
        val apiResponse = apiCall()
//        var doubleStatus = apiResponse.status is Double
        when (apiResponse.status) {
           /* 200 -> {
                val adapter: JsonAdapter<T> = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(dataClass)
               return UiState.Success(adapter.fromJson(apiResponse.body?.string() ?: ""))
            }*/
            401 -> UiState.Error(InvalidAuthorization(), apiResponse.message ?: "")
            else -> UiState.Error(InvalidAuthorization(), apiResponse.message ?: "")
           /* else -> {
                if(apiResponse.status is Double)
                    UiState.Error(Exception(), apiResponse.message ?: "", apiResponse.status.toInt())
                else {
                    UiState.Success(apiResponse.data)
                }
            }*/
        }
    } catch (exception: Exception) {
        if((exception as? HttpException)?.code() == 401) {
            UiState.Error(InvalidAuthorization(), exception.message ?: "")
        } else if((exception is JsonSyntaxException)){
            UiState.Error(exception, exception.message ?: "")
        } else if((exception is UnknownHostException)){
            UiState.Error(NoInternetConnection(), exception.message ?: "")
        } else {
            UiState.Error(exception, exception.message.toString())
        }
    }
}