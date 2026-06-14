package com.example.admrestaurant.presentation.ui.category

import com.example.admrestaurant.domain.model.Category

data class CategoryState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)