package com.example.adminlibraryapp.data.remote

import com.example.adminlibraryapp.data.remote.models.DataEditorials
import com.example.adminlibraryapp.data.remote.response.EditorialResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EditorialService {

    @GET("/editoriales")
    suspend fun getEditorials(): Response<EditorialResponse>

    @POST("/editoriales/add")
    suspend fun addEditorial(@Body editorial: DataEditorials): Response<EditorialResponse>

    @POST("/editoriales/update")
    suspend fun updateEditorial(@Body editorial: DataEditorials): Response<EditorialResponse>

    @POST("/editoriales/delete")
    suspend fun deleteEditorial(@Body editorial: DataEditorials): Response<EditorialResponse>
}