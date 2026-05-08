package com.example.adminlibraryapp.data.remote.response

import com.example.adminlibraryapp.data.remote.models.DataAuthor
import com.google.gson.annotations.SerializedName

data class AuthorResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("mensaje")
    var message: String,
    @SerializedName("data")
    var data: List<DataAuthor> = emptyList()
)
