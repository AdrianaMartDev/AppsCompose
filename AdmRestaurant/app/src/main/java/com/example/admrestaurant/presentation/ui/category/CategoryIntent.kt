package com.example.admrestaurant.presentation.ui.category

sealed class CategoryIntent {
    object LoadCategories : CategoryIntent()
    data class AddCategory(val nameCategory: String) : CategoryIntent()
    data class DeleteCategory(val nameCategory: String) : CategoryIntent()
}