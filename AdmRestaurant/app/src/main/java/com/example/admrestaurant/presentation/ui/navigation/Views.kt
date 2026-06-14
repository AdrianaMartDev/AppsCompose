package com.example.admrestaurant.presentation.ui.navigation

sealed class Views(
    val route: String
){
    object SplashScreen: Views("splash_screen")
    object InitView: Views("init")
    object CategoryView: Views("category")
    object DishView: Views("dish")
}