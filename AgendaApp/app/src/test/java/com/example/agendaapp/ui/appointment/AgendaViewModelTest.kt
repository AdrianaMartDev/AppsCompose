package com.example.agendaapp.ui.appointment

import app.cash.turbine.test
import com.example.agendaapp.data.local.AppointmentEntity
import com.example.agendaapp.domain.usecase.AppointmentUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AgendaViewModelTest {

    private val useCase: AppointmentUseCase = mockk()
    private lateinit var viewModel: AgendaViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Mock the initial call in init block
        every { useCase.getAllAppointments() } returns flowOf(emptyList())
        viewModel = AgendaViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveAppointment should call addAppointment when id is empty`() = runTest {
        // Given
        val name = "John Doe"
        val phone = "1234567890"
        val subject = "Checkup"
        val day = "Monday"
        val time = "10:00"
        
        coEvery { useCase.addAppointment(any()) } returns Unit

        // When
        viewModel.onNameChange(name)
        viewModel.onPhoneChange(phone)
        viewModel.onSubjectChange(subject)
        viewModel.onDayChange(day)
        viewModel.onTimeChange(time)
        
        viewModel.saveAppointment("")

        // Then
        coVerify { useCase.addAppointment(match { 
            it.namePatient == name && 
            it.phonePatient == phone && 
            it.subject == subject &&
            it.dayAppointment == day &&
            it.timeAppointment == time
        }) }
    }

    @Test
    fun `saveAppointment should call updateAppointment when id is provided`() = runTest {
        // Given
        val id = "123"
        val name = "Jane Doe"
        coEvery { useCase.updateAppointment(any()) } returns Unit

        // When
        viewModel.onNameChange(name)
        viewModel.saveAppointment(id)

        // Then
        coVerify { useCase.updateAppointment(match { 
            it.idAppointment == id && it.namePatient == name 
        }) }
    }

    @Test
    fun `state should update when useCase returns appointments`() = runTest {
        // Given
        val appointments = listOf(
            AppointmentEntity("1", "Patient 1", "123", "Sub 1", "Mon", "10:00")
        )
        every { useCase.getAllAppointments() } returns flowOf(appointments)
        
        // Re-initialize to trigger init block with the new mock
        viewModel = AgendaViewModel(useCase)

        // Then
        viewModel.state.test {
            val item = awaitItem()
            assertEquals(appointments, item.appointmentList)
        }
    }
}