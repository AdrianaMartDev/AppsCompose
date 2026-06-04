package com.example.adminlibraryapp.presentation.ui.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlibraryapp.data.remote.models.DataBooks
import com.example.adminlibraryapp.data.remote.models.DataLoans
import com.example.adminlibraryapp.data.repository.AuthorsRepositoryImpl
import com.example.adminlibraryapp.data.repository.BookRepositoryImpl
import com.example.adminlibraryapp.data.repository.CategoryRepositoryImpl
import com.example.adminlibraryapp.data.repository.EditorialRepositoryImpl
import com.example.adminlibraryapp.data.repository.LoanRepositoryImpl
import com.example.adminlibraryapp.domain.repository.Resource
import com.example.adminlibraryapp.presentation.ui.state.BookState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepositoryImpl,
    private val loanRepository: LoanRepositoryImpl,
    private val authorRepository: AuthorsRepositoryImpl,
    private val categoryRepository: CategoryRepositoryImpl,
    private val editorialRepository: EditorialRepositoryImpl
) :
    ViewModel() {

    private val _state = MutableStateFlow(BookState())
    val state = _state.asStateFlow()

    init {
        loadInitialData()
    }


    fun loadInitialData() {
        viewModelScope.launch {
            showLoading()
            val books = async { bookRepository.getBooks() }
            val authors = async { authorRepository.getAuthors() }
            val categories = async { categoryRepository.getCategories() }
            val editorials = async { editorialRepository.getEditorial() }

            val booksResult = books.await()
            val authorsResult = authors.await()
            val categoriesResult = categories.await()
            val editorialsResult = editorials.await()

            when {
                booksResult is Resource.Error ->
                    showError(booksResult.message)

                authorsResult is Resource.Error ->
                    showError(authorsResult.message)

                categoriesResult is Resource.Error ->
                    showError(categoriesResult.message)

                editorialsResult is Resource.Error ->
                    showError(editorialsResult.message)

                else -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        books = (booksResult as Resource.Success).data.data,
                        authors = (authorsResult as Resource.Success).data.data,
                        categories = (categoriesResult as Resource.Success).data.data,
                        editorials = (editorialsResult as Resource.Success).data.data,
                        error = ""
                    )
                }
            }
        }
    }

    private fun getBooks() {
        viewModelScope.launch {
            showLoading()
            when (val response = bookRepository.getBooks()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        books = response.data.data,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    fun addBook(book: DataBooks) {
        viewModelScope.launch {
            showLoading()
            when (val response = bookRepository.addBook(book)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        books = response.data.data,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    fun editBook(book: DataBooks) {
        viewModelScope.launch {
            showLoading()
            when (val response = bookRepository.updateBook(book)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        books = response.data.data,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    fun deleteBook(book: DataBooks) {
        viewModelScope.launch {
            showLoading()
            when (val response = bookRepository.deleteBook(book)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        books = response.data.data,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    fun loanBook(loan: DataLoans) {
        viewModelScope.launch {
            showLoading()
            when (val response = loanRepository.addLoan(loan)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        loans = response.data.data,
                        error = ""
                    )
                    getBooks()
                }

                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    private fun showLoading() {
        _state.value = _state.value.copy(
            isLoading = true
        )
    }

    private fun showError(message: String) {
        _state.value = _state.value.copy(
            isLoading = false,
            error = message
        )
    }

}