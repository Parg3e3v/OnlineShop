package com.parg3v.tz_effective.view.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.parg3v.tz_effective.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {

    LoginScreenUI(controller = navController)
}

@Composable
fun LoginScreenUI(controller: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Login", modifier = Modifier
            .align(Alignment.Center)
            .clickable { controller.navigate(Screen.HomeScreen.route) })
    }
}
