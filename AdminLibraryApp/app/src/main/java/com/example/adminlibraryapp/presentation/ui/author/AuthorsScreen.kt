package com.example.adminlibraryapp.presentation.ui.author

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adminlibraryapp.R
import com.example.adminlibraryapp.data.remote.models.DataAuthor
import com.example.adminlibraryapp.presentation.ui.state.AuthorState

enum class AuthorDialogMode {
    ADD,
    EDIT
}

@Composable
fun AuthorsRoute(
    viewModel: AuthorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    AuthorsScreen(
        state = state,
        onAdd = viewModel::addAuthor,
        onUpdate = viewModel::updateAuthor,
        onDelete = viewModel::deleteAuthor
    )
}

@Composable
fun AuthorsScreen(
    state: AuthorState,
    onAdd: (DataAuthor) -> Unit,
    onUpdate: (DataAuthor) -> Unit,
    onDelete: (DataAuthor) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogMode by remember { mutableStateOf(AuthorDialogMode.ADD) }
    var selectedAuthor by remember { mutableStateOf<DataAuthor?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogMode = AuthorDialogMode.ADD
                    selectedAuthor = null
                    showDialog = true
                },
                containerColor = colorResource(R.color.morado_oscuro),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Author"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        AuthorsContent(
            modifier = Modifier.padding(padding),
            state = state,
            onEdit = { author ->
                dialogMode = AuthorDialogMode.EDIT
                selectedAuthor = author
                showDialog = true
            },
            onDelete = onDelete
        )

        if (showDialog) {
            AuthorDialog(
                mode = dialogMode,
                author = selectedAuthor,
                onConfirm = { name ->
                    when (dialogMode) {
                        AuthorDialogMode.ADD -> {
                            onAdd(
                                DataAuthor(
                                    authorId = System.currentTimeMillis().toString(),
                                    authorName = name
                                )
                            )
                        }

                        AuthorDialogMode.EDIT -> {
                            selectedAuthor?.let {
                                onUpdate(
                                    it.copy(
                                        authorName = name
                                    )
                                )
                            }
                        }
                    }
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AuthorsContent(
    modifier: Modifier,
    state: AuthorState,
    onEdit: (DataAuthor) -> Unit,
    onDelete: (DataAuthor) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        items(
            state.data,
            key = { it.authorId }) { author ->
            AuthorCard(
                author = author,
                onEdit = {
                    onEdit(author)
                },
                onDelete = {
                    onDelete(author)
                }
            )
        }

    }
}

@Composable
fun AuthorCard(
    author: DataAuthor,
    onEdit: (DataAuthor) -> Unit = {},
    onDelete: (DataAuthor) -> Unit = {}
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
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(2f),
                text = author.authorName
            )

            IconButton(
                onClick = {
                    onEdit(author)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Edit"
                )
            }

            IconButton(
                onClick = {
                    onDelete(author)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "delete"
                )
            }
        }
    }
}

@Composable
fun AuthorDialog(
    mode: AuthorDialogMode,
    author: DataAuthor?,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    var authorName by remember(author?.authorId) {
        mutableStateOf(author?.authorName ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (mode == AuthorDialogMode.ADD) "Add Author" else "Edit Author")
        },
        text = {
            Column(verticalArrangement = Arrangement.Center) {
                OutlinedTextField(
                    value = authorName,
                    onValueChange = {
                        authorName = it
                    },
                    label = { Text(text = "Author") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(authorName)
                },
                enabled = authorName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.morado_oscuro)
                )
            ) {
                Text(
                    text = "Accept"
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.morado_oscuro)
                )
            ) {
                Text(
                    text = "Cancel"
                )
            }
        }
    )
}