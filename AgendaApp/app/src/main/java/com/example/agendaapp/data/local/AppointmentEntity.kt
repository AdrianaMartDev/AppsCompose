package com.example.agendaapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointment")
data class AppointmentEntity (
    @PrimaryKey(autoGenerate = false)
    val idAppointment:String,

    @ColumnInfo("namePatient")
    val namePatient:String,

    @ColumnInfo("phonePatient")
    val phonePatient:String,

    @ColumnInfo("subject")
    val subject:String,

    @ColumnInfo("dayAppointment")
    val dayAppointment:String,

    @ColumnInfo("timeAppointment")
    val timeAppointment:String
)