package com.parg3v.tz_effective

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.parg3v.tz_effective.components.CustomScaffold
import com.parg3v.tz_effective.navigation.Navigation
import com.parg3v.tz_effective.ui.theme.Tz_effectiveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tz_effectiveTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val navController: NavHostController = rememberAnimatedNavController()
                CustomScaffold(navController = navController, snackbarHostState) { paddingValues ->
                    Navigation(navController, paddingValues, snackbarHostState)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Tz_effectiveTheme {
        Greeting("Android")
    }
}