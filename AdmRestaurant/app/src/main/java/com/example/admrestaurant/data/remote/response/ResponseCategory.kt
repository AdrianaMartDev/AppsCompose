package com.example.admrestaurant.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseCategory(
    @SerializedName("nom_categoria")
    var nameCategory: String = "",
    @SerializedName("img_categoria")
    var imageCategory: String = ""
)