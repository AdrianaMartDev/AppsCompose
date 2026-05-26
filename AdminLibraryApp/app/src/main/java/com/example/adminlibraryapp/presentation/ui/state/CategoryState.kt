package com.example.adminlibraryapp.presentation.ui.state

import com.example.adminlibraryapp.data.remote.models.DataCategories

data class CategoryState (
    val isLoading: Boolean? = false,
    val data: List<DataCategories> = emptyList(),
    val error: String? = null
)