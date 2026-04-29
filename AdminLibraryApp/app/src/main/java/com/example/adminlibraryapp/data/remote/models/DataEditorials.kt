package com.example.adminlibraryapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class DataEditorials(
    @SerializedName("id_editorial")
    val editorialId: String,
    @SerializedName("nom_editorial")
    val editorialName: String
) {
    override fun toString(): String {
        return "$editorialId, $editorialName"
    }
}