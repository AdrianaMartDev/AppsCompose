package com.example.agendaapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {

    @Query("Select * from appointment")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Query("Select * from appointment where idAppointment = :idAppointment")
    fun getAppointment(idAppointment: String): Flow<AppointmentEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppointment(appointmentEntity: AppointmentEntity)

    @Update
    suspend fun updateAppointment(appointmentEntity: AppointmentEntity)

    @Delete
    suspend fun deleteAppointment(appointmentEntity: AppointmentEntity)

}