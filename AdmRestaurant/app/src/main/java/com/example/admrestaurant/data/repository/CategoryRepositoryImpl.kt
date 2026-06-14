package com.example.admrestaurant.data.repository

import com.example.admrestaurant.data.remote.mapper.toDomain
import com.example.admrestaurant.data.remote.service.CategoryService
import com.example.admrestaurant.domain.model.Category
import com.example.admrestaurant.domain.repository.CategoryRepository
import com.example.admrestaurant.utils.safeApiCall
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val service: CategoryService
) : CategoryRepository {

    override suspend fun getCategories(): Result<List<Category>> {
        return safeApiCall { service.getCategories() }
            .map { response -> response.datos.map { it.toDomain() } }
    }

    override suspend fun addCategories(nameCategory: String): Result<Unit> {
        return safeApiCall { service.addCategory(nameCategory) }
            .map { response -> response.datos.map { it.toDomain() } }
    }

    override suspend fun deleteCategories(nameCategory: String): Result<Unit> {
        return safeApiCall { service.deleteCategory(nameCategory) }
            .map { response -> response.datos.map { it.toDomain() } }
    }

}