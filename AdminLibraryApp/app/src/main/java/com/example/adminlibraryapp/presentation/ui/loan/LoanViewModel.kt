package com.example.adminlibraryapp.presentation.ui.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlibraryapp.data.remote.models.DataLoans
import com.example.adminlibraryapp.data.repository.LoanRepositoryImpl
import com.example.adminlibraryapp.domain.repository.Resource
import com.example.adminlibraryapp.presentation.ui.state.LoanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val repository: LoanRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(LoanState())
    val state: StateFlow<LoanState> = _state.asStateFlow()

    init {
        getLoans()
    }

    private fun getLoans() {
        viewModelScope.launch {
            loading()

            when (val response = repository.getLoans()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loading = false,
                        data = response.data.data,
                        message = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    fun deleteLoan(loan: DataLoans) {
        viewModelScope.launch {
            loading()

            when (val response = repository.deleteLoan(loan)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loading = false,
                        data = response.data.data,
                        message = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    private fun loading() {
        _state.value =
            _state.value.copy(
                loading = true
            )
    }

    private fun showError(message: String) {
        _state.value =
            _state.value.copy(
                loading = false,
                message = message
            )
    }
}