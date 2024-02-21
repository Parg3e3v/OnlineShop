package com.parg3v.tz_effective.navigation

sealed class Screen(val route: String, val title: String? = null) {
    object LoginScreen : Screen("login_screen", "Вход")
    object HomeScreen : Screen("home_screen", "Главная")
    object CatalogScreen : Screen("catalog_screen", "Каталог")
    object CartScreen : Screen("cart_screen", "Корзина")
    object DiscountScreen : Screen("discount_screen", "Акции")
    object ProductScreen : Screen("product_screen")
    object AccountScreen : Screen("account_screen", "Личный кабинет")
    object FavoritesScreen : Screen("favorites_screen", "Избранное")

    companion object {
        fun getTitleByRoute(route: String?): String? {
            val matchingScreen = Screen::class.sealedSubclasses.map { it.objectInstance as Screen }
                .find { it.route == route }
            return matchingScreen?.title
        }
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}