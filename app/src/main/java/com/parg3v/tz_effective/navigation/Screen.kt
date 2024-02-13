package com.parg3v.tz_effective.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("home_screen")
    object CatalogScreen : Screen("catalog_screen")
    object CartScreen : Screen("cart_screen")
    object DiscountScreen : Screen("discount_screen")
    object ProductScreen : Screen("product_screen")
    object AccountScreen : Screen("account_screen")
    object FavouritesScreen : Screen("favourites_screen")
}