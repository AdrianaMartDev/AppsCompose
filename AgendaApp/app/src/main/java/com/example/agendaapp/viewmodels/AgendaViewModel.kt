package com.example.agendaapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendaapp.models.Appointment
import com.example.agendaapp.room.AgendaDao
import com.example.agendaapp.state.AgendaState
import com.example.agendaapp.state.AppointmentFormState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AgendaViewModel(
    private val dao: AgendaDao
) : ViewModel() {
    var state by mutableStateOf(AgendaState())
        private set

    private val _formState = MutableStateFlow(AppointmentFormState())
    val formState: StateFlow<AppointmentFormState> = _formState

    private var job: Job? = null

    init {
        viewModelScope.launch {
            dao.getAllAppointments().collectLatest {
                state = state.copy(appointmentList = it)
            }
        }
    }

    private fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            dao.addAppointment(appointment)
        }
    }

    fun getAppointment(idAppointment: String?) {
        if (idAppointment == null) return

        job?.cancel()
        job = viewModelScope.launch {
            dao.getAppointment(idAppointment).collectLatest { appointment ->
                if (appointment != null) {
                    _formState.value = AppointmentFormState(
                        id = appointment.idAppointment,
                        name = appointment.namePatient,
                        phone = appointment.phonePatient,
                        subject = appointment.subject,
                        day = appointment.dayAppointment,
                        time = appointment.timeAppointment
                    )
                }
            }
        }
    }

    private fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            dao.updateAppointment(appointment)
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            dao.deleteAppointment(appointment)
        }
    }

    fun onNameChange(value: String) {
        _formState.update { it.copy(name = value) }
    }

    fun onPhoneChange(value: String) {
        _formState.update { it.copy(phone = value) }
    }

    fun onSubjectChange(value: String) {
        _formState.update { it.copy(subject = value) }
    }

    fun onDayChange(value: String) {
        _formState.update { it.copy(day = value) }
    }

    fun onTimeChange(value: String) {
        _formState.update { it.copy(time = value) }
    }

    fun saveAppointment(idAppointment: String = "") {
        val state = _formState.value

        val appointment = Appointment(
            idAppointment = System.currentTimeMillis().toString(),
            namePatient = state.name,
            phonePatient = state.phone,
            subject = state.subject,
            dayAppointment = state.day,
            timeAppointment = state.time
        )

        if (idAppointment.isEmpty()) {
            addAppointment(appointment)
        } else {
            updateAppointment(appointment)
        }
        clearForm()
    }

    private fun clearForm() {
        _formState.value = AppointmentFormState()
    }
}