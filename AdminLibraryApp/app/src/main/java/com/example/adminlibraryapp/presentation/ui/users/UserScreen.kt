package com.example.adminlibraryapp.presentation.ui.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adminlibraryapp.R
import com.example.adminlibraryapp.data.remote.models.DataUsers
import com.example.adminlibraryapp.presentation.ui.state.UserState

enum class UserDialogMode {
    ADD,
    UPDATE
}

@Composable
fun UserRoute(
    viewModel: UsersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    UserScreen(
        state = state,
        onAdd = viewModel::addUser,
        onUpdate = viewModel::updateUser,
        onDelete = viewModel::deleteUser
    )
}

@Composable
fun UserScreen(
    state: UserState,
    onAdd: (DataUsers) -> Unit,
    onUpdate: (DataUsers) -> Unit,
    onDelete: (DataUsers) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    var dialogMode by remember { mutableStateOf(UserDialogMode.ADD) }
    var selectedUser by remember { mutableStateOf<DataUsers?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogMode = UserDialogMode.ADD
                    showDialog = true
                    selectedUser = null
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add User"
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        if (showDialog) {
            UserDialog(
                mode = dialogMode,
                user = selectedUser,
                onDismiss = {
                    showDialog = false
                },
                onConfirm = { name, pass ->
                    when (dialogMode) {
                        UserDialogMode.ADD -> {
                            onAdd(
                                DataUsers(
                                    userId = System.currentTimeMillis().toString(),
                                    userName = name,
                                    "",
                                    pass
                                )
                            )
                        }

                        UserDialogMode.UPDATE -> {
                            selectedUser?.let {
                                onUpdate(
                                    it.copy(
                                        userName = it.userName
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }

        UserContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEdit = { user ->
                dialogMode = UserDialogMode.UPDATE
                selectedUser = user
                showDialog = true
            },
            onDelete = onDelete
        )
    }
}

@Composable
fun UserContent(
    modifier: Modifier,
    state: UserState,
    onEdit: (DataUsers) -> Unit,
    onDelete: (DataUsers) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
        ) {
            items(
                items = state.data,
                key = { it.userId }) { user ->
                UserCard(
                    user = user,
                    onEdit = {
                        onEdit(user)
                    },
                    onDelete = {
                        onDelete(user)
                    }
                )
            }
        }
    }
}

@Composable
fun UserCard(
    user: DataUsers,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 9.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(2f),
                text = user.userName
            )
            IconButton(onClick = {
                onEdit()
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit User"
                )
            }
            IconButton(onClick = {
                onDelete()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete User"
                )
            }
        }
    }

}

@Composable
fun UserDialog(
    mode: UserDialogMode,
    user: DataUsers?,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var userName by remember(user?.userId) {
        mutableStateOf(user?.userName ?: "")
    }
    var userPass by remember(user?.userId) {
        mutableStateOf(user?.password ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (mode == UserDialogMode.ADD) "Add User" else "Edit User"
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = userName,
                    onValueChange = {
                        userName = it
                    },
                    label = {
                        Text(text = "User name")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                OutlinedTextField(
                    value = userPass,
                    onValueChange = {
                        userPass = it
                    },
                    label = {
                        Text(text = "Password")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(userName, userPass)
                },
                enabled = userName.isNotBlank() && userPass.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.verde_oscuro)
                )
            ) {
                Text(text = "Accept")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.rojo_oscuro)
                )
            ) {
                Text(text = "Cancel")
            }
        }
    )
}