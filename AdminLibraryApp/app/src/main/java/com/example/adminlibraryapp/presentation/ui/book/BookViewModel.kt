package com.example.adminlibraryapp.presentation.ui.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlibraryapp.data.remote.models.DataBooks
import com.example.adminlibraryapp.data.remote.models.DataLoans
import com.example.adminlibraryapp.data.remote.response.BookResponse
import com.example.adminlibraryapp.domain.repository.AuthorsRepository
import com.example.adminlibraryapp.domain.repository.BookRepository
import com.example.adminlibraryapp.domain.repository.CategoryRepository
import com.example.adminlibraryapp.domain.repository.EditorialRepository
import com.example.adminlibraryapp.domain.repository.LoanRepository
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
    private val bookRepository: BookRepository,
    private val loanRepository: LoanRepository,
    private val authorRepository: AuthorsRepository,
    private val categoryRepository: CategoryRepository,
    private val editorialRepository: EditorialRepository
) :
    ViewModel() {

    private val _state = MutableStateFlow(BookState())
    val state = _state.asStateFlow()

    init {
        loadInitialData()
    }


    private fun loadInitialData() {
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
            handleBookResponse(
                bookRepository.getBooks()
            )
        }
    }

    fun addBook(book: DataBooks) {
        viewModelScope.launch {
            showLoading()
            handleBookResponse(
                bookRepository.addBook(book)
            )
        }
    }

    fun editBook(book: DataBooks) {
        viewModelScope.launch {
            showLoading()
            handleBookResponse(
                bookRepository.updateBook(book)
            )
        }
    }

    fun deleteBook(book: DataBooks) {
        viewModelScope.launch {
            showLoading()
            handleBookResponse(
                bookRepository.deleteBook(book)
            )
        }
    }

    fun loanBook(loan: DataLoans) {
        viewModelScope.launch {
            showLoading()
            when (val response = loanRepository.addLoan(loan)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
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

    private fun handleBookResponse(
        response: Resource<BookResponse>
    ) {
        when (response) {
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