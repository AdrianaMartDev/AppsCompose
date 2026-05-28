package com.example.adminlibraryapp.presentation.ui.editorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adminlibraryapp.R
import com.example.adminlibraryapp.data.remote.models.DataEditorials
import com.example.adminlibraryapp.presentation.ui.state.EditorialState


enum class EditorialDialogMode {
    ADD,
    EDIT
}

@Composable
fun EditorialRoute(
    viewModel: EditorialViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    EditorialScreen(
        state = state,
        onAdd = viewModel::addEditorial,
        onUpdate = viewModel::updateEditorial,
        onDelete = viewModel::deleteEditorial
    )
}

@Composable
fun EditorialScreen(
    state: EditorialState,
    onAdd: (DataEditorials) -> Unit,
    onUpdate: (DataEditorials) -> Unit,
    onDelete: (DataEditorials) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogMode by remember { mutableStateOf(EditorialDialogMode.ADD) }
    var selectedEditorial by remember { mutableStateOf<DataEditorials?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogMode = EditorialDialogMode.ADD
                    showDialog = true
                    selectedEditorial = null
                },
                containerColor = colorResource(R.color.morado_oscuro),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Editorial"
                )
            }
        }
    ) { paddingValues ->
        if (showDialog) {
            EditorialDialog(
                mode = dialogMode,
                editorial = selectedEditorial,
                onDismiss = {
                    showDialog = false
                },
                onConfirm = { name ->
                    when (dialogMode) {
                        EditorialDialogMode.ADD -> {
                            onAdd(
                                DataEditorials(
                                    editorialId = System.currentTimeMillis().toString(),
                                    editorialName = name
                                )
                            )
                        }

                        EditorialDialogMode.EDIT -> {
                            selectedEditorial?.let {
                                onUpdate(
                                    it.copy(
                                        editorialName = name
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }

        EditorialContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEdit = { editorial ->
                dialogMode = EditorialDialogMode.EDIT
                selectedEditorial = editorial
                showDialog = true
            },
            onDelete = onDelete
        )
    }

}

@Composable
fun EditorialContent(
    modifier: Modifier,
    state: EditorialState,
    onEdit: (DataEditorials) -> Unit,
    onDelete: (DataEditorials) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                4.dp
            )
    ) {
        LazyColumn(modifier = Modifier.padding(4.dp)) {
            items(
                items = state.data,
                key = { it.editorialId }) { editorial ->
                EditorialCard(
                    editorial = editorial,
                    onEdit = {
                        onEdit(editorial)
                    },
                    onDelete = {
                        onDelete(editorial)
                    }
                )
            }
        }
    }
}

@Composable
fun EditorialCard(
    editorial: DataEditorials,
    onEdit: (DataEditorials) -> Unit,
    onDelete: (DataEditorials) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(2f),
                text = editorial.editorialName
            )
            IconButton(
                onClick = {
                    onEdit(editorial)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Editorial"
                )
            }

            IconButton(
                onClick = {
                    onDelete(editorial)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Editorial"
                )
            }
        }
    }
}

@Composable
fun EditorialDialog(
    mode: EditorialDialogMode,
    editorial: DataEditorials?,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {

    val editorialName by remember(editorial?.editorialId) {
        mutableStateOf(editorial?.editorialName ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = when (mode) {
                    EditorialDialogMode.ADD -> "Add Editorial"
                    EditorialDialogMode.EDIT -> "Edit Editorial"
                }
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.Center) {
                OutlinedTextField(
                    value = editorialName,
                    onValueChange = {
                        onConfirm(it)
                    },
                    label = {
                        Text(text = "Editorial Name")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(editorialName)
                },
                enabled = editorialName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.verde_oscuro),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Accept")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.rojo_oscuro),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Cancel")
            }
        }
    )

}