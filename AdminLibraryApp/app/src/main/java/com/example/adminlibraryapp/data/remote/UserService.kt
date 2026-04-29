package com.example.adminlibraryapp.data.remote

import com.example.adminlibraryapp.data.remote.models.DataAdminUser
import com.example.adminlibraryapp.data.remote.models.DataUsers
import com.example.adminlibraryapp.data.remote.response.AdminUserResponse
import com.example.adminlibraryapp.data.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface UserService {

    @POST("/adminlogin")
    suspend fun login(@Body user: DataAdminUser): Response<AdminUserResponse>

    @GET("/users")
    suspend fun getUsers(): Response<UserResponse>

    @POST("/usuarios/add")
    suspend fun addUser(@Body user: DataUsers): Response<UserResponse>

    @POST("/usuarios/update")
    suspend fun updateUser(@Body user: DataUsers): Response<UserResponse>

    @POST("/usuarios/delete")
    suspend fun deleteUser(@Body user: DataUsers): Response<UserResponse>

}