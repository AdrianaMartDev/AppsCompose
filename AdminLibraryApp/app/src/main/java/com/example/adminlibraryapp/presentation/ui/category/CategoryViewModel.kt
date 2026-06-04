package com.example.adminlibraryapp.presentation.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlibraryapp.data.remote.models.DataCategories
import com.example.adminlibraryapp.domain.repository.CategoryRepository
import com.example.adminlibraryapp.domain.repository.Resource
import com.example.adminlibraryapp.presentation.ui.state.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: CategoryRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow()

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            setLoading()
            when (val result = repository.getCategories()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = result.data.data,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    setError(message = result.message)
                }
            }
        }
    }

    fun addCategory(category: DataCategories) {
        viewModelScope.launch {
            setLoading()

            when (val result = repository.addCategory(category)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = result.data.data,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    setError(message = result.message)
                }
            }
        }
    }

    fun deleteCategory(category: DataCategories) {
        setLoading()

        viewModelScope.launch {
            when (val result = repository.deleteCategory(category)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = result.data.data,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    setError(message = result.message)

                }
            }
        }
    }

    fun updateCategory(category: DataCategories) {
        viewModelScope.launch {
            when (val result = repository.updateCategory(category)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = result.data.data,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    setError(message = result.message)
                }
            }
        }
    }

    private fun setLoading() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun setError(message: String) {
        _state.update {
            it.copy(
                isLoading = false,
                error = message
            )
        }
    }
}


