package com.example.adminlibraryapp.data.remote

import com.example.adminlibraryapp.data.remote.models.DataBooks
import com.example.adminlibraryapp.data.remote.response.BookResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BookService {

    @GET("/libros")
    suspend fun getBooks(): Response<BookResponse>

    @POST("/libros/add")
    suspend fun addBook(@Body book: DataBooks): Response<BookResponse>

    @POST("/libros/update")
    suspend fun updateBook(@Body book: DataBooks): Response<BookResponse>

    @POST("/libros/delete")
    suspend fun deleteBook(@Body book: DataBooks): Response<BookResponse>

}