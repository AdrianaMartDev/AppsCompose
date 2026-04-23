package com.example.agendaapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.agendaapp.navigation.NavManager
import com.example.agendaapp.room.AgendaDataBase
import com.example.agendaapp.ui.theme.AgendaAppTheme
import com.example.agendaapp.viewmodels.AgendaViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgendaAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                   val  database = Room.databaseBuilder(
                       this,
                       AgendaDataBase::class.java,
                       "db_agenda"
                   ).build()
                    val dao = database.appointmentDao()
                    val viewModel = AgendaViewModel(dao = dao)
                    NavManager(viewModel = viewModel)
                }
            }
        }
    }
}