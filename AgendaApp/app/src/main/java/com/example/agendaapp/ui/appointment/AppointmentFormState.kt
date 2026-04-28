package com.example.agendaapp.ui.appointment

data class AppointmentFormState(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val subject: String = "",
    val day: String = "",
    val time: String = ""
)