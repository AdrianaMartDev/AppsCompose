package com.example.admrestaurant.data.remote.response

import com.example.admrestaurant.data.remote.model.GeminiContent

data class GeminiResponse(
    val candidates: List<GeminiCandidate>
)

data class GeminiCandidate(
    val content: GeminiContent?
)