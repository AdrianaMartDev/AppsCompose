package com.example.adminlibraryapp.domain.repository

import com.example.adminlibraryapp.data.remote.models.DataEditorials
import com.example.adminlibraryapp.data.remote.response.EditorialResponse

interface EditorialRepository {

    suspend fun getEditorial(): Resource<EditorialResponse>

    suspend fun addEditorial(editorial: DataEditorials): Resource<EditorialResponse>

    suspend fun updateEditorial(editorial: DataEditorials): Resource<EditorialResponse>

    suspend fun deleteEditorial(editorial: DataEditorials): Resource<EditorialResponse>

}