package com.example.adminlibraryapp.presentation.ui.editorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlibraryapp.data.remote.models.DataEditorials
import com.example.adminlibraryapp.data.repository.EditorialRepositoryImpl
import com.example.adminlibraryapp.domain.repository.Resource
import com.example.adminlibraryapp.presentation.ui.state.EditorialState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditorialViewModel @Inject constructor(
    private val editorialRepository: EditorialRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(EditorialState())
    val state: StateFlow<EditorialState> = _state.asStateFlow()

    init {
        getEditorials()
    }

    private fun getEditorials() {
        viewModelScope.launch {
            loading()

            when (val response = editorialRepository.getEditorial()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loading = false,
                        data = response.data.data,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    fun updateEditorial(editorial: DataEditorials) {
        viewModelScope.launch {
            loading()

            when (val response = editorialRepository.updateEditorial(editorial)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loading = false,
                        data = response.data.data,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    fun addEditorial(editorial: DataEditorials) {
        viewModelScope.launch {
            loading()

            when (val response = editorialRepository.addEditorial(editorial)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loading = false,
                        data = response.data.data,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    fun deleteEditorial(editorial: DataEditorials) {
        viewModelScope.launch {
            loading()

            when (val response = editorialRepository.deleteEditorial(editorial)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loading = false,
                        data = response.data.data,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    private fun loading() {
        _state.value = _state.value.copy(
            loading = true
        )
    }

    private fun showError(message: String) {
        _state.value = _state.value.copy(
            loading = false,
            error = message
        )
    }

}