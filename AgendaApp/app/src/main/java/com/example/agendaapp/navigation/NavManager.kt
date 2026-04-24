package com.example.agendaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.agendaapp.views.AddAppointmentScreen
import com.example.agendaapp.viewmodels.AgendaViewModel
import com.example.agendaapp.views.EditAppointmentScreen
import com.example.agendaapp.views.HomeScreen

@Composable
fun NavManager(
    viewModel: AgendaViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            HomeScreen(
                navController,
                viewModel
            )
        }
        composable(route = "add") {
            AddAppointmentScreen(navController, viewModel)
        }
        composable(
            route = "edit/{idAppointment}",
            arguments = listOf(
                navArgument("idAppointment") { type = NavType.StringType }
            )
        ) {
            EditAppointmentScreen(
                navController,
                viewModel,
                it.arguments?.getString("idAppointment")
            )
        }
    }
}

