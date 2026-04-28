package com.example.agendaapp.domain.repository

import com.example.agendaapp.data.local.AppointmentEntity
import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {

    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    suspend fun addAppointment(appointmentEntity: AppointmentEntity)

    fun getAppointment(idAppointment: String): Flow<AppointmentEntity?>

    suspend fun deleteAppointment(appointmentEntity: AppointmentEntity)

    suspend fun updateAppointment(appointmentEntity: AppointmentEntity)
}