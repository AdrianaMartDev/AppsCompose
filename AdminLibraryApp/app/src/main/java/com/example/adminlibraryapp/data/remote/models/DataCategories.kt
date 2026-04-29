package com.example.adminlibraryapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class DataCategories(
    @SerializedName("id_categoria")
    val categoryId: String = "",
    @SerializedName("nom_categoria")
    val categoryName: String
) {
    override fun toString(): String {
        return "$categoryId, $categoryName"
    }
}