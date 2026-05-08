package com.example.adminlibraryapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlibraryapp.data.remote.models.DataAdminUser
import com.example.adminlibraryapp.domain.repository.LoginRepository
import com.example.adminlibraryapp.domain.repository.Resource
import com.example.adminlibraryapp.presentation.ui.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _adminUserResp = MutableStateFlow(LoginState())
    val adminUserResp = _adminUserResp.asStateFlow()

    fun login(userId: String, password: String) {
        viewModelScope.launch {
            _adminUserResp.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.login(DataAdminUser(userId, "", password))) {
                is Resource.Success -> {
                    _adminUserResp.update {
                        it.copy(
                            isLoading = false,
                            data = result.data,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _adminUserResp.update {
                        it.copy(
                            isLoading = false,
                            data = null,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun logout() {
        _adminUserResp.value = LoginState()
    }

}