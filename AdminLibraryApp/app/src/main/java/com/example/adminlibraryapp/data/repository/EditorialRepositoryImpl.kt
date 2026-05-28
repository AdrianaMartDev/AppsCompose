package com.example.adminlibraryapp.data.repository

import com.example.adminlibraryapp.data.remote.EditorialService
import com.example.adminlibraryapp.data.remote.models.DataEditorials
import com.example.adminlibraryapp.data.remote.response.EditorialResponse
import com.example.adminlibraryapp.domain.repository.EditorialRepository
import com.example.adminlibraryapp.domain.repository.Resource
import javax.inject.Inject

class EditorialRepositoryImpl @Inject constructor(private val editorialService: EditorialService) :
    EditorialRepository, BaseRepository() {

    override suspend fun getEditorial(): Resource<EditorialResponse> =
        safeApiCall { editorialService.getEditorials() }

    override suspend fun addEditorial(editorial: DataEditorials): Resource<EditorialResponse> =
        safeApiCall { editorialService.addEditorial(editorial) }

    override suspend fun updateEditorial(editorial: DataEditorials): Resource<EditorialResponse> =
        safeApiCall { editorialService.updateEditorial(editorial) }

    override suspend fun deleteEditorial(editorial: DataEditorials): Resource<EditorialResponse> =
        safeApiCall { editorialService.deleteEditorial(editorial) }

}