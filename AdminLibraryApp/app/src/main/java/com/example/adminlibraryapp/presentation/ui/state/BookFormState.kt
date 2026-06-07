package com.example.adminlibraryapp.presentation.ui.state

data class BookFormState(
    val isbn: String = "",
    val bookName: String = "",
    val description: String = "",
    val publicationYear: String = "",
    val edition: String = "",
    val stock: String = "",
    val author: String = "Select author",
    val editorial: String = "Select editorial",
    val category: String = "Select category"
)