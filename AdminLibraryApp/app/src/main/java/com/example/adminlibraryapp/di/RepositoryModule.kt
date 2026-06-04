package com.example.adminlibraryapp.di

import com.example.adminlibraryapp.data.repository.AuthorsRepositoryImpl
import com.example.adminlibraryapp.data.repository.BookRepositoryImpl
import com.example.adminlibraryapp.data.repository.CategoryRepositoryImpl
import com.example.adminlibraryapp.data.repository.EditorialRepositoryImpl
import com.example.adminlibraryapp.data.repository.LoanRepositoryImpl
import com.example.adminlibraryapp.data.repository.LoginRepositoryImpl
import com.example.adminlibraryapp.data.repository.UserRepositoryImpl
import com.example.adminlibraryapp.domain.repository.AuthorsRepository
import com.example.adminlibraryapp.domain.repository.BookRepository
import com.example.adminlibraryapp.domain.repository.CategoryRepository
import com.example.adminlibraryapp.domain.repository.EditorialRepository
import com.example.adminlibraryapp.domain.repository.LoanRepository
import com.example.adminlibraryapp.domain.repository.LoginRepository
import com.example.adminlibraryapp.domain.repository.UsersRepository
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

    @Binds
    abstract fun bindBookRepository(
        impl: BookRepositoryImpl
    ): BookRepository

    @Binds
    abstract fun bindLoanRepository(
        impl: LoanRepositoryImpl
    ): LoanRepository

    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UsersRepository

    @Binds
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    abstract fun bindEditorialRepository(
        impl: EditorialRepositoryImpl
    ): EditorialRepository

}