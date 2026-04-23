package com.example.agendaapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.agendaapp.models.Appointment

@Database(
    entities =[Appointment::class],
    version = 1,
    exportSchema = false
)
abstract class AgendaDataBase: RoomDatabase(){
    abstract fun appointmentDao(): AgendaDao
}