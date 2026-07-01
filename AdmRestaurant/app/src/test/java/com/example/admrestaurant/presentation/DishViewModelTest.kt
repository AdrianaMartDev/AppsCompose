package com.example.admrestaurant.presentation


import com.example.admrestaurant.domain.model.Category
import com.example.admrestaurant.domain.model.Dish
import com.example.admrestaurant.domain.repository.CategoryRepository
import com.example.admrestaurant.domain.repository.DishRepository
import com.example.admrestaurant.domain.usecase.GenerateDishDescriptionUseCase
import com.example.admrestaurant.presentation.ui.dish.DishViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import app.cash.turbine.test
import com.example.admrestaurant.presentation.ui.dish.DishIntent
import com.example.admrestaurant.presentation.ui.dish.DishState
import io.mockk.coVerify
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertNotNull
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class DishViewModelTest {
    //SUT
    private lateinit var viewModel: DishViewModel

    //MOCK
    private val dishRepository: DishRepository = mockk()
    private val categoryRepository: CategoryRepository = mockk()
    private val generateDishDescriptionUseCase: GenerateDishDescriptionUseCase = mockk()

    //Fixtures
    private val testDish = Dish(
        nameDish = "Pozole rojo",
        category = "Caldos"
    )
    private val testDishes = listOf(testDish)
    private val testCategories = listOf(Category(nameCategory = "Caldos"))

    //Setup
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        coEvery { dishRepository.getDishes() } returns Result.success(testDishes)
        coEvery { categoryRepository.getCategories() } returns Result.success(testCategories)
        viewModel =
            DishViewModel(dishRepository, categoryRepository, generateDishDescriptionUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //Load Data (init)
    @Test
    fun initialState_loadsDishesAndCategories_successfully() = runTest {
        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)
            assertEquals(testDishes, loadedState.dishes)
            assertEquals(testCategories, loadedState.categories)
            assertNull(loadedState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun loadData_setsError_whenDishesRequestFails() = runTest {
        coEvery { dishRepository.getDishes() } returns
                Result.failure(Exception("Error al cargar platillos"))

        val viewModelWithError = DishViewModel(
            dishRepository,
            categoryRepository,
            generateDishDescriptionUseCase
        )

        advanceUntilIdle()

        viewModelWithError.state.test {
            val errorState = awaitItem() // isLoading = false con error
            assertFalse(errorState.isLoading)
            assertEquals("Error al cargar platillos", errorState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun loadData_setsError_whenCategoriesRequestFails() = runTest {
        coEvery { categoryRepository.getCategories() } returns
                Result.failure(Exception("Error al cargar las categorias"))

        val viewModelWithError = DishViewModel(
            dishRepository,
            categoryRepository,
            generateDishDescriptionUseCase
        )

        advanceUntilIdle()

        viewModelWithError.state.test {
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals("Error al cargar las categorias", errorState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    //Generate Description
    @Test
    fun generateDescription_intentSets_generatedDescription_onSuccess() = runTest {
        val expectedDescription = "Un pozole rojo intenso, lleno de sabor tradicional."
        coEvery { generateDishDescriptionUseCase(testDish) } returns
                Result.success(expectedDescription)

        viewModel.state.test {
            consumeInitStates()

            viewModel.processIntent(DishIntent.GenerateDescription(testDish))

            // Estado intermedio: generando descripción
            val generatingState = awaitItem()
            assertTrue(generatingState.isGeneratingDescription)
            assertNull(generatingState.generatedDescription)

            // Estado final: descripción lista
            val successState = awaitItem()
            assertFalse(successState.isGeneratingDescription)
            assertEquals(expectedDescription, successState.generatedDescription)
            assertNull(successState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun generateDescription_IntentSets_errorOnFailure() = runTest {
        //Given
        coEvery { generateDishDescriptionUseCase(testDish) } returns
                Result.failure(Exception("Cuota de Api excedida"))

        viewModel.state.test {
            consumeInitStates()

            viewModel.processIntent(DishIntent.GenerateDescription(testDish))

            skipItems(1) // isGeneratingDescription = true

            val error = awaitItem()
            assertFalse(error.isGeneratingDescription)
            assertNull(error.generatedDescription)
            assertEquals("Cuota de Api excedida", error.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    //ClearGenerationDescription
    @Test
    fun clearGeneratedDescription_intentSets_generatedDescription_toNull() = runTest {
        val description = "Descripcion generada"
        coEvery { generateDishDescriptionUseCase(testDish) } returns
                Result.success(description)

        viewModel.state.test {
            consumeInitStates()

            viewModel.processIntent(DishIntent.GenerateDescription(testDish))
            awaitItem() // isGeneratingDescription = true
            val withDescription = awaitItem()
            assertEquals(description, withDescription.generatedDescription)

            viewModel.processIntent(DishIntent.ClearGeneratedDescription)
            val clearedState = awaitItem()
            assertNull(clearedState.generatedDescription)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // AddDish
    @Test
    fun addDish_intentReloadsData_onSuccess() = runTest {
        coEvery { dishRepository.addDish(testDish) } returns
                Result.success(testDishes)

        viewModel.state.test {
            consumeInitStates()

            viewModel.processIntent(DishIntent.AddDish(testDish))

            awaitItem()
            awaitItem()
            //getDishes should be to call 2 times: init+reload after addDish
            coVerify(atLeast = 2) { dishRepository.getDishes() }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun addDish_intentSets_error_onFailure() = runTest {
        coEvery { dishRepository.addDish(testDish) } returns
                Result.failure(Exception("No se pudo guardar el platillo"))

        viewModel.state.test {
            consumeInitStates()

            viewModel.processIntent(DishIntent.AddDish(testDish))

            awaitItem() // isLoading = true

            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertNotNull(errorState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    //Delete
    @Test
    fun deleteDish_intentReloadsData_onSuccess() = runTest {
        coEvery { dishRepository.deleteDish(testDish.nameDish) } returns Result.success(testDishes)

        viewModel.state.test {
            consumeInitStates()

            viewModel.processIntent(DishIntent.DeleteDish(testDish.nameDish))

            awaitItem() // isLoading = true
            awaitItem() // isLoading = false, datos recargados

            coVerify(atLeast = 2) { dishRepository.getDishes() }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun deleteDish_intentSets_error_onFailure() = runTest {
        coEvery { dishRepository.deleteDish(testDish.nameDish) } returns
                Result.failure(Exception("El platillo no existe"))

        viewModel.state.test {
            consumeInitStates()

            viewModel.processIntent(DishIntent.DeleteDish(testDish.nameDish))

            awaitItem() // isLoading = true

            val errorState = awaitItem()
            assertEquals("El platillo no existe", errorState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    //Helpers
    //1) isLoading = true
    //2) isLoading = false, dishes and categories are loaded
    private suspend fun app.cash.turbine.TurbineTestContext<DishState>.consumeInitStates() {
        awaitItem()
        awaitItem()
    }
}