package com.example.agendaapp.domain.usecase

import com.example.agendaapp.data.local.AppointmentEntity
import com.example.agendaapp.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {

    fun getAllAppointments(): Flow<List<AppointmentEntity>> {
        return repository.getAllAppointments()
    }

    fun getAppointment(idAppointment: String): Flow<AppointmentEntity?> {
        return repository.getAppointment(idAppointment)
    }

    suspend fun addAppointment(appointmentEntity: AppointmentEntity) {
        repository.addAppointment(appointmentEntity)
    }

    suspend fun deleteAppointment(appointmentEntity: AppointmentEntity) {
        repository.deleteAppointment(appointmentEntity)
    }

    suspend fun updateAppointment(appointmentEntity: AppointmentEntity) {
        repository.updateAppointment(appointmentEntity)
    }


}