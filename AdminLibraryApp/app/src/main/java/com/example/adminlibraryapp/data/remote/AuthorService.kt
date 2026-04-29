package com.example.adminlibraryapp.data.remote

import com.example.adminlibraryapp.data.remote.models.DataAuthor
import com.example.adminlibraryapp.data.remote.response.AuthorResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthorService {

    @GET("/autores")
    suspend fun getAuthors(): Response<AuthorResponse>

    @POST("/autores/add")
    suspend fun addAuthor(@Body author: DataAuthor): Response<AuthorResponse>

    @POST("/autores/update")
    suspend fun updateAuthor(@Body author: DataAuthor): Response<AuthorResponse>

    @POST("/autores/delete")
    suspend fun deleteAuthor(@Body author: DataAuthor): Response<AuthorResponse>

}