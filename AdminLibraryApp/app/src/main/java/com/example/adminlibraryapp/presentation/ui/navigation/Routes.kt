package com.example.adminlibraryapp.presentation.ui.navigation

sealed class Routes(
    val route: String
) {
    object SplashScreen : Routes("splash")
    object LoginScreen : Routes("login")
    object MenuScreen: Routes("menu")
    object UsersScreen: Routes("users")
    object authorsScreen: Routes("authors")
    object categoriesScreen: Routes("categories")
    object editorialsScreen: Routes("editorials")
    object booksScreen: Routes("books")
    object lendScreen: Routes("lend")
}