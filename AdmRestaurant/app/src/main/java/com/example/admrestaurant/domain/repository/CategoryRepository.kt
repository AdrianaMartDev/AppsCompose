package com.example.admrestaurant.domain.repository

import com.example.admrestaurant.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): Result<List<Category>>
    suspend fun addCategories(nameCategory: String): Result<List<Category>>
    suspend fun deleteCategories(nameCategory: String): Result<List<Category>>
}