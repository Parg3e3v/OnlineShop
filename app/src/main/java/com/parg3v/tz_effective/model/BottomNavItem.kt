package com.parg3v.tz_effective.model

import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.navigation.Screen

sealed class BottomNavItem(
    val title: String,
    val route: String,
    val icon: Int
) {
    object Home : BottomNavItem("Главная", Screen.HomeScreen.route, R.drawable.icon_home)
    object Catalog : BottomNavItem("Каталог", Screen.CatalogScreen.route, R.drawable.icon_catalog)
    object Cart : BottomNavItem("Корзина", Screen.CartScreen.route, R.drawable.icon_cart)
    object Discount : BottomNavItem("Акции", Screen.DiscountScreen.route, R.drawable.icon_discount)
    object Account : BottomNavItem("Профиль", Screen.AccountScreen.route, R.drawable.icon_account)
}