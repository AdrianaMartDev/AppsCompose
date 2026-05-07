package com.example.adminlibraryapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adminlibraryapp.presentation.ui.LibraryViewModel
import com.example.adminlibraryapp.presentation.ui.home.LoginScreen
import com.example.adminlibraryapp.presentation.ui.home.SplashScreen

@Composable
fun NavManager(
    viewModel: LibraryViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.route
    ) {
        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(Routes.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(Routes.MenuScreen.route) {
            MenuScreen(navController, viewModel)
        }

        composable(Routes.UsersScreen.route) {
            UsersScreen()
        }

        composable(Routes.authorsScreen.route) {
            AuthorsScreen()
        }

        composable(Routes.categoriesScreen.route) {
            CategoriesScreen()
        }
        composable(Routes.editorialsScreen.route) {
            EditorialsScreen()
        }

        composable(Routes.booksScreen.route) {
            BooksScreen()
        }

        composable(Routes.lendScreen.route) {
            LendScreen()
        }

    }
}