package com.example.admrestaurant.data.remote.response

import com.example.admrestaurant.data.remote.model.DataDish
import com.google.gson.annotations.SerializedName

data class ResponseDish(
    val codigo: String,
    val mensaje: String,
    val datos: List<DataDish>
)