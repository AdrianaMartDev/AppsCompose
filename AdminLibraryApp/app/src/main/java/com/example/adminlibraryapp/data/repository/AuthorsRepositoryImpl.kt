package com.example.adminlibraryapp.data.repository

import com.example.adminlibraryapp.data.remote.AuthorService
import com.example.adminlibraryapp.data.remote.models.DataAuthor
import com.example.adminlibraryapp.domain.repository.AuthorsRepository
import javax.inject.Inject

class AuthorsRepositoryImpl @Inject constructor(
    private val api: AuthorService
) : BaseRepository(), AuthorsRepository {

    override suspend fun getAuthors() =
        safeApiCall {
            api.getAuthors()
        }

    override suspend fun addAuthor(author: DataAuthor) =
        safeApiCall {
            api.addAuthor(author)
        }

    override suspend fun deleteAuthor(author: DataAuthor) =
        safeApiCall {
            api.deleteAuthor(author)
        }

    override suspend fun updateAuthor(author: DataAuthor) =
        safeApiCall {
            api.updateAuthor(author)
        }

}
