package com.example.adminlibraryapp.data.repository

import com.example.adminlibraryapp.domain.repository.Resource
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        call: suspend () -> Response<T>
    ): Resource<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Response body is null")
            } else {
                Resource.Error("HTTP ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error $e")
        }
    }
}