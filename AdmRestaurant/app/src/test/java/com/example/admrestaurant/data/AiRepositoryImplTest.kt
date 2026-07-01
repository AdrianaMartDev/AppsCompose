package com.example.admrestaurant.data

import com.example.admrestaurant.data.remote.model.GeminiContent
import com.example.admrestaurant.data.remote.model.GeminiPart
import com.example.admrestaurant.data.remote.response.GeminiCandidate
import com.example.admrestaurant.data.remote.response.GeminiResponse
import com.example.admrestaurant.data.remote.service.GeminiService
import com.example.admrestaurant.data.repository.AiRepositoryImpl
import com.example.admrestaurant.domain.model.Dish
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AiRepositoryImplTest {

    //SUT
    private lateinit var repository: AiRepositoryImpl

    //MOCK
    private val geminiService: GeminiService = mockk()

    //Fixtures
    private val testDish = Dish(
        nameDish = "Enchiladas Verdes",
        category = "Platillos Principales"
    )

    @Before
    fun setUp() {
        repository = AiRepositoryImpl(geminiService)
    }

    // Happy path
    @Test
    fun generateDishDescription_returnsTrimmedText_whenApiSucceeds() = runTest {
        //Given
        val rawText = "  Enchiladas en salsa verde fresca, cubiertas de crema y queso.  "
        coEvery { geminiService.generateContent(any(), any()) } returns
                Response.success(buildGeminiResponse(rawText))

        //When
        val result = repository.generateDishDescription(testDish)

        //Then
        assert(result.isSuccess)
        assertEquals(rawText.trim(), result.getOrNull())
    }

    @Test
    fun generateDishDescription_includesDishNameAndCategory_inPrompt() = runTest {
        //Given
        val requestSlot = slot<com.example.admrestaurant.data.remote.model.GeminiRequest>()
        coEvery { geminiService.generateContent(any(), capture(requestSlot)) } returns
                Response.success(buildGeminiResponse("Descripción de prueba"))
        //When
        repository.generateDishDescription(testDish)

        //Then
        val promptText = requestSlot.captured.contents.first().parts.first().text
        assertTrue(promptText.contains(testDish.nameDish))
        assertTrue(promptText.contains(testDish.category))
    }

    //Error path
    @Test
    fun generateDishDescription_returnsFailure_whenApiReturnsEmptyTest() = runTest {
        //Given
        coEvery { geminiService.generateContent(any(), any()) } returns
                Response.success(buildGeminiResponse(""))
        //When
        val result = repository.generateDishDescription(testDish)

        //Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }

    @Test
    fun generateDishDescription_returnsFailure_whenCandidatesListIsEmpty() = runTest {
        //Given
        val emptyResponse = GeminiResponse(candidates = emptyList())
        coEvery { geminiService.generateContent(any(), any()) } returns
                Response.success(emptyResponse)
        //When
        val result = repository.generateDishDescription(testDish)

        //Then
        assertTrue(result.isFailure)
    }

    @Test
    fun generateDishDescription_returnsFailure_onNetworkError() = runTest {
        //Given
        coEvery { geminiService.generateContent(any(), any()) } throws
                Exception("Network error")
        //When
        val result = repository.generateDishDescription(testDish)

        //Then
        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    //Helpers
    fun buildGeminiResponse(text: String): GeminiResponse =
        GeminiResponse(
            candidates = listOf(
                GeminiCandidate(
                    content = GeminiContent(
                        parts = listOf(
                            GeminiPart(text = text)
                        )
                    )
                )
            )
        )
}
