package com.example.admrestaurant.data.remote.service

import com.example.admrestaurant.data.remote.response.ResponseCategory
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryService {
    @GET("/categorias")
    suspend fun getCategories(): Response<ResponseCategory>

    @FormUrlEncoded
    @POST("/categorias/add")
    suspend fun addCategory(
        @Field("nom_categoria") nameCategory: String,
    ): Response<ResponseCategory>


    @DELETE("/categorias/delete/{nomCategoria}")
    suspend fun deleteCategory(
        @Path("nomCategoria") nameCategory: String,
    ): Response<ResponseCategory>
}

