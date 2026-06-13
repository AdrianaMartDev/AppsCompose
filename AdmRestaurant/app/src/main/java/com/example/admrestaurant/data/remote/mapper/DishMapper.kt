package com.example.admrestaurant.data.remote.mapper

import com.example.admrestaurant.data.remote.model.DataDish
import com.example.admrestaurant.domain.model.Dish

fun DataDish.toDomain(): Dish {
    return Dish(
        nameDish = nameDish,
        descDish = descDish,
        priceDish = priceDish,
        category = category
    )
}

fun Dish.toData(): DataDish {
    return DataDish(
        nameDish = nameDish,
        descDish = descDish,
        priceDish = priceDish,
        category = category
    )
}