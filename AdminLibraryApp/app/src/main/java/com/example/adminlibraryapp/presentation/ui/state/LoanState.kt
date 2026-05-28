package com.example.adminlibraryapp.presentation.ui.state

import com.example.adminlibraryapp.data.remote.models.DataLoans

data class LoanState(
    val loading: Boolean = false,
    val data: List<DataLoans> = listOf(),
    val message: String = ""
)