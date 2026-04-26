package com.example.agendaapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agendaapp.models.Appointment
import com.example.agendaapp.viewmodels.AgendaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAppointmentScreen(
    navController: NavController,
    viewModel: AgendaViewModel,
    idAppointment: String?
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Edit Appointment",
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
                    navController.navigate("home")
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
        EditContentView(
            it,
            navController,
            viewModel,
            idAppointment
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContentView(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: AgendaViewModel,
    idAppointment: String?
) {
    LaunchedEffect(idAppointment) {
        viewModel.getAppointment(idAppointment)
    }
    val state = viewModel.state
    val appointment = state.appointment

    val keyboardController = LocalSoftwareKeyboardController.current

    var namePatient by remember(appointment) { mutableStateOf(appointment?.namePatient ?: "") }
    var phonePatient by remember(appointment) { mutableStateOf(appointment?.phonePatient ?: "") }
    var subject by remember(appointment) { mutableStateOf(appointment?.subject ?: "") }

    val dayList =
        listOf(
            "Select day",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
        )

    var selectedDay by remember { mutableStateOf(dayList[0]) }
    var showDays by remember { mutableStateOf(false) }

    val schedulesList = listOf(
        "Select time",
        "9:00 to 10:00",
        "10:00 to 11:00",
        "11:00 to 12:00",
        "12:00 to 13:00",
        "13:00 to 14:00",
        "14:00 to 15:00",
        "15:00 to 16:00",
        "16:00 to 17:00",
        "17:00 to 18:00",
        "18:00 to 19:00"
    )
    var selectedTime by remember { mutableStateOf(schedulesList[0]) }
    var showTime by remember { mutableStateOf(false) }

    val maxTel = 10

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 30.dp)
            .padding(horizontal = 15.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = namePatient,
            onValueChange = {
                namePatient = it
            },
            label = {
                Text(text = "Name of patience")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = phonePatient,
            onValueChange = {
                phonePatient = it
            },
            label = {
                Text(text = "Phone of patience")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            maxLines = maxTel
        )

        OutlinedTextField(
            value = subject,
            onValueChange = {
                subject = it
            },
            label = {
                Text(text = "Subject")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .padding(
                    bottom = 15.dp,
                    top = 15.dp
                ),
            expanded = showDays,
            onExpandedChange = {
                showDays = !showDays
            }
        ) {
            keyboardController?.hide()

            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedDay,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = showDays
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = showDays,
                onDismissRequest = {
                    showDays = false
                }
            ) {
                dayList.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = {
                            Text(text = s)
                        },
                        onClick = {
                            if (s != dayList[0]) {
                                selectedDay = s
                            }
                            showDays = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            expanded = showTime,
            onExpandedChange = {
                showTime = !showTime
            }
        ) {
            keyboardController?.hide()

            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedTime,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDays)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = showTime,
                onDismissRequest = {
                    showTime = false
                }
            ) {
                schedulesList.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = {
                            Text(text = s)
                        },
                        onClick = {
                            if (s != schedulesList[0]) {
                                selectedTime = s
                            }
                            showTime = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }


    Button(
        onClick = {
            val appointment = Appointment(
                idAppointment = appointment?.idAppointment ?: "",
                namePatient = namePatient,
                phonePatient = phonePatient,
                subject = subject,
                dayAppointment = selectedDay,
                timeAppointment = selectedTime
            )

            viewModel.updateAppointment(appointment)
            navController.popBackStack()
        }
    ) {
        Text(text = "Update appointment")
    }
}