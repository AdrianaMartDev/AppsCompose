package com.example.agendaapp.state

import com.example.agendaapp.models.Appointment

data class AgendaState(
    var appointmentList: List<Appointment> = emptyList()
)