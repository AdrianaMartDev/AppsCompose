package com.example.admrestaurant.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.admrestaurant.R
import com.example.admrestaurant.presentation.ui.navigation.Views
import com.example.admrestaurant.utils.Constants
import kotlinx.coroutines.delay
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun SplashRoute(navController: NavController){
    SplashScreen(
        onSplashFinished = {
            navController.popBackStack()
            navController.navigate(Views.InitView.route)
        }
    )
}

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
){
    LaunchedEffect(key1 = Unit){
        delay(Constants.SPLASH_SCREEN_TIME.toLong())
        onSplashFinished()
    }
    SplashContent()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SplashContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            model = R.drawable.restautant,
            contentDescription = "Logo",
        )
    }
}
