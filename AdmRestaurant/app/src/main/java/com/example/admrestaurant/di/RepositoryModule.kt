package com.example.admrestaurant.di

import com.example.admrestaurant.data.repository.AiRepositoryImpl
import com.example.admrestaurant.data.repository.CategoryRepositoryImpl
import com.example.admrestaurant.data.repository.DishRepositoryImpl
import com.example.admrestaurant.domain.repository.AiRepository
import com.example.admrestaurant.domain.repository.CategoryRepository
import com.example.admrestaurant.domain.repository.DishRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindDishRepository(
        dishRepositoryImpl: DishRepositoryImpl
    ): DishRepository

    @Binds
    @Singleton
    abstract fun bindAiRepository(
        aiRepositoryImpl: AiRepositoryImpl
    ): AiRepository

}