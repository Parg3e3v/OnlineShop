package com.parg3v.tz_effective.view.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AccountScreen(navController: NavController) {

    AccountScreenUI(controller = navController)
}

@Composable
fun AccountScreenUI(controller: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Account", modifier = Modifier.align(Alignment.Center))
    }
}
