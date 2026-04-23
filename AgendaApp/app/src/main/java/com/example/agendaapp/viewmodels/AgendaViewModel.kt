package com.example.agendaapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendaapp.models.Appointment
import com.example.agendaapp.room.AgendaDao
import com.example.agendaapp.state.AgendaState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AgendaViewModel(
    private val dao: AgendaDao
) : ViewModel() {
    var state by mutableStateOf(AgendaState())
        private set

    init {
        viewModelScope.launch {
            dao.getAllAppointments().collectLatest {
                state = state.copy(appointmentList = it)
            }
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            dao.addAppointment(appointment)
        }
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            dao.updateAppointment(appointment)
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            dao.deleteAppointment(appointment)
        }
    }


}