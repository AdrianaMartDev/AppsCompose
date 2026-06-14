package com.example.admrestaurant.presentation.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.admrestaurant.AppTopBar
import com.example.admrestaurant.R
import com.example.admrestaurant.domain.model.Category
import com.example.admrestaurant.utils.Constants

@Composable
fun CategoryRoute(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoryScreen(
        state = state,
        onIntent = viewModel::processIntent,
        onBack = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    state: CategoryState,
    onIntent: (CategoryIntent) -> Unit,
    onBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Categorías",
                onBack = onBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
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
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        if (showDialog) {
            AddCategoryDialog(
                onDismiss = {
                    showDialog = false
                },
                onConfirm = { nameCategory ->
                    showDialog = false
                    onIntent(CategoryIntent.AddCategory(nameCategory))
                }
            )
        }

        CategoryContent(
            it,
            state,
            onDelete = { nameCategory ->
                onIntent(CategoryIntent.DeleteCategory(nameCategory))
            }
        )
    }
}

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var nameCategory by remember { mutableStateOf("") }

    AlertDialog(
        title = {
            Text(text = "Add Category")
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "The image from category must be uploaded from a computer to the server.\n" +
                            "Call TI for this action."
                )
                OutlinedTextField(
                    value = nameCategory,
                    onValueChange = { nameCategory = it },
                    label = {
                        Text(text = "Category name")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    maxLines = 1
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
            ) {
                Text(
                    text = "Cancel",
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(nameCategory) },
                enabled = nameCategory.isNotBlank()
            ) {
                Text(text = "Accept")
            }

        },
        onDismissRequest = { onDismiss() },
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CategoryContent(
    paddingValues: PaddingValues,
    state: CategoryState,
    onDelete: (String) -> Unit
) {

    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.error != null -> {
            Text(text = state.error)
        }

        else -> {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                columns = GridCells.Fixed(2),
            ) {
                items(state.categories) { category ->
                    CategoryItem(
                        category = category,
                        onDelete = {
                            onDelete(category.nameCategory)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CategoryItem(
    category: Category,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.golden_valley)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier
                    .width(30.dp)
                    .padding(end = 8.dp)
                    .align(Alignment.TopEnd),
                onClick = {
                    onDelete()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_bote_basura),
                    tint = Color.Unspecified,
                    contentDescription = "Delete"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            GlideImage(
                modifier = Modifier
                    .width(220.dp)
                    .height(200.dp)
                    .padding(top = 8.dp),
                contentScale = ContentScale.Fit,
                model = "${Constants.PAT_IMG_CATEGORIES}${category.imageCategory}",
                contentDescription = "Category",
                alignment = Alignment.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.dark_gray))
                    .padding(12.dp),
                text = category.nameCategory.uppercase(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 26.sp,
                    color = colorResource(id = R.color.secret_love)
                )
            )
        }
    }
}