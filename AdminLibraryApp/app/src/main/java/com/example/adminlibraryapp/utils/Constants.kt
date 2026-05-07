package com.example.adminlibraryapp.utils

import com.example.adminlibraryapp.presentation.ui.menu.MenuItem
import com.example.adminlibraryapp.presentation.ui.navigation.Routes

object Constants {
    const val BASE_URL = "http://192.168.1.62:3000"
    const val URL_ICON_MENU = "$BASE_URL/static/icon_menu"
    const val COVERS = "$BASE_URL/static/portadas_libros"

    val menuItems = listOf(
        MenuItem("Users", Routes.UsersScreen.route, "Users.png"),
        MenuItem("Authors", Routes.authorsScreen.route, "Authors.png"),
        MenuItem("Categories", Routes.categoriesScreen.route, "Categories.png"),
        MenuItem("Editorials", Routes.editorialsScreen.route, "Editorials.png"),
        MenuItem("Books", Routes.booksScreen.route, "Books.png"),
        MenuItem("Lend", Routes.lendScreen.route, "Lend.png")
    )
}