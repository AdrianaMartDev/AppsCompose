package com.example.adminlibraryapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class DataBooks(
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("portada")
    val cover: String,
    @SerializedName("nom_libre")
    val bookName: String,
    @SerializedName("autor")
    val author: String,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("editorial")
    val editorial: String,
    @SerializedName("anio_publicacion")
    val releaseYear: String,
    @SerializedName("edicion")
    val edition: String,
    @SerializedName("existencias")
    val stock: String,
    @SerializedName("categoria")
    val category: String,
)