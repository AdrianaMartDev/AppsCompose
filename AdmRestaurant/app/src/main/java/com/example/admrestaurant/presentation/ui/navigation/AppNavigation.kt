package com.example.admrestaurant.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.admrestaurant.presentation.ui.init.InitRoute
import com.example.admrestaurant.presentation.ui.SplashRoute
import com.example.admrestaurant.presentation.ui.category.CategoryRoute
import com.example.admrestaurant.presentation.ui.dish.DishRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Views.SplashScreen.route
    ) {
        composable(Views.SplashScreen.route) {
            SplashRoute(navController)
        }
        composable(Views.InitView.route) {
            InitRoute(navController)
        }
        composable(Views.CategoryView.route) {
            CategoryRoute(navController)
        }
        composable(Views.DishView.route) {
            DishRoute(navController)
        }
    }
}