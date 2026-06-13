package com.example.admrestaurant.domain.repository

import com.example.admrestaurant.domain.model.Dish

interface DishRepository {

    suspend fun getDishes(): Result<List<Dish>>
    suspend fun addDish(dish: Dish): Result<List<Dish>>
    suspend fun updateDish(nameDish: String, dish: Dish): Result<List<Dish>>
    suspend fun deleteDish(nameDish: String): Result<List<Dish>>

}