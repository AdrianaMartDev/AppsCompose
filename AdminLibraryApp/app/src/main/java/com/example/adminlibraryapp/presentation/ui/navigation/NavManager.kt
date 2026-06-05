package com.example.adminlibraryapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adminlibraryapp.presentation.ui.LibraryViewModel
import com.example.adminlibraryapp.presentation.ui.author.AuthorsRoute
import com.example.adminlibraryapp.presentation.ui.book.BookRoute
import com.example.adminlibraryapp.presentation.ui.category.CategoryRoute
import com.example.adminlibraryapp.presentation.ui.editorial.EditorialRoute
import com.example.adminlibraryapp.presentation.ui.home.LoginScreen
import com.example.adminlibraryapp.presentation.ui.home.SplashScreen
import com.example.adminlibraryapp.presentation.ui.loan.LoanRoute
import com.example.adminlibraryapp.presentation.ui.menu.MenuScreen
import com.example.adminlibraryapp.presentation.ui.users.UserRoute

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
            MenuScreen(navController)
        }

        composable(Routes.UsersScreen.route) {
            UserRoute()
        }

        composable(Routes.authorsScreen.route) {
            AuthorsRoute()
        }

        composable(Routes.categoriesScreen.route) {
            CategoryRoute()
        }
        composable(Routes.editorialsScreen.route) {
            EditorialRoute()
        }

        composable(Routes.booksScreen.route) {
            BookRoute()
        }

        composable(Routes.lendScreen.route) {
            LoanRoute()
        }

    }
}