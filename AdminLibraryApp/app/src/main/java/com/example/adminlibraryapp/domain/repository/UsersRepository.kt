package com.example.adminlibraryapp.domain.repository

import com.example.adminlibraryapp.data.remote.models.DataUsers
import com.example.adminlibraryapp.data.remote.response.UserResponse

interface UsersRepository {

    suspend fun getUsers(): Resource<UserResponse>

    suspend fun addUser(user: DataUsers): Resource<UserResponse>

    suspend fun updateUser(user: DataUsers): Resource<UserResponse>

    suspend fun deleteUser(user: DataUsers): Resource<UserResponse>

}