package com.example.admrestaurant.utils

import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            }
                ?: Result.failure(Exception("Response body is null"))
        } else {
            val errorBody = response.errorBody()?.string()
            android.util.Log.e("API_DEBUG", "Error ${response.code()}: $errorBody")
            Result.failure(Exception("Request failed with code ${response.code()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}