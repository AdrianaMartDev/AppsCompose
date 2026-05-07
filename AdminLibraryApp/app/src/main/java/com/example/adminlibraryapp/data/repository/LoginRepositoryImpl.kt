package com.example.adminlibraryapp.data.repository

import com.example.adminlibraryapp.data.remote.UserService
import com.example.adminlibraryapp.data.remote.models.DataAdminUser
import com.example.adminlibraryapp.data.remote.response.AdminUserResponse
import com.example.adminlibraryapp.domain.repository.LoginRepository
import com.example.adminlibraryapp.domain.repository.Resource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: UserService
) : LoginRepository {

    override suspend fun login(user: DataAdminUser): Resource<AdminUserResponse> {
        return try {
            val response = api.login(user)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("HTTP ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error de red")
        }
    }
}