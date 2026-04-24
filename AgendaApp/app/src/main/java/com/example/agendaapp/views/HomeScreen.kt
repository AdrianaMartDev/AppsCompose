package com.example.agendaapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agendaapp.viewmodels.AgendaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AgendaViewModel
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Agenda",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add")
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        HomeContentView(
            it,
            navController,
            viewModel
        )
    }
}

@Composable
fun HomeContentView(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: AgendaViewModel
) {

    var txtPatience by remember { mutableStateOf("") }
    val state = viewModel.state

    Column(modifier = Modifier.padding(paddingValues)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            OutlinedTextField(
                value = txtPatience,
                onValueChange = {
                    txtPatience = it
                },
                placeholder = {
                    Text(text = "Name of patience to search...")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                )
            )
        }

        LazyColumn {
            items(
                state.appointmentList.filter {
                    it.namePatient.lowercase().contains(txtPatience.lowercase())
                }.sortedBy {
                    it.dayAppointment
                }.sortedBy {
                    it.timeAppointment
                }
            ) {
                var color: Color = Color.White

                when (it.dayAppointment) {
                    "Monday" -> color = Color.Red
                    "Tuesday" -> color = Color.Blue
                    "Wednesday" -> color = Color.Gray
                    "Thursday" -> color = Color.Cyan
                    "Friday" -> color = Color.Magenta
                    "Saturday" -> color = Color.LightGray
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = color,
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = it.namePatient
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = it.dayAppointment
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = it.timeAppointment
                        )

                        Row(modifier = Modifier.fillMaxWidth()) {
                            IconButton(
                                onClick = { navController.navigate("edit/${it.idAppointment}") }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit"
                                )
                            }

                            IconButton(
                                onClick = {
                                    viewModel.deleteAppointment(it)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}