package com.example.adminlibraryapp.data.remote

import com.example.adminlibraryapp.data.remote.models.DataCategories
import com.example.adminlibraryapp.data.remote.response.CategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryService {

    @GET("/categorias")
    suspend fun getCategories(): Response<CategoryResponse>

    @POST("/categorias/add")
    suspend fun addCategory(@Body category: DataCategories): Response<CategoryResponse>

    @POST("/categorias/update")
    suspend fun updateCategory(@Body category: DataCategories): Response<CategoryResponse>

    @POST("/categorias/delete")
    suspend fun deleteCategory(@Body category: DataCategories): Response<CategoryResponse>

}