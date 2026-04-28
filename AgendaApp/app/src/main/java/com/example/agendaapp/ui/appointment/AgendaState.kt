package com.example.agendaapp.ui.appointment

import com.example.agendaapp.data.local.AppointmentEntity

data class AgendaState(
    var appointmentList: List<AppointmentEntity> = emptyList(),
    var appointment: AppointmentEntity? = null
)