package com.example.adminlibraryapp.presentation.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminlibraryapp.R
import com.example.adminlibraryapp.presentation.ui.navigation.Routes
import com.example.adminlibraryapp.utils.Constants

@Composable
fun MenuScreen(
    navController: NavController
) {

    Scaffold(
        floatingActionButton = {
            LogoutFab(navController)
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        MenuContent(
            paddingValues = padding,
            items = Constants.menuItems,
            onItemClick = {
                navController.navigate(it.route)
            }
        )
    }
}

@Composable
fun LogoutFab(
    navController: NavController
) {
    FloatingActionButton(
        onClick = {
            navController.navigate(Routes.LoginScreen.route) {
                popUpTo(0)
            }
        },
        contentColor = colorResource(R.color.gris_oscuro)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Logout"
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuContent(
    paddingValues: PaddingValues,
    items: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(12.dp)
    ) {
        items(items) { item ->
            MenuCard(
                item = item,
                onClick = { onItemClick(item) }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuCard(
    item: MenuItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.padding(12.dp),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                model = "${Constants.URL_ICON_MENU}${item.icon}",
                contentDescription = item.title
            )

            Text(item.title)
        }
    }
}