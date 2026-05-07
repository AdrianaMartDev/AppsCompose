package com.example.adminlibraryapp.presentation.ui.home

import com.example.adminlibraryapp.data.remote.response.AdminUserResponse

data class LoginState(
    val isLoading: Boolean = false,
    var data: AdminUserResponse? = null,
    val error: String? = null
)