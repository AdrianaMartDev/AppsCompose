package com.example.adminlibraryapp.presentation.ui.state

import com.example.adminlibraryapp.data.remote.models.DataAuthor
import com.example.adminlibraryapp.data.remote.models.DataBooks
import com.example.adminlibraryapp.data.remote.models.DataCategories
import com.example.adminlibraryapp.data.remote.models.DataEditorials
import com.example.adminlibraryapp.data.remote.models.DataLoans

data class BookState(
    val isLoading: Boolean = false,
    val books: List<DataBooks> = emptyList(),
    val authors: List<DataAuthor> = emptyList(),
    val editorials: List<DataEditorials> = emptyList(),
    val categories: List<DataCategories> = emptyList(),
    val loans: List<DataLoans> = emptyList(),
    val error: String = ""
)