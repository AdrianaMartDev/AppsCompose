package com.example.admrestaurant.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataDish(
    @SerializedName("nom_platillo")
    var nameDish: String ="",
    @SerializedName("descripcion_platillo")
    var descDish: String ="",
    @SerializedName("precio")
    var priceDish: Double = 0.0,
    @SerializedName("nom_categoria")
    var category: String ="",
)