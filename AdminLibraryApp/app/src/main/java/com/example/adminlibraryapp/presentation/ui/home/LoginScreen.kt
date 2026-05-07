package com.example.adminlibraryapp.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminlibraryapp.R
import com.example.adminlibraryapp.presentation.ui.navigation.Routes

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.adminUserResp.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.data) {
        if (state.data != null) {
            Toast.makeText(context, state.data?.message, Toast.LENGTH_SHORT).show()
            navController.navigate(Routes.MenuScreen.route) {
                popUpTo(0)
            }
        }
    }

    LaunchedEffect(state.error) {
        state.error.let {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }


    LoginContent(
        state = state,
        onLogin = { userId, password ->
            viewModel.login(userId, password)
        }
    )

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoginContent(
    state: LoginState,
    onLogin: (String, String) -> Unit,
) {

    var user by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val isValid = user.isNotBlank() && password.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.gris_oscuro)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .padding(12.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column {
                GlideImage(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    model = R.drawable.user_card_1,
                    contentDescription = "user_card_1"
                )

                OutlinedTextField(
                    value = user,
                    onValueChange = { user = it },
                    label = { Text(text = "User") },
                    modifier = Modifier,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password") },
                    modifier = Modifier,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Button(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally),
                    enabled = !state.isLoading && isValid,
                    onClick = {
                        onLogin(user, password)
                    }
                ) {
                    if (state.isLoading) {
                        Text(text = "Loading..")
                    } else {
                        Text(text = "Login")
                    }
                }
            }
        }
    }
}