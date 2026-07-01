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
        //Given
        coEvery { dishRepository.getDishes() } returns
                Result.failure(Exception("Error al cargar las categorías"))


        viewModel.state.test {
            viewModel.processIntent(DishIntent.LoadDish)

            awaitItem() //isLoading = true (init)
            awaitItem() //isLoading = false (init)
            awaitItem() //isLoading = true (LoadDish intent)

            skipItems(1) // isLoading = true

            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals("Error al cargar las categorías", errorState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun loadData_setsError_whenCategoriesRequestFails() = runTest {
        coEvery { categoryRepository.getCategories() } returns
                Result.failure(Exception("Error al cargar las categorías"))

        viewModel.state.test {
            viewModel.processIntent(DishIntent.LoadDish)

            awaitItem() //isLoading = true (init)
            awaitItem() //isLoading = false (init)
            awaitItem() //isLoading = true (LoadDish intent)

            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals("Error al cargar las categorías", errorState.error)

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
            awaitItem() // isLoading = true
            awaitItem() // isLoading = false

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
            awaitItem() // isLoading = true
            awaitItem() // isLoading = false

            viewModel.processIntent(DishIntent.GenerateDescription(testDish))

            skipItems(1) // isGeneratingDescription = true

            val error = awaitItem()
            assertFalse(error.isGeneratingDescription)
            assertNull(error.generatedDescription)
            assertEquals("Cuota de Api excedida", error.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun generateDescription_clearsPreviousDescription_BeforeCallingUseCase() = runTest {
        //Given
        coEvery { generateDishDescriptionUseCase(testDish) } returns
                Result.success("Nueva descripcion")

        viewModel.state.test {
            awaitItem() // isLoading = true
            awaitItem() // isLoading = false

            viewModel.processIntent(DishIntent.GenerateDescription(testDish))
            val generatingState = awaitItem()
            assertNull(generatingState.generatedDescription)

            cancelAndIgnoreRemainingEvents()
        }
    }

    //ClearGenerationDescription
    @Test
    fun clearGeneratedDescription_intentSets_generatedDescription_toNull() = runTest {
        coEvery { generateDishDescriptionUseCase(testDish) } returns
                Result.success("Descripcion generada")

        viewModel.state.test {
            awaitItem() // isLoading = true
            awaitItem() // isLoading = false


            viewModel.processIntent(DishIntent.GenerateDescription(testDish))
            awaitItem() // isGeneratingDescription = true
            val withDescription = awaitItem()
            assertEquals("Descripcion generada", withDescription.generatedDescription)


            viewModel.processIntent(DishIntent.ClearGeneratedDescription)
            val clearedState = awaitItem()
            assertNull(clearedState.generatedDescription)

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