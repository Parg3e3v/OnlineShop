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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.parg3v.domain.model.LoginInfo
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.view.account.AccountScreen
import com.parg3v.tz_effective.view.account.AccountViewModel
import com.parg3v.tz_effective.view.cart.CartScreen
import com.parg3v.tz_effective.view.catalog.CatalogScreen
import com.parg3v.tz_effective.view.catalog.CatalogViewModel
import com.parg3v.tz_effective.view.discount.DiscountScreen
import com.parg3v.tz_effective.view.favorites.FavoriteProductsViewModel
import com.parg3v.tz_effective.view.favorites.FavoritesScreen
import com.parg3v.tz_effective.view.home.HomeScreen
import com.parg3v.tz_effective.view.login.LoginScreen
import com.parg3v.tz_effective.view.login.LoginViewModel
import com.parg3v.tz_effective.view.product.ProductScreen
import com.parg3v.tz_effective.view.product.ProductViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    var loginInfoFetched by remember { mutableStateOf(false) }
    val catalogViewModel: CatalogViewModel = hiltViewModel()
    val sortingType =
        remember { mutableStateOf(R.string.by_popularity) } // not remembering in screen file
    val productViewModel: ProductViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val accountViewModel: AccountViewModel = hiltViewModel()
    val favoriteProductsViewModel: FavoriteProductsViewModel = hiltViewModel()

    val context = LocalContext.current
    val loginInfo by loginViewModel.loginInfoState.collectAsStateWithLifecycle()

    LaunchedEffect(loginViewModel) {
        loginViewModel.getLoginInfo(context)
    }


    val slideIn = slideInHorizontally(
        initialOffsetX = { -300 }, animationSpec = tween(
            durationMillis = 300, easing = FastOutSlowInEasing
        )
    ) + fadeIn(animationSpec = tween(300))

    val slideOut = slideOutHorizontally(
        targetOffsetX = { -300 }, animationSpec = tween(
            durationMillis = 300, easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(300))

    AnimatedNavHost(
        navController = navController,
        startDestination =

        if (loginInfo.data!! != LoginInfo()) {
            Screen.CatalogScreen.route
        } else {
            Screen.LoginScreen.route
        },

        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_login))
    ) {
        composable(route = Screen.LoginScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }) {

            val name by loginViewModel.nameState.collectAsStateWithLifecycle()
            val surname by loginViewModel.surnameState.collectAsStateWithLifecycle()
            val phone by loginViewModel.phoneState.collectAsStateWithLifecycle()
            val validName by loginViewModel.validNameState.collectAsStateWithLifecycle()
            val validSurname by loginViewModel.validSurnameState.collectAsStateWithLifecycle()
            val validPhone by loginViewModel.validPhoneState.collectAsStateWithLifecycle()

            LoginScreen(
                controller = navController,
                nameProvider = { name },
                validName = validName,
                nameInputChange = loginViewModel::validateName,
                surnameProvider = { surname },
                validSurname = validSurname,
                surnameInputChange = loginViewModel::validateSurname,
                phoneProvider = { phone },
                validPhone = validPhone,
                phoneInputChange = loginViewModel::validatePhone,
                saveData = loginViewModel::saveLoginInfo
            )

        }
        composable(route = Screen.HomeScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.CatalogScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }) {

            if (!loginInfoFetched) {
                LaunchedEffect(catalogViewModel) {
                    catalogViewModel.getProducts()
                    loginInfoFetched = true
                }
            }

            val itemsListState by catalogViewModel.productsState.collectAsStateWithLifecycle()
            val selectedOption by catalogViewModel.selectedOption.collectAsStateWithLifecycle()
            val filteredItemsList by catalogViewModel.filteredProductsState.collectAsStateWithLifecycle()

            CatalogScreen(
                controller = navController,
                itemsListState = itemsListState,
                sortingMethod = catalogViewModel::sortBy,
                containsTag = catalogViewModel::containsTag,
                selectedOption = selectedOption,
                filteredItemsListState = filteredItemsList,
                sortingType = sortingType,
                addToFavourites = catalogViewModel::addToFavorites,
                removeFromFavourites = catalogViewModel::deleteFromFavorites,
                matchProductsWithLocalData = catalogViewModel::matchProductsListWithLocalData
            )
        }
        composable(route = Screen.CartScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }) {
            CartScreen()
        }
        composable(route = Screen.DiscountScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }) {
            DiscountScreen()
        }
        composable(route = Screen.AccountScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }) {

            val count by accountViewModel.productsCount.collectAsStateWithLifecycle()
            LaunchedEffect(accountViewModel) {
                accountViewModel.getFavoriteProductsCount()
            }
            AccountScreen(
                navController = navController,
                loginInfo = loginInfo.data ?: LoginInfo(),
                clearData = accountViewModel::deleteLoginInfo,
                count = count
            )
        }
        composable(route = Screen.FavoritesScreen.route,
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }) {

            val itemsList by favoriteProductsViewModel.productsState.collectAsStateWithLifecycle()

            LaunchedEffect(favoriteProductsViewModel) {
                favoriteProductsViewModel.getProducts()
            }

            FavoritesScreen(
                controller = navController,
                itemsListState = itemsList,
                addToFavorites = favoriteProductsViewModel::addToFavorites,
                removeFromFavorites = favoriteProductsViewModel::deleteFromFavorites
            )
        }
        composable(route = "${Screen.ProductScreen.route}/{productId}",
            arguments = listOf(navArgument("productId") {
                type = NavType.StringType
                defaultValue = "-1"
                nullable = false
            }),
            exitTransition = { slideOut },
            popEnterTransition = { slideIn }) { entry ->


            val productId = entry.arguments?.getString("productId")

            productId?.let {

                val productState by productViewModel.productState.collectAsStateWithLifecycle()
                LaunchedEffect(productId) {
                    productViewModel.getProductById(productId)
                }
                ProductScreen(
                    productState = productState,
                    addToFavorites = productViewModel::addToFavorites,
                    removeFromFavorites = productViewModel::deleteFromFavorites
                )
            }
        }
    }
}