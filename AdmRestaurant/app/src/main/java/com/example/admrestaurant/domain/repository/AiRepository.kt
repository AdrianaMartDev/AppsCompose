package com.example.admrestaurant.domain.repository

import com.example.admrestaurant.domain.model.Dish

interface AiRepository {

    suspend fun generateDishDescription(dish: Dish): Result<String>

}