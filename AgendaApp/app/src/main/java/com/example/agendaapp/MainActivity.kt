package com.example.agendaapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.agendaapp.ui.navigation.NavManager
import com.example.agendaapp.ui.theme.AgendaAppTheme
import com.example.agendaapp.ui.appointment.AgendaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AgendaViewModel by viewModels()

    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgendaAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavManager(viewModel = viewModel)
                }
            }
        }
    }
}