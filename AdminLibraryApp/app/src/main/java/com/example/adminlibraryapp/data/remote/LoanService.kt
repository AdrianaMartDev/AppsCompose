package com.example.adminlibraryapp.data.remote

import com.example.adminlibraryapp.data.remote.models.DataLoans
import com.example.adminlibraryapp.data.remote.response.LoanResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoanService {

    @GET("/prestamos")
    suspend fun getLoans(): Response<LoanResponse>

    @POST("/prestamos/add")
    suspend fun addLoan(@Body book: DataLoans): Response<LoanResponse>

    @POST("/prestamos/delete")
    suspend fun deleteLoan(@Body book: DataLoans): Response<LoanResponse>
}