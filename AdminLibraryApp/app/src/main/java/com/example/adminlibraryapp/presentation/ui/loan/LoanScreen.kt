package com.example.adminlibraryapp.presentation.ui.loan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adminlibraryapp.data.remote.models.DataLoans
import com.example.adminlibraryapp.presentation.ui.state.LoanState
import java.util.Locale

@Composable
fun LoanRoute(
    viewModel: LoanViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LoanScreen(
        state = state,
        onDelete = viewModel::deleteLoan
    )
}

@Composable
fun LoanScreen(
    state: LoanState,
    onDelete: (DataLoans) -> Unit
) {
    var textSearch by remember { mutableStateOf("") }

    val filteredLoans = remember(state.data, textSearch) {
        state.data.filter { loan ->
            loan.userId.lowercase(Locale.ROOT).contains(textSearch)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        SearchLoan(textSearch, onValueChange = { textSearch = it })

        LazyColumn(
            modifier = Modifier.padding(4.dp)
        ) {
            items(
                items = filteredLoans,
                key = { it.lendId }
            ) { loan ->
                DeleteLoanCard(loan, onDelete)
            }
        }
    }
}

@Composable
fun SearchLoan(
    textSearch: String,
    onValueChange: (String) -> Unit
) {

    TextField(
        value = textSearch,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(text = "Search ID user")
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
fun DeleteLoanCard(
    loan: DataLoans,
    onDelete: (DataLoans) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(text = "Loan ID: ${loan.lendId}")
            Text(text = "Loan User: ${loan.userId}")
            Text(text = "Loan Date: ${loan.lendDate}")
        }
        Button(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                onDelete(loan)
            }
        ) {
            Text(text = "Back loan")
        }

    }
}