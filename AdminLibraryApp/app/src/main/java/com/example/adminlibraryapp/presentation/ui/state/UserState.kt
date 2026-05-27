package com.example.adminlibraryapp.presentation.ui.state

import com.example.adminlibraryapp.data.remote.models.DataUsers

data class UserState(
    val loading: Boolean = false,
    val data: List<DataUsers> = emptyList(),
    val error: String? = null
)
