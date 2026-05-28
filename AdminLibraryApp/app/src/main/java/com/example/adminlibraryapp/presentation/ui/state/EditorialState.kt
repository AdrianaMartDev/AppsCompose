package com.example.adminlibraryapp.presentation.ui.state

import com.example.adminlibraryapp.data.remote.models.DataEditorials

data class EditorialState(
    val loading: Boolean = false,
    val data: List<DataEditorials> = listOf(),
    val error: String = ""
)