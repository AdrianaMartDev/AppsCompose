package com.example.adminlibraryapp.data.repository

import com.example.adminlibraryapp.data.remote.CategoryService
import com.example.adminlibraryapp.data.remote.models.DataCategories
import com.example.adminlibraryapp.data.remote.response.CategoryResponse
import com.example.adminlibraryapp.domain.repository.CategoryRepository
import com.example.adminlibraryapp.domain.repository.Resource
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService
) :
    CategoryRepository, BaseRepository() {

    override suspend fun getCategories(): Resource<CategoryResponse> =
        safeApiCall {
            categoryService.getCategories()
        }


    override suspend fun addCategory(category: DataCategories): Resource<CategoryResponse> =
        safeApiCall {
            categoryService.addCategory(category)
        }

    override suspend fun updateCategory(category: DataCategories): Resource<CategoryResponse> =
        safeApiCall {
            categoryService.updateCategory(category)
        }


    override suspend fun deleteCategory(category: DataCategories): Resource<CategoryResponse> =
        safeApiCall {
            categoryService.deleteCategory(category)
        }
}