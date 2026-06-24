package com.example.admrestaurant.presentation.ui.dish

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.admrestaurant.AppTopBar
import com.example.admrestaurant.R
import com.example.admrestaurant.domain.model.Dish


enum class DishDialogMode {
    Add,
    Edit
}

@Composable
fun DishRoute(
    navController: NavController,
    viewModel: DishViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DishScreen(
        state = state,
        onIntent = viewModel::processIntent,
        onBack = {
            navController.popBackStack()
        }
    )
}

@Composable
fun DishScreen(
    state: DishState,
    onIntent: (DishIntent) -> Unit,
    onBack: () -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    var dishAddUpdate by remember { mutableStateOf(DishDialogMode.Add) }
    var dishEdit by remember { mutableStateOf(Dish()) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Platos",
                onBack = onBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dishAddUpdate = DishDialogMode.Add
                    showDialog = true
                },
                containerColor = colorResource(id = R.color.atlantic_blue)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    ) {
        if (showDialog) {
            DialogDishAddEdit(
                state = state,
                dishDialogMode = dishAddUpdate,
                dish = dishEdit,
                onDismiss = {
                    showDialog = false
                },
                onConfirm = { dish ->
                    if (dishAddUpdate == DishDialogMode.Add) {
                        onIntent(DishIntent.AddDish(dish))
                    } else {
                        onIntent(DishIntent.UpdateDish(dishEdit.nameDish, dish))
                    }
                    showDialog = false
                },
                onGenerateDescription = { dish ->
                    onIntent(DishIntent.GenerateDescription(dish))
                },
                onClearGenerated = {
                    onIntent(DishIntent.ClearGeneratedDescription)
                }
            )
        }

        DishContent(
            paddingValues = it,
            state = state,
            onEdit = { dish ->
                dishAddUpdate = DishDialogMode.Edit
                dishEdit = dish
                showDialog = true
            },
            onDelete = { dish ->
                onIntent(DishIntent.DeleteDish(dish.nameDish))
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDishAddEdit(
    state: DishState,
    dishDialogMode: DishDialogMode,
    dish: Dish,
    onDismiss: () -> Unit,
    onConfirm: (Dish) -> Unit,
    onGenerateDescription: (Dish) -> Unit,   // IA: nuevo callback
    onClearGenerated: () -> Unit             // IA: para limpiar el state tras consumir
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var formState by remember {
        mutableStateOf(
            when (dishDialogMode) {
                DishDialogMode.Add -> DishFormState()
                DishDialogMode.Edit -> DishFormState(
                    nomDish = dish.nameDish,
                    descDish = dish.descDish,
                    priceDish = dish.priceDish.toString(),
                    categoryDish = dish.category
                )
            }
        )
    }

    LaunchedEffect(state.generatedDescription) {
        state.generatedDescription?.let { generated ->
            formState = formState.copy(descDish = generated)
            onClearGenerated()
        }
    }

    AlertDialog(
        title = {
            if (dishDialogMode == DishDialogMode.Add) {
                Text(text = "Add Dish")
            } else {
                Text(text = "Edit Dish")
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = formState.nomDish,
                    onValueChange = {
                        if (dishDialogMode == DishDialogMode.Add) formState =
                            formState.copy(nomDish = it)
                    },
                    label = {
                        Text(text = "Dish name")
                    },
                    enabled = dishDialogMode == DishDialogMode.Add,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    value = formState.descDish,
                    onValueChange = {
                        formState = formState.copy(descDish = it)
                    },
                    label = {
                        Text(text = "Dish description")
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = false
                )

                TextButton(
                    onClick = {
                        onGenerateDescription(
                            Dish(
                                nameDish = formState.nomDish,
                                descDish = formState.descDish,
                                priceDish = formState.priceAsDouble,
                                category = formState.categoryDish
                            )
                        )
                    },
                    enabled = formState.nomDish.isNotBlank() && !state.isGeneratingDescription
                ) {
                    if (state.isGeneratingDescription) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Generando...")
                    } else {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Generar descripción con IA")
                    }
                }

                OutlinedTextField(
                    value = formState.priceDish,
                    onValueChange = {
                        formState = formState.copy(priceDish = it)
                    },
                    label = {
                        Text(text = "Dish price")
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    expanded = formState.showCategory,
                    onExpandedChange = {
                        formState = formState.copy(showCategory = it)
                    }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                        value = formState.categoryDish,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = formState.showCategory)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = formState.showCategory,
                        onDismissRequest = {
                            formState = formState.copy(showCategory = false)
                        }
                    ) {
                        keyboardController?.hide()
                        state.categories.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = category.nameCategory)
                                },
                                onClick = {
                                    if (category.nameCategory.isNotBlank()) {
                                        formState = formState.copy(
                                            categoryDish = category.nameCategory
                                        )
                                    }
                                    formState = formState.copy(showCategory = false)
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        Dish(
                            nameDish = formState.nomDish,
                            descDish = formState.descDish,
                            priceDish = formState.priceAsDouble,
                            category = formState.categoryDish
                        )
                    )
                },
                enabled = formState.isValid
            ) {
                Text(text = "Accept")
            }
        },
        onDismissRequest = {
            onDismiss()
        }
    )
}


@Composable
fun DishContent(
    paddingValues: PaddingValues,
    state: DishState,
    onEdit: (Dish) -> Unit,
    onDelete: (Dish) -> Unit
) {
    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.error != null -> {
            Text(text = state.error)
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                items(state.dishes) { dish ->
                    ItemDish(
                        dish = dish,
                        onEdit = {
                            onEdit(dish)
                        },
                        onDelete = {
                            onDelete(dish)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemDish(
    dish: Dish,
    onDelete: (Dish) -> Unit,
    onEdit: (Dish) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 12.dp,
                horizontal = 28.dp
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.dark_gray),
            contentColor = colorResource(id = R.color.secret_love)
        )
    ) {
        Column {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp),
                text = dish.nameDish.uppercase(),
                style = TextStyle(
                    fontSize = 16.sp
                )
            )

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Category: ${dish.category}"
            )

            if (dish.descDish.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "Description: ${dish.descDish}"
                )
            }

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Price: $${dish.priceDish}"
            )

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp, bottom = 8.dp)
            ) {
                IconButton(
                    onClick = {
                        onEdit(dish)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_edit),
                        tint = Color.Unspecified,
                        contentDescription = "Edit"
                    )
                }

                IconButton(
                    onClick = {
                        onDelete(dish)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_bote_basura),
                        tint = Color.Unspecified,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
}