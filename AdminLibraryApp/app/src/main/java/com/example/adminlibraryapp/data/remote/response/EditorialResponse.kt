package com.example.adminlibraryapp.data.remote.response

import com.example.adminlibraryapp.data.remote.models.DataEditorials
import com.example.adminlibraryapp.data.remote.models.DataUsers
import com.google.gson.annotations.SerializedName

data class EditorialResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("mensaje")
    var message: String,
    @SerializedName("data")
    var data: List<DataEditorials> = emptyList()
)
