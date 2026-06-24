package com.example.admrestaurant.presentation.ui.dish

import com.example.admrestaurant.domain.model.Category
import com.example.admrestaurant.domain.model.Dish

data class DishState(
    val dishes: List<Dish> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
    val isGeneratingDescription: Boolean = false,
    val generatedDescription: String? = null
)