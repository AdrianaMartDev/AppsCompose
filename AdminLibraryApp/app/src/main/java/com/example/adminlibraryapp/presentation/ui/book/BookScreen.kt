package com.example.adminlibraryapp.presentation.ui.book

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminlibraryapp.R
import com.example.adminlibraryapp.data.remote.models.DataBooks
import com.example.adminlibraryapp.data.remote.models.DataLoans
import com.example.adminlibraryapp.presentation.ui.state.BookFormState
import com.example.adminlibraryapp.presentation.ui.state.BookState
import com.example.adminlibraryapp.utils.Constants
import java.time.format.DateTimeFormatter

enum class BookDialogMode {
    ADD,
    EDIT
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookRoute(viewModel: BookViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    BookScreen(
        state = state,
        onAddBok = viewModel::addBook,
        onUpdateBook = viewModel::editBook,
        onDeleteBook = viewModel::deleteBook
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookScreen(
    state: BookState,
    onAddBok: (book: DataBooks) -> Unit,
    onUpdateBook: (book: DataBooks) -> Unit,
    onDeleteBook: (book: DataBooks) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    var dialogMode by remember { mutableStateOf(BookDialogMode.ADD) }
    var bookSelected by remember { mutableStateOf<DataBooks?>(null) }

    var openDialogLoan by remember { mutableStateOf(false) }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogMode = BookDialogMode.ADD
                    showDialog = true
                },
                containerColor = colorResource(id = R.color.morado_oscuro),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        if (showDialog) {
            DialogAddEditBook(
                state = state,
                mode = dialogMode,
                book = bookSelected,
                onDismiss = {
                    showDialog = false
                },
                onConfirm = { book ->
                    when (dialogMode) {
                        BookDialogMode.ADD -> {
                            onAddBok(
                                book
                            )
                        }

                        BookDialogMode.EDIT -> {
                            onUpdateBook(book)
                        }
                    }
                    showDialog = false
                }
            )
        }

        if (openDialogLoan) {
            DialogLoanBook(
                onDismiss = {
                    openDialogLoan = false
                },
                onConfirm = { idUser ->
                    openDialogLoan = false
                    val date = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val loan = DataLoans(
                        lendId = System.currentTimeMillis().toString(),
                        userId = idUser,
                        isBn = bookSelected!!.isbn,
                        lendDate = date.toString()
                    )
                },
                book = bookSelected!!
            )
        }

        BookContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onDelete = onDeleteBook,
            onBookDialogMode = {
                dialogMode = it
            },
            onLoanDialogMode = { showDialog, book ->
                openDialogLoan = showDialog
                bookSelected = book
            }
        )
    }
}

