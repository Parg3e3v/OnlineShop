package com.parg3v.tz_effective

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
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
                val navController: NavHostController = rememberAnimatedNavController()
                CustomScaffold(navController = navController) { paddingValues ->
                    Navigation(navController, paddingValues)
                }
            }
        }
    }
}