package com.parg3v.tz_effective.view.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ProductScreen(navController: NavController) {

    ProductScreenUI(controller = navController)
}

@Composable
fun ProductScreenUI(controller: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Product", modifier = Modifier.align(Alignment.Center))
    }
}
