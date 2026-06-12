package com.example.admrestaurant.data.remote.response

import com.example.admrestaurant.data.remote.model.DataCategory

data class ResponseCategory(
    val codigo: String,
    val mensaje: String,
    val datos: List<DataCategory>
)