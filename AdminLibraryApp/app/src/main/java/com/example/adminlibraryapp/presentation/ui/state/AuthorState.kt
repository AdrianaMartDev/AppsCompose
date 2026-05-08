package com.example.adminlibraryapp.presentation.ui.state

import com.example.adminlibraryapp.data.remote.models.DataAuthor

data class AuthorState(
    val isLoading: Boolean = false,
    var data: List<DataAuthor> = emptyList(),
    val error: String? = null
)