package com.parg3v.tz_effective.view.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FavouritesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Favourites", modifier = Modifier.align(Alignment.Center))
    }
}
