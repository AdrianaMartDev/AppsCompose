package com.example.admrestaurant.presentation.ui.dish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.admrestaurant.domain.model.Dish
import com.example.admrestaurant.domain.repository.CategoryRepository
import com.example.admrestaurant.domain.repository.DishRepository
import com.example.admrestaurant.domain.usecase.GenerateDishDescriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishViewModel @Inject constructor(
    private val dishRepository: DishRepository,
    private val categoryRepository: CategoryRepository,
    private val generateDishDescriptionUseCase: GenerateDishDescriptionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DishState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun processIntent(intent: DishIntent) {
        when (intent) {
            is DishIntent.LoadDish -> loadData()
            is DishIntent.AddDish -> addDish(intent.dish)
            is DishIntent.UpdateDish -> updateDish(intent.nameDish, intent.dish)
            is DishIntent.DeleteDish -> deleteDish(intent.nameDish)
            is DishIntent.GenerateDescription -> generateDishDescription(intent.dish)
            is DishIntent.ClearGeneratedDescription -> clearGeneratedDescription()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val dishesDeferred = async { dishRepository.getDishes() }
            val categoriesDeferred = async { categoryRepository.getCategories() }

            val dishesResult = dishesDeferred.await()
            val categoriesResult = categoriesDeferred.await()

            if (dishesResult.isFailure) {
                _state.update {
                    it.copy(isLoading = false, error = dishesResult.exceptionOrNull()?.message)
                }
                return@launch
            }

            if (categoriesResult.isFailure) {
                _state.update {
                    it.copy(isLoading = false, error = categoriesResult.exceptionOrNull()?.message)
                }
                return@launch
            }

            _state.update {
                it.copy(
                    dishes = dishesResult.getOrDefault(emptyList()),
                    categories = categoriesResult.getOrDefault(emptyList()),
                    isLoading = false
                )
            }

        }
    }

    private fun generateDishDescription(dish: Dish) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isGeneratingDescription = true,
                    error = null,
                    generatedDescription = null
                )
            }

            generateDishDescriptionUseCase(dish)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            isGeneratingDescription = false,
                            generatedDescription = description
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isGeneratingDescription = false,
                            error = error.message
                        )
                    }
                }
        }
    }

    private fun clearGeneratedDescription() {
        _state.update {
            it.copy(generatedDescription = null)
        }
    }

    private fun addDish(dish: Dish) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            dishRepository.addDish(dish)
                .onSuccess {
                    loadData()
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun updateDish(nameDish: String, dish: Dish) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            dishRepository.updateDish(nameDish, dish)
                .onSuccess {
                    loadData()
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun deleteDish(nameDish: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            dishRepository.deleteDish(nameDish)
                .onSuccess {
                    loadData()
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

}