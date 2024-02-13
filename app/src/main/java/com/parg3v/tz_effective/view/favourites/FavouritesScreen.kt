package com.parg3v.tz_effective.view.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun FavouritesScreen(navController: NavController) {

    FavouritesScreenUI(controller = navController)
}

@Composable
fun FavouritesScreenUI(controller: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Favourites", modifier = Modifier.align(Alignment.Center))
    }
}
