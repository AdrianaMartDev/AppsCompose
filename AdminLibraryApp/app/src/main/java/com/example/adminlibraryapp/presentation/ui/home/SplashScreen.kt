package com.example.adminlibraryapp.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminlibraryapp.R
import com.example.adminlibraryapp.presentation.ui.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(true) {
        delay(3000)
        navController.popBackStack()
        navController.navigate(Routes.LoginScreen.route)
    }
    SplashComponent()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SplashComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azul_100)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            model = R.drawable.gif_libro,
            contentDescription = "gif_logo"
        )
    }
}