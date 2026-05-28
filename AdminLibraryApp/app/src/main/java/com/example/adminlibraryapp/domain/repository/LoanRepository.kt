package com.example.adminlibraryapp.domain.repository

import com.example.adminlibraryapp.data.remote.models.DataLoans
import com.example.adminlibraryapp.data.remote.response.LoanResponse

interface LoanRepository {

    suspend fun getLoans(): Resource<LoanResponse>

    suspend fun addLoan(loan: DataLoans): Resource<LoanResponse>

    suspend fun deleteLoan(loan: DataLoans): Resource<LoanResponse>

}