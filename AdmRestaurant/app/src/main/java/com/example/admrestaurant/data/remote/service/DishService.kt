package com.example.admrestaurant.data.remote.service

import com.example.admrestaurant.data.remote.model.DataDish
import com.example.admrestaurant.data.remote.response.ResponseDish
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DishService {

    @GET("/platillos")
    suspend fun getDishes(): Response<ResponseDish>

    @POST("/platillos/add")
    suspend fun addDish(
        @Body dish: DataDish
    ): Response<ResponseDish>

    @PUT("/platillos/update/{namePlatillo}")
    suspend fun updateDish(
        @Path("namePlatillo") nameDish: String,
        @Body dish: DataDish
    ): Response<ResponseDish>


    @DELETE("/platillos/delete/{namePlatillo}")
    suspend fun deleteDish(
        @Path("namePlatillo") nameDish: String,
    ): Response<ResponseDish>

}