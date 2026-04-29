package com.example.adminlibraryapp.di

import com.example.adminlibraryapp.data.remote.AuthorService
import com.example.adminlibraryapp.data.remote.BookService
import com.example.adminlibraryapp.data.remote.CategoryService
import com.example.adminlibraryapp.data.remote.EditorialService
import com.example.adminlibraryapp.data.remote.LoanService
import com.example.adminlibraryapp.data.remote.UserService
import com.example.adminlibraryapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(providesOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthorService(retrofit: Retrofit): AuthorService {
        return retrofit.create(AuthorService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookService(retrofit: Retrofit): BookService {
        return retrofit.create(BookService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideEditorialService(retrofit: Retrofit): EditorialService {
        return retrofit.create(EditorialService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoanService(retrofit: Retrofit): LoanService {
        return retrofit.create(LoanService::class.java)
    }

     @Provides
     @Singleton
     fun provideUserService(retrofit: Retrofit): UserService {
         return retrofit.create(UserService::class.java)
     }

}