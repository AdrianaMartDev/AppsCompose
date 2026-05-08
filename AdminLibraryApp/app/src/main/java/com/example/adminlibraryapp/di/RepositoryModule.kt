package com.example.adminlibraryapp.di

import com.example.adminlibraryapp.data.repository.AuthorsRepositoryImpl
import com.example.adminlibraryapp.data.repository.LoginRepositoryImpl
import com.example.adminlibraryapp.domain.repository.AuthorsRepository
import com.example.adminlibraryapp.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun bindAuthorsRepository(
        impl: AuthorsRepositoryImpl
    ): AuthorsRepository
}