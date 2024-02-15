package com.parg3v.tz_effective.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.view.account.AccountScreen
import com.parg3v.tz_effective.view.cart.CartScreen
import com.parg3v.tz_effective.view.catalog.CatalogScreen
import com.parg3v.tz_effective.view.discount.DiscountScreen
import com.parg3v.tz_effective.view.favourites.FavouritesScreen
import com.parg3v.tz_effective.view.home.HomeScreen
import com.parg3v.tz_effective.view.login.LoginScreen
import com.parg3v.tz_effective.view.product.ProductScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState
) {
    val slideIn = slideInHorizontally(
        initialOffsetX = { -300 },
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(animationSpec = tween(300))

    val slideOut = slideOutHorizontally(
        targetOffsetX = { -300 },
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(300))

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route,
        modifier = Modifier.padding(paddingValues)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_login))
    ) {
        composable(
            route = Screen.LoginScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.HomeScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.CatalogScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }
        ) {
            CatalogScreen(navController = navController)
        }
        composable(
            route = Screen.CartScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }
        ) {
            CartScreen(navController = navController)
        }
        composable(
            route = Screen.DiscountScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }
        ) {
            DiscountScreen(navController = navController)
        }
        composable(
            route = Screen.AccountScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }
        ) {
            AccountScreen(navController = navController)
        }
        composable(
            route = Screen.FavouritesScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }
        ) {
            FavouritesScreen(navController = navController)
        }
        composable(
            route = Screen.ProductScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }
        ) {
            ProductScreen(navController = navController)
        }
    }
}