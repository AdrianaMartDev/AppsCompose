package com.example.adminlibraryapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class DataAuthor(
    @SerializedName("id_autor")
    val authorId: String = "",
    @SerializedName("nom_autor")
    val authorName: String = "",
){
    override fun toString(): String {
        return "$authorId, $authorName"
    }
}