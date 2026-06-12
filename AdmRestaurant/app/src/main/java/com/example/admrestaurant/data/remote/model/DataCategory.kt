package com.example.admrestaurant.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataCategory(
    @SerializedName("nom_categoria")
    val nameCategory: String,
    @SerializedName("img_categoria")
    val imageCategory: String
)