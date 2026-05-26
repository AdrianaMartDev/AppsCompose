package com.example.adminlibraryapp.presentation.ui.category

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
import com.example.adminlibraryapp.data.remote.models.DataCategories
import com.example.adminlibraryapp.presentation.ui.state.CategoryState

enum class CategoryDialogMode {
    ADD,
    EDIT
}

@Composable
fun CategoryRoute(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    CategoryScreen(
        state = state,
        onAdd = viewModel::addCategory,
        onUpdate = viewModel::updateCategory,
        onDelete = viewModel::deleteCategory
    )
}

@Composable
fun CategoryScreen(
    state: CategoryState,
    onAdd: (DataCategories) -> Unit,
    onUpdate: (DataCategories) -> Unit,
    onDelete: (DataCategories) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogMode by remember { mutableStateOf(CategoryDialogMode.ADD) }
    var selectedCategory by remember { mutableStateOf<DataCategories?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogMode = CategoryDialogMode.ADD
                    showDialog = true
                    // selectedCategory = null
                },
                containerColor = colorResource(R.color.morado_oscuro),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Category"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        if (showDialog) {
            CategoryDialog(
                mode = dialogMode,
                category = selectedCategory,
                onDismiss = {
                    showDialog = false
                },
                onConfirm = { name ->
                    when (dialogMode) {
                        CategoryDialogMode.ADD -> {
                            onAdd(
                                DataCategories(
                                    categoryId = System.currentTimeMillis().toString(),
                                    categoryName = name
                                )
                            )
                        }

                        CategoryDialogMode.EDIT -> {
                            selectedCategory?.let {
                                onUpdate(
                                    it.copy(
                                        categoryName = name
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }

        CategoryContent(
            modifier = Modifier.padding(padding),
            state = state,
            onEdit = { category ->
                dialogMode = CategoryDialogMode.EDIT
                selectedCategory = category
                showDialog = true
            },
            onDelete = onDelete
        )
    }
}

@Composable
fun CategoryContent(
    modifier: Modifier,
    state: CategoryState,
    onEdit: (DataCategories) -> Unit,
    onDelete: (DataCategories) -> Unit
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
                key = { it.categoryId }) { category ->
                CategoryCard(
                    category = category,
                    onEdit = {
                        onEdit(category)
                    },
                    onDelete = {
                        onDelete(category)
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: DataCategories,
    onEdit: (DataCategories) -> Unit = {},
    onDelete: (DataCategories) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
                text = category.categoryName
            )
            IconButton(onClick = {
                onEdit(category)
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Category"
                )
            }

            IconButton(onClick = {
                onDelete(category)
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Category"
                )
            }
        }
    }
}

@Composable
fun CategoryDialog(
    mode: CategoryDialogMode,
    category: DataCategories?,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var categoryName by remember(category?.categoryId) {
        mutableStateOf(category?.categoryName ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (mode == CategoryDialogMode.ADD) "Add Category" else "Edit Category"
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.Center) {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = {
                        Text(text = "Category Name")
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
                    onConfirm(categoryName)
                },
                enabled = categoryName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.verde_oscuro)
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
                    containerColor = colorResource(R.color.rojo_oscuro)
                )
            ) {
                Text(
                    text = "Cancel"
                )
            }
        }
    )
}