@Composable
fun DialogLoanBook(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    book: DataBooks
) {
    var idUser by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Loan Book")
        },
        text = {
            Column {
                OutlinedTextField(
                    modifier = Modifier.padding(vertical = 4.dp),
                    value = book.isbn,
                    onValueChange = { },
                    label = { Text(text = "ISBN") },
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                OutlinedTextField(
                    modifier = Modifier.padding(vertical = 4.dp),
                    value = book.bookName,
                    onValueChange = { },
                    label = { Text(text = "BOOK") },
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                OutlinedTextField(
                    modifier = Modifier.padding(vertical = 4.dp),
                    value = idUser,
                    onValueChange = { idUser = it },
                    label = { Text(text = "ID user to loan") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(idUser)
                },
                enabled = idUser.isNotBlank(),
            ) {
                Text(text = "Loan")
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

        }
    )
}

@Composable
fun SearchBooks(
    textSearch: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = textSearch,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(text = "Search book")
        },
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 20.sp
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun BookContent(
    modifier: Modifier,
    state: BookState,
    onBookDialogMode: (BookDialogMode) -> Unit,
    onLoanDialogMode: (Boolean, DataBooks?) -> Unit,
    onDelete: (book: DataBooks) -> Unit
) {
    var textSearch by remember { mutableStateOf("") }
    val filteredBooks = remember(state.books, textSearch) {
        state.books.filter { book ->
            book.bookName.lowercase().contains(textSearch)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        SearchBooks(textSearch, onValueChange = { textSearch = it })

        LazyVerticalGrid(
            modifier = Modifier.padding(4.dp),
            columns = GridCells.Fixed(2),
        ) {
            items(
                items = filteredBooks,
                key = { it.isbn }
            ) { book ->
                BookCard(
                    book = book,
                    onDelete = {
                        onDelete(book)
                    },
                    onDialogBookMode = {
                        onBookDialogMode(it)
                    },
                    onLoanDialogMode = { showDialog ->
                        onLoanDialogMode(showDialog, book)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookCard(
    book: DataBooks,
    onDelete: () -> Unit,
    onDialogBookMode: (BookDialogMode) -> Unit,
    onLoanDialogMode: (Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        GlideImage(
            model = "${Constants.URL_ICON_MENU}${book.cover}",
            contentDescription = "Book cover",
            contentScale = ContentScale.Fit
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = book.bookName,
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onLoanDialogMode(true)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    onDialogBookMode(BookDialogMode.EDIT)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }

            IconButton(
                onClick = {
                    onDelete()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddEditBook(
    mode: BookDialogMode,
    book: DataBooks?,
    onDismiss: () -> Unit,
    onConfirm: (DataBooks) -> Unit,
    state: BookState
) {

    var formState by remember {
        mutableStateOf(
            BookFormState(
                isbn = book?.isbn ?: "",
                bookName = book?.bookName ?: "",
                description = book?.description ?: "",
                publicationYear = book?.releaseYear ?: "",
                edition = book?.edition ?: "",
                stock = book?.stock ?: "",
                author = book?.author ?: "Select author",
                editorial = book?.editorial ?: "Select editorial",
                category = book?.category ?: "Select category"
            )
        )
    }

    var showCategory by remember { mutableStateOf(false) }
    var showAuthor by remember { mutableStateOf(false) }
    var showEditorial by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (mode == BookDialogMode.ADD) "Add Book" else "Edit Book"
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                BookTextField(
                    value = formState.isbn,
                    label = "ISBN",
                    onValueChange = {
                        formState = formState.copy(
                            isbn = it
                        )
                    }
                )

                BookTextField(
                    value = formState.bookName,
                    label = "Book name",
                    onValueChange = {
                        formState = formState.copy(
                            bookName = it
                        )
                    }
                )

                BookTextField(
                    value = formState.publicationYear,
                    label = "Publication Year",
                    onValueChange = {
                        formState = formState.copy(
                            publicationYear = it
                        )
                    }

                )

                ExposedDropDialog(
                    expanded = showAuthor,
                    onExpandedChange = {
                        showAuthor = it
                    },
                    items = state.authors,
                    selectedValue = formState.author,
                    onItemSelected = { author ->
                        formState = formState.copy(
                            author = author.authorName
                        )
                    },
                    itemLabel = { author ->
                        author.authorName
                    }
                )

                ExposedDropDialog(
                    expanded = showEditorial,
                    onExpandedChange = {
                        showEditorial = it
                    },
                    items = state.editorials,
                    selectedValue = formState.editorial,
                    onItemSelected = { editorial ->
                        formState = formState.copy(
                            editorial = editorial.editorialName
                        )
                    },
                    itemLabel = { editorial ->
                        editorial.editorialName
                    }
                )

                ExposedDropDialog(
                    expanded = showCategory,
                    onExpandedChange = {
                        showCategory = it
                    },
                    items = state.categories,
                    selectedValue = formState.category,
                    onItemSelected = { category ->
                        formState = formState.copy(
                            category = category.categoryName
                        )
                    },
                    itemLabel = { category ->
                        category.categoryName
                    }
                )

                BookTextField(
                    value = formState.description,
                    label = "Description",
                    onValueChange = {
                        formState = formState.copy(
                            description = it
                        )
                    }

                )

                BookTextField(
                    value = formState.edition,
                    label = "Edition",
                    onValueChange = {
                        formState = formState.copy(
                            edition = it
                        )
                    }

                )

                BookTextField(
                    value = formState.stock,
                    label = "Stock",
                    onValueChange = {
                        formState = formState.copy(
                            stock = it
                        )
                    }

                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        DataBooks(
                            isbn = formState.isbn,
                            cover = "${formState.isbn}.png",
                            bookName = formState.bookName,
                            author = formState.author,
                            description = formState.description,
                            editorial = formState.editorial,
                            releaseYear = formState.bookName,
                            edition = formState.edition,
                            stock = formState.stock,
                            category = formState.category
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.verde_oscuro)
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
                    contentColor = colorResource(id = R.color.rojo_oscuro)
                )
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ExposedDropDialog(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    items: List<T>,
    selectedValue: String,
    onItemSelected: (T) -> Unit,
    itemLabel: (T) -> String
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(vertical = 8.dp),
        expanded = expanded,
        onExpandedChange = { onExpandedChange(it) }
    ) {
        keyboardController?.hide()

        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(itemLabel(item))
                    },
                    onClick = {
                        onItemSelected(item)
                        onExpandedChange(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun BookTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )
}
