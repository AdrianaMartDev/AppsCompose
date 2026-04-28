package com.example.agendaapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities =[AppointmentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AgendaDataBase: RoomDatabase(){
    abstract fun appointmentDao(): AgendaDao
}