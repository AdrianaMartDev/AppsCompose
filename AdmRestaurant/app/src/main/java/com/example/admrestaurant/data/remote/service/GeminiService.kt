package com.example.admrestaurant.data.remote.service

import com.example.admrestaurant.data.remote.model.GeminiRequest
import com.example.admrestaurant.data.remote.response.GeminiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiService {

    @POST("v1beta/models/gemini-2.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>

}