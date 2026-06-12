package com.example.admrestaurant.data.remote.mapper

import com.example.admrestaurant.data.remote.model.DataCategory
import com.example.admrestaurant.domain.model.Category

fun DataCategory.toDomain(): Category {
    return Category(
        nameCategory = nameCategory,
        imageCategory = imageCategory
    )
}