package com.example.adminlibraryapp.presentation.ui.book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminlibraryapp.R
import com.example.adminlibraryapp.data.remote.models.DataBooks
import com.example.adminlibraryapp.presentation.ui.state.BookState
import com.example.adminlibraryapp.utils.Constants

enum class BookDialogMode {
    ADD,
    EDIT
}

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
                mode = dialogMode,
                book = bookSelected,
                onDismiss = {
                    showDialog = false
                },
                onConfirm = {
                    when (dialogMode) {
                        BookDialogMode.ADD -> {
                            onAddBok(
                                DataBooks(
                                    isbn = "",
                                    cover = "",
                                    bookName = "",
                                    author = "",
                                    description = "",
                                    editorial = "",
                                    releaseYear = "",
                                    edition = "",
                                    stock = "",
                                    category = ""
                                )
                            )
                        }

                        BookDialogMode.EDIT -> {
                        }
                    }
                }
            )
        }

        BookContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            mode = dialogMode,
            onEdit = { book ->
                dialogMode = BookDialogMode.EDIT
                bookSelected = book
                showDialog = true
            },
            onDelete = onDeleteBook
        )
    }
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
    mode: BookDialogMode,
    onEdit: (book: DataBooks) -> Unit,
    onDelete: (book: DataBooks) -> Unit
) {
    var textSearch by remember { mutableStateOf("") }
    val filteredBooks = remember(state.books, textSearch) {
        state.books.filter { book ->
            book.bookName.lowercase().contains(textSearch)
        }
    }
    //DialogLoan validate if is add

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
                    onEdit = {
                        onEdit(book)
                    },
                    onDelete = {
                        onDelete(book)
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
    onEdit: () -> Unit,
    onDelete: () -> Unit
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
        ){

        }
    }
}

@Composable
fun DialogAddEditBook(
    mode: BookDialogMode,
    book: DataBooks?,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

}
