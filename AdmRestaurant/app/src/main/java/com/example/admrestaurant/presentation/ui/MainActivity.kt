package com.example.admrestaurant.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.admrestaurant.presentation.ui.navigation.AppNavigation
import com.example.admrestaurant.presentation.ui.theme.AdmRestaurantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdmRestaurantTheme {
                Surface(modifier = Modifier.Companion.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}