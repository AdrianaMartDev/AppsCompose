package com.example.agendaapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.agendaapp.models.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {

    @Query("Select * from appointment")
    fun getAllAppointments(): Flow<List<Appointment>>

    @Query("Select * from appointment where idAppointment = :idAppointment")
    fun getAppointment(idAppointment: String): Flow<Appointment?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppointment(appointment: Appointment)

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Delete
    suspend fun deleteAppointment(appointment: Appointment)

}