package com.example.agendaapp.ui.appointment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendaapp.data.local.AppointmentEntity
import com.example.agendaapp.domain.usecase.AppointmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val appointmentUseCase: AppointmentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AgendaState())
    val state: StateFlow<AgendaState> = _state

    private val _formState = MutableStateFlow(AppointmentFormState())
    val formState: StateFlow<AppointmentFormState> = _formState

    private var job: Job? = null
    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search

    init {
        viewModelScope.launch {
            appointmentUseCase.getAllAppointments().collectLatest { appointments ->
                _state.update {
                    it.copy(appointmentList = appointments)
                }
            }
        }
    }

    private fun addAppointment(appointmentEntity: AppointmentEntity) {
        viewModelScope.launch {
            appointmentUseCase.addAppointment(appointmentEntity)
        }
    }

    fun getAppointment(idAppointment: String?) {
        if (idAppointment == null) return

        job?.cancel()
        job = viewModelScope.launch {
            appointmentUseCase.getAppointment(idAppointment).collectLatest { appointment ->
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

    private fun updateAppointment(appointmentEntity: AppointmentEntity) {
        viewModelScope.launch {
            appointmentUseCase.updateAppointment(appointmentEntity)
        }
    }

    fun deleteAppointment(appointmentEntity: AppointmentEntity) {
        viewModelScope.launch {
            appointmentUseCase.deleteAppointment(appointmentEntity)
        }
    }

    val filterAppointment = combine(
        _state,
        _search
    ) { state, search ->
        state.appointmentList
            .filter {
                it.namePatient.contains(search, ignoreCase = true)
            }
            .sortedBy { it.dayAppointment }
            .sortedBy { it.timeAppointment }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun onSearchChange(value: String) {
        _search.value = value
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

        val appointmentEntity = AppointmentEntity(
            idAppointment = idAppointment.ifEmpty { System.currentTimeMillis().toString() },
            namePatient = state.name,
            phonePatient = state.phone,
            subject = state.subject,
            dayAppointment = state.day,
            timeAppointment = state.time
        )

        if (idAppointment.isEmpty()) {
            addAppointment(appointmentEntity)
        } else {
            updateAppointment(appointmentEntity)
        }
        clearForm()
    }

    private fun clearForm() {
        _formState.value = AppointmentFormState()
    }
}