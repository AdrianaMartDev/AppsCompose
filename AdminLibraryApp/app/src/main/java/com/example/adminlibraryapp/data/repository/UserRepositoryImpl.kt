package com.example.adminlibraryapp.data.repository

import com.example.adminlibraryapp.data.remote.UserService
import com.example.adminlibraryapp.data.remote.models.DataUsers
import com.example.adminlibraryapp.data.remote.response.UserResponse
import com.example.adminlibraryapp.domain.repository.Resource
import com.example.adminlibraryapp.domain.repository.UsersRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: UserService) : UsersRepository,
    BaseRepository() {

    override suspend fun getUsers(): Resource<UserResponse> =
        safeApiCall { api.getUsers() }

    override suspend fun addUser(user: DataUsers): Resource<UserResponse> =
        safeApiCall { api.addUser(user) }

    override suspend fun updateUser(user: DataUsers): Resource<UserResponse> =
        safeApiCall { api.updateUser(user) }

    override suspend fun deleteUser(user: DataUsers): Resource<UserResponse> =
        safeApiCall { api.deleteUser(user) }

}