package com.aman.retrofitmoshihiltapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.retrofitmoshihiltapp.dependency_injection.NetworkApiRepository
import com.aman.retrofitmoshihiltapp.dependency_injection.UiState
import com.aman.retrofitmoshihiltapp.models.UserResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn

@HiltViewModel
class GetApiViewModel @Inject constructor(
    private val repository: NetworkApiRepository
) : ViewModel() {
    private val _uiStateUserResponse = MutableSharedFlow<UiState<UserResponseItem>>(replay = 1)
    val uiStateUserResponse: SharedFlow<UiState<UserResponseItem>> = _uiStateUserResponse.asSharedFlow()


    fun fetchUserList() = repository.getApi("users",
        null,
        UserResponseItem::class.java)
        .catch {
        _uiStateUserResponse.emit(
            UiState.Error(
                exception = it,
                errorMessage = it.message.toString()
            )
        )
    }.onEach {
        _uiStateUserResponse.emit(it)
    }.launchIn(viewModelScope)
}