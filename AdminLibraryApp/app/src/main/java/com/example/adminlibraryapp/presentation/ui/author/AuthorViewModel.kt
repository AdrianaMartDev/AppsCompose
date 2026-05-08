package com.example.adminlibraryapp.presentation.ui.author

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlibraryapp.data.remote.models.DataAuthor
import com.example.adminlibraryapp.presentation.ui.state.AuthorState
import com.example.adminlibraryapp.domain.repository.AuthorsRepository
import com.example.adminlibraryapp.domain.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val repository: AuthorsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AuthorState())
    val state = _state.asStateFlow()

    init {
        getAuthors()
    }


    private fun getAuthors() {
        viewModelScope.launch {
            setLoading()

            when (val result = repository.getAuthors()) {
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

    fun addAuthor(author: DataAuthor) {
        viewModelScope.launch {
            setLoading()

            when (val result = repository.addAuthor(author)) {
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

    fun deleteAuthor(author: DataAuthor) {
        viewModelScope.launch {
            setLoading()
            when (
                val result = repository.deleteAuthor(author)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = result.data.data,
                        )
                    }
                }

                is Resource.Error -> {
                    setError(message = result.message)
                }
            }
        }
    }

    fun updateAuthor(author: DataAuthor) {
        viewModelScope.launch {
            setLoading()
            when (
                val result = repository.updateAuthor(author)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = result.data.data,
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


