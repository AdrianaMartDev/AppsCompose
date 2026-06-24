package com.example.admrestaurant.domain.usecase

import com.example.admrestaurant.domain.model.Dish
import com.example.admrestaurant.domain.repository.AiRepository
import javax.inject.Inject

class GenerateDishDescriptionUseCase @Inject constructor(
    private val aiRepository: AiRepository
) {

    suspend operator fun invoke(dish: Dish): Result<String> {
        return aiRepository.generateDishDescription(dish)
    }
}