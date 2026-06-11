package com.example.admrestaurant.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataCategories(
    @SerializedName("nom_categoria")
    var nameCategory: String,
    @SerializedName("img_categoria")
    var imageCategory: String
)