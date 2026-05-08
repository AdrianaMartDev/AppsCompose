package com.example.adminlibraryapp.domain.repository

import com.example.adminlibraryapp.data.remote.models.DataAuthor
import com.example.adminlibraryapp.data.remote.response.AuthorResponse

interface AuthorsRepository {

    suspend fun getAuthors(): Resource<AuthorResponse>

    suspend fun addAuthor(author: DataAuthor): Resource<AuthorResponse>

    suspend fun deleteAuthor(author: DataAuthor): Resource<AuthorResponse>

    suspend fun updateAuthor(author: DataAuthor): Resource<AuthorResponse>

}