package com.example.admrestaurant.domain

import com.example.admrestaurant.domain.model.Dish
import com.example.admrestaurant.domain.repository.AiRepository
import com.example.admrestaurant.domain.usecase.GenerateDishDescriptionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GenerateDishDescriptionUseCaseTest {

    // SUT
    private lateinit var useCase: GenerateDishDescriptionUseCase

    // Mock
    private val aiRepository: AiRepository = mockk()

    // Fixtures
    private val testDish = Dish(
        nameDish = "Tacos de Birria",
        category = "Platillos Principales"
    )

    @Before
    fun setUp() {
        useCase = GenerateDishDescriptionUseCase(aiRepository)
    }

    // Happy Path
    @Test
    fun `invoke returns success when repository returns description`() = runTest {
        //Given
        val expectedDescription = "Jugosos tacos de birria bañados en consome"
        coEvery { aiRepository.generateDishDescription(testDish) } returns
                Result.success(expectedDescription)

        //When
        val result = useCase(testDish)

        //Then
        assertTrue(result.isSuccess)
        assertEquals(expectedDescription, result.getOrNull())
    }

    @Test
    fun `invoke delegates correctly to repository with same dish`() = runTest {
        //Given
        coEvery { aiRepository.generateDishDescription(testDish) } returns
                Result.success("Descripción de prueba")

        //When
        useCase(testDish)

        //Then
        coVerify(exactly = 1) { aiRepository.generateDishDescription(testDish) }
    }

    // Error Path
    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        //Given
        val networkError = Exception("Sin conexión a internet")
        coEvery { aiRepository.generateDishDescription(testDish) } returns
                Result.failure(networkError)

        //When
        val result = useCase(testDish)

        //Then
        assertTrue(result.isFailure)
        assertEquals("Sin conexión a internet", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke propagates the exact exception from repository`() = runTest {
        //Given
        val specificError = IllegalStateException("La IA no devolvió una descripción.")
        coEvery { aiRepository.generateDishDescription(testDish) } returns
                Result.failure(specificError)

        //When
        val result = useCase(testDish)

        //Then
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }

}