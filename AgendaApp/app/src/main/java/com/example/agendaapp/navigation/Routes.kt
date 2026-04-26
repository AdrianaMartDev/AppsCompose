package com.example.agendaapp.navigation

sealed class Routes(val rout: String) {
    object Home : Routes("home")

    object AddAppointment : Routes("add")

    object Edit : Routes("edit/{idAppointment}") {
        fun createRoute(idAppointment: String) = "edit/$idAppointment"
    }

    object Delete : Routes("delete")
}