package com.example.admrestaurant.data.repository

import com.example.admrestaurant.data.remote.mapper.toData
import com.example.admrestaurant.data.remote.mapper.toDomain
import com.example.admrestaurant.data.remote.service.DishService
import com.example.admrestaurant.domain.model.Dish
import com.example.admrestaurant.domain.repository.DishRepository
import com.example.admrestaurant.utils.safeApiCall
import javax.inject.Inject

class DishRepositoryImpl @Inject constructor(private val service: DishService) : DishRepository {

    override suspend fun getDishes(): Result<List<Dish>> {
        return safeApiCall { service.getDishes() }
            .map { response -> response.datos.map { it.toDomain() } }
    }

    override suspend fun addDish(dish: Dish): Result<List<Dish>> {
        return safeApiCall { service.addDish(dish.toData()) }
            .map { response -> response.datos.map { it.toDomain() } }
    }

    override suspend fun updateDish(nameDish: String, dish: Dish): Result<List<Dish>> {
        return safeApiCall { service.updateDish(nameDish, dish.toData()) }
            .map { response -> response.datos.map { it.toDomain() } }
    }

    override suspend fun deleteDish(nameDish: String): Result<List<Dish>> {
        return safeApiCall { service.deleteDish(nameDish) }
            .map { response -> response.datos.map { it.toDomain() } }
    }
}