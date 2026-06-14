package com.example.admrestaurant.presentation.ui.init

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.admrestaurant.R
import com.example.admrestaurant.presentation.ui.navigation.Views

@Composable
fun InitRoute(navController: NavController) {
    InitScreen(
        onNavigate = { route ->
            navController.navigate(route)
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun InitScreen(
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.golden_valley)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CardItem(
            iconItem = R.drawable.icon_lista,
            contentDescriptionItem = "Dish",
            textItem = "Categorías",
            onClick = {
                onNavigate(Views.CategoryView.route)
            }
        )

        CardItem(
            iconItem = R.drawable.icon_platillos,
            contentDescriptionItem = "Dish",
            textItem = "Platos",
            onClick = {
                onNavigate(Views.DishView.route)
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardItem(
    iconItem: Int,
    contentDescriptionItem: String,
    textItem: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            onClick()
        }
    ) {
        GlideImage(
            modifier = Modifier
                .width(250.dp)
                .height(200.dp),
            model = iconItem,
            contentDescription = contentDescriptionItem,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit
        )

        Text(
            modifier = Modifier
                .width(250.dp)
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.atlantic_blue))
                .padding(12.dp),
            text = textItem,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 26.sp,
                color = colorResource(id = R.color.secret_love)
            )
        )
    }
}