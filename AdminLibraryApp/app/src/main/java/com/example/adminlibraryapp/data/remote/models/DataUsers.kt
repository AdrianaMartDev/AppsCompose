package com.example.adminlibraryapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class DataUsers(
    @SerializedName("id_usuario")
    val userId: String,
    @SerializedName("nom_usuario")
    val userName: String,
    @SerializedName("estado_usuario")
    val userState: String,
    @SerializedName("contrasena")
    val password: String
) {
    override fun toString(): String {
        return "$userId, $userName"
    }
}
