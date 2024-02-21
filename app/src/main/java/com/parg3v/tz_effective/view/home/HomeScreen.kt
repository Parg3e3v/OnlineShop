package com.parg3v.tz_effective.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.parg3v.tz_effective.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Home", modifier = Modifier
            .align(Alignment.Center)
            .clickable { navController.navigate(Screen.ProductScreen.route) })
    }
}