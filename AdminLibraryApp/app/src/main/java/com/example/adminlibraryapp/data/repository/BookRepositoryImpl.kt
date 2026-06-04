package com.example.adminlibraryapp.data.repository

import com.example.adminlibraryapp.data.remote.BookService
import com.example.adminlibraryapp.data.remote.models.DataBooks
import com.example.adminlibraryapp.data.remote.response.BookResponse
import com.example.adminlibraryapp.domain.repository.BookRepository
import com.example.adminlibraryapp.domain.repository.Resource
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookService: BookService) : BookRepository,
    BaseRepository() {

    override suspend fun getBooks(): Resource<BookResponse> =
        safeApiCall { bookService.getBooks() }

    override suspend fun addBook(book: DataBooks): Resource<BookResponse> =
        safeApiCall { bookService.addBook(book) }

    override suspend fun updateBook(book: DataBooks): Resource<BookResponse> =
        safeApiCall { bookService.updateBook(book) }

    override suspend fun deleteBook(book: DataBooks): Resource<BookResponse> =
        safeApiCall { bookService.deleteBook(book) }

}