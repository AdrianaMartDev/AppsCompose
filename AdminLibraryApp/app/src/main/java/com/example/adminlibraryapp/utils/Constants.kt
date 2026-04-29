package com.example.adminlibraryapp.utils

object Constants {
    const val BASE_URL = "http://192.168.1.62:3000"
    const val URL_ICON_MENU = "$BASE_URL/static/icon_menu"
    const val COVERS = "$BASE_URL/static/portadas_libros"

    val menuList = listOf(
        "Users",
        "Authors",
        "Categories",
        "Editorials",
        "Books",
        "Lends"
    )
}