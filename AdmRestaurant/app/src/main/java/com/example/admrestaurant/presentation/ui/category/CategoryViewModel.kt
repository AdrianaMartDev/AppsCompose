package com.example.admrestaurant.presentation.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.admrestaurant.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow()

    init {
        processIntent(CategoryIntent.LoadCategories)
    }

    fun processIntent(intent: CategoryIntent) {
        when (intent) {
            is CategoryIntent.LoadCategories -> loadCategories()
            is CategoryIntent.AddCategory -> addCategory(intent.nameCategory)
            is CategoryIntent.DeleteCategory -> deleteCategory(intent.nameCategory)
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            repository.getCategories()
                .onSuccess { categories ->
                    _state.update {
                        it.copy(categories = categories, isLoading = false)
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(error = error.message, isLoading = false)
                    }
                }
        }
    }

    fun addCategory(nameCategory: String) {
        if (nameCategory.isBlank()) {
            _state.update {
                it.copy(error = "El nombre de la categoría no puede estar vacío")
            }
            return
        }
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            repository.addCategories(nameCategory)
                .onSuccess {
                    loadCategories()
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(error = error.message, isLoading = false)
                    }
                }
        }
    }

    fun deleteCategory(nameCategory: String) {
        if (nameCategory.isBlank()) {
            _state.update {
                it.copy(error = "El nombre de la categoría no puede estar vacío")
            }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.deleteCategories(nameCategory)
                .onSuccess {
                    loadCategories()
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
        }
    }
}