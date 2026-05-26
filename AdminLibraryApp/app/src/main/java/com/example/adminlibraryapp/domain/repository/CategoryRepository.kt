package com.example.adminlibraryapp.domain.repository

import com.example.adminlibraryapp.data.remote.models.DataCategories
import com.example.adminlibraryapp.data.remote.response.CategoryResponse

interface CategoryRepository {

    suspend fun getCategories(): Resource<CategoryResponse>

    suspend fun addCategory(category: DataCategories): Resource<CategoryResponse>

    suspend fun updateCategory(category: DataCategories): Resource<CategoryResponse>

    suspend fun deleteCategory(category: DataCategories): Resource<CategoryResponse>


}