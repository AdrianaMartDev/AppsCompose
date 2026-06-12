package com.example.admrestaurant.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataDish(
    @SerializedName("nom_platillo")
    val nameDish: String = "",
    @SerializedName("descripcion_platillo")
    val descDish: String = "",
    @SerializedName("precio")
    val priceDish: Double = 0.0,
    @SerializedName("nom_categoria")
    val category: String = ""
)