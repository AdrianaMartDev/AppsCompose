package com.example.adminlibraryapp.domain.repository

import com.example.adminlibraryapp.data.remote.models.DataAdminUser
import com.example.adminlibraryapp.data.remote.response.AdminUserResponse

interface LoginRepository {

    suspend fun login(user: DataAdminUser): Resource<AdminUserResponse>
}