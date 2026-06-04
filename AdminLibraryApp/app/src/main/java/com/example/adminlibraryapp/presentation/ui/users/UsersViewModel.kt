package com.example.adminlibraryapp.presentation.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlibraryapp.data.remote.models.DataUsers
import com.example.adminlibraryapp.domain.repository.Resource
import com.example.adminlibraryapp.domain.repository.UsersRepository
import com.example.adminlibraryapp.presentation.ui.state.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersRepository
) : ViewModel() {

    private var _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            loading()
            when (val result = repository.getUsers()) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        loading = false,
                        data = result.data.data,
                        error = null
                    )
                }

                is Resource.Error -> {
                    error(result.message)
                }
            }
        }
    }

    fun addUser(user: DataUsers) {
        viewModelScope.launch {
            loading()

            when (val result = repository.addUser(user)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        loading = false,
                        data = result.data.data,
                        error = null
                    )
                }

                is Resource.Error -> {
                    error(result.message)
                }
            }
        }
    }

    fun updateUser(user: DataUsers) {
        viewModelScope.launch {
            loading()
            when (val result = repository.updateUser(user)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        loading = false,
                        data = result.data.data,
                        error = null
                    )
                }

                is Resource.Error -> {
                    error(result.message)
                }
            }
        }
    }

    fun deleteUser(user: DataUsers) {
        viewModelScope.launch {
            loading()
            when (val result = repository.deleteUser(user)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        loading = false,
                        data = result.data.data,
                        error = null
                    )
                }

                is Resource.Error -> {
                    error(result.message)
                }
            }
        }
    }

    private fun loading() {
        _state.value = state.value.copy(
            loading = true
        )
    }

    private fun error(message: String) {
        _state.value = state.value.copy(
            loading = false,
            error = message
        )
    }

}