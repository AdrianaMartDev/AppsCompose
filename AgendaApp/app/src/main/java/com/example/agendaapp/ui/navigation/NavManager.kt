package com.example.agendaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.agendaapp.ui.appointment.AddAppointmentScreen
import com.example.agendaapp.ui.appointment.AgendaViewModel
import com.example.agendaapp.ui.appointment.EditAppointmentScreen
import com.example.agendaapp.ui.home.HomeScreen

const val ID_APPOINTMENT = "idAppointment"

@Composable
fun NavManager(
    viewModel: AgendaViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Home.rout
    ) {
        composable(route = Routes.Home.rout) {
            HomeScreen(
                navController,
                viewModel
            )
        }
        composable(route = Routes.AddAppointment.rout) {
            AddAppointmentScreen(navController, viewModel)
        }
        composable(
            route = Routes.Edit.rout,
            arguments = listOf(
                navArgument(ID_APPOINTMENT) { type = NavType.StringType }
            )
        ) {
            EditAppointmentScreen(
                navController,
                viewModel,
                it.arguments?.getString(ID_APPOINTMENT)
            )
        }
    }
}

