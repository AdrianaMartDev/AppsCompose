package com.example.agendaapp.data.repository

import com.example.agendaapp.data.local.AgendaDao
import com.example.agendaapp.data.local.AppointmentEntity
import com.example.agendaapp.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppointmentRepositoryImpl
@Inject constructor(
    private val dao: AgendaDao
) : AppointmentRepository {

    override fun getAllAppointments(): Flow<List<AppointmentEntity>> = dao.getAllAppointments()


    override suspend fun addAppointment(appointmentEntity: AppointmentEntity) {
        dao.addAppointment(appointmentEntity)
    }

    override fun getAppointment(idAppointment: String): Flow<AppointmentEntity?> =
        dao.getAppointment(idAppointment)

    override suspend fun deleteAppointment(appointmentEntity: AppointmentEntity) {
        dao.deleteAppointment(appointmentEntity)
    }

    override suspend fun updateAppointment(appointmentEntity: AppointmentEntity) {
        dao.updateAppointment(appointmentEntity)
    }


}