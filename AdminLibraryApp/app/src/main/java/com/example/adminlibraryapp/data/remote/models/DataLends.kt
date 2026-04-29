package com.example.adminlibraryapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class DataLends(
    @SerializedName("id_prestamo")
    val lendId: String,
    @SerializedName("isbn")
    val isBn: String,
    @SerializedName("id_usuario")
    val userId: String,
    @SerializedName("fecha_prestamo")
    val lendDate: String
)