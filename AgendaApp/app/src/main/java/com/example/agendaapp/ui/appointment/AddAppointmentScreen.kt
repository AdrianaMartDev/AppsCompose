package com.example.agendaapp.ui.appointment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agendaapp.R
import com.example.agendaapp.viewmodels.AgendaViewModel
import com.example.agendaapp.ui.components.TopBarView

@Composable
fun AddAppointmentScreen(
    navController: NavController,
    viewModel: AgendaViewModel
) {

    Scaffold(
        topBar = {
            TopBarView(navController, R.string.add)
        }) {
        AddAppointmentContentView(
            it,
            navController,
            viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentContentView(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: AgendaViewModel
) {
    val formState by viewModel.formState.collectAsState()

    val days = stringArrayResource(id = R.array.days_of_week)
    val schedules = stringArrayResource(id = R.array.time_slots)

    var showDays by remember { mutableStateOf(false) }
    var showTime by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTextField(
            value = formState.name,
            label = R.string.name_of_patience,
            onValueChange = viewModel::onNameChange
        )

        AppTextField(
            value = formState.subject,
            label = R.string.subject,
            onValueChange = viewModel::onSubjectChange
        )

        AppTextField(
            value = formState.phone,
            label = R.string.phone_of_patience,
            onValueChange = viewModel::onPhoneChange,
            keyboardType = KeyboardType.Number
        )

        AppDropdown(
            value = formState.day.ifEmpty { days[0] },
            items = days,
            expanded = showDays,
            onExpandedChange = {
                showDays = it
            },
            onItemSelected = viewModel::onDayChange
        )

        AppDropdown(
            value = formState.time.ifEmpty { schedules[0] },
            items = schedules,
            expanded = showTime,
            onExpandedChange = {
                showTime = it
            },
            onItemSelected = viewModel::onTimeChange
        )


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            onClick = {
                viewModel.saveAppointment()

                navController.popBackStack()
            }
        ) {
            Text(text = stringResource(R.string.agend_appointment))
        }
    }
}
