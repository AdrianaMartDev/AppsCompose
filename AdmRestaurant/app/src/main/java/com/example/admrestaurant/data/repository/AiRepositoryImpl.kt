package com.example.admrestaurant.data.repository

import com.example.admrestaurant.data.remote.model.GeminiContent
import com.example.admrestaurant.data.remote.model.GeminiPart
import com.example.admrestaurant.data.remote.model.GeminiRequest
import com.example.admrestaurant.data.remote.service.GeminiService
import com.example.admrestaurant.domain.model.Dish
import com.example.admrestaurant.domain.repository.AiRepository
import com.example.admrestaurant.utils.safeApiCall
import javax.inject.Inject
import com.example.admrestaurant.BuildConfig

class AiRepositoryImpl @Inject constructor(
    private val service: GeminiService
) : AiRepository {

    override suspend fun generateDishDescription(dish: Dish): Result<String> {
        val prompt = buildPrompt(dish)

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(GeminiPart(text = prompt))
                )
            )
        )

        return safeApiCall {
            service.generateContent(
                apiKey = BuildConfig.GEMINI_API_KEY,
                request = request
            )
        }.mapCatching { response ->
            val text = response.candidates
                ?.firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text
                ?.trim()

            if (text.isNullOrEmpty()) {
                throw IllegalStateException("LA IA no devolvio una descripción.")
            }
            text
        }
    }

    private fun buildPrompt(dish: Dish): String {
        return """
            Eres un experto en marketing gastronómico para menús de restaurante.
            Escribe una descripción apetitosa, breve y atractiva para el siguiente platillo.
            Usa máximo 2 oraciones. No incluyas el precio. Responde solo con la descripción,
            sin comillas ni texto adicional.

            Nombre del platillo: ${dish.nameDish}
            Categoría: ${dish.category}
           """
    }
}