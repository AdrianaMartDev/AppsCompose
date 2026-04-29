package com.example.adminlibraryapp.data.remote.response

import com.example.adminlibraryapp.data.remote.models.DataLoans
import com.google.gson.annotations.SerializedName

data class LoanResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("mensaje")
    var message: String,
    @SerializedName("data")
    var data: List<DataLoans> = emptyList()
)
