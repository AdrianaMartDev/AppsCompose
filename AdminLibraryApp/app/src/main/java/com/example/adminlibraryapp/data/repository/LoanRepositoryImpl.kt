package com.example.adminlibraryapp.data.repository

import com.example.adminlibraryapp.data.remote.LoanService
import com.example.adminlibraryapp.data.remote.models.DataLoans
import com.example.adminlibraryapp.data.remote.response.LoanResponse
import com.example.adminlibraryapp.domain.repository.LoanRepository
import com.example.adminlibraryapp.domain.repository.Resource
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(private val service: LoanService) : LoanRepository,
    BaseRepository() {

    override suspend fun getLoans(): Resource<LoanResponse> =
        safeApiCall { service.getLoans() }

    override suspend fun addLoan(loan: DataLoans): Resource<LoanResponse> =
        safeApiCall { service.addLoan(loan) }


    override suspend fun deleteLoan(loan: DataLoans): Resource<LoanResponse> =
        safeApiCall { service.deleteLoan(loan) }
}