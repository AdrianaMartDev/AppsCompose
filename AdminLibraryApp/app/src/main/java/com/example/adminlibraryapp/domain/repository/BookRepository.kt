package com.example.adminlibraryapp.domain.repository

import com.example.adminlibraryapp.data.remote.models.DataBooks
import com.example.adminlibraryapp.data.remote.response.BookResponse

interface BookRepository {
    suspend fun getBooks(): Resource<BookResponse>
    suspend fun addBook(book: DataBooks): Resource<BookResponse>
    suspend fun updateBook(book: DataBooks): Resource<BookResponse>
    suspend fun deleteBook(book: DataBooks): Resource<BookResponse>
}