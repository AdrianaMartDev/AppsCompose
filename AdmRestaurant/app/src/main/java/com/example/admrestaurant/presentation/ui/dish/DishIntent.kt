package com.example.admrestaurant.presentation.ui.dish

import com.example.admrestaurant.domain.model.Dish

sealed class DishIntent {
    object LoadDish : DishIntent()
    data class AddDish(val dish: Dish) : DishIntent()
    data class UpdateDish(val nameDish: String, val dish: Dish) : DishIntent()
    data class DeleteDish(val nameDish: String) : DishIntent()
}