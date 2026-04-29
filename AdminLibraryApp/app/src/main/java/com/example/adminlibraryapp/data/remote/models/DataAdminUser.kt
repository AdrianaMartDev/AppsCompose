package com.example.adminlibraryapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class DataAdminUser(
    @SerializedName("id_usuario")
    val userId: String,
    @SerializedName("nom_usuario")
    val userName: String,
    @SerializedName("contrasena")
    val password: String,
)
