package com.example.agendaapp.di

import android.content.Context
import androidx.room.Room
import com.example.agendaapp.data.repository.AppointmentRepositoryImpl
import com.example.agendaapp.data.local.AgendaDao
import com.example.agendaapp.data.local.AgendaDataBase
import com.example.agendaapp.domain.repository.AppointmentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ): AgendaDataBase {
        return Room.databaseBuilder(
            context,
            AgendaDataBase::class.java,
            "db_agenda"
        ).build()
    }

    @Provides
    fun providesTaskDao(db: AgendaDataBase) = db.appointmentDao()

    @Provides
    @Singleton
    fun provideRepository(
        dao: AgendaDao,
    ): AppointmentRepository = AppointmentRepositoryImpl(dao = dao)
}