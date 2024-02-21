package com.parg3v.tz_effective.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.model.BottomNavItem
import com.parg3v.tz_effective.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    items: List<BottomNavItem> = listOf(
        BottomNavItem.Home,
        BottomNavItem.Catalog,
        BottomNavItem.Cart,
        BottomNavItem.Discount,
        BottomNavItem.Account
    ),
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    var bottomBarState by rememberSaveable { (mutableStateOf(false)) }
    var topBarShareButtonState by rememberSaveable { (mutableStateOf(false)) }
    var topBarBackButtonState by rememberSaveable { (mutableStateOf(false)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val topAppBarTitle = Screen.getTitleByRoute(navBackStackEntry?.destination?.route)

    when (navBackStackEntry?.destination?.route) {
        Screen.LoginScreen.route -> {
            bottomBarState = false
            topBarBackButtonState = false
            topBarShareButtonState = false
        }

        Screen.FavouritesScreen.route -> {
            bottomBarState = true
            topBarBackButtonState = true
            topBarShareButtonState = false
        }

        "${Screen.ProductScreen.route}/{productId}" -> {
            bottomBarState = true
            topBarBackButtonState = true
            topBarShareButtonState = true
        }

        else -> {
            bottomBarState = true
            topBarBackButtonState = false
            topBarShareButtonState = false
        }
    }

    Box {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                AnimatedVisibility(
                    visible = bottomBarState,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    content = {
                        BottomNavigationBar(
                            items = items,
                            navController = navController,
                            modifier = Modifier,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    })
            },
            topBar = {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(initialOffsetY = { -it }),
                    exit = slideOutVertically(targetOffsetY = { -it }),
                    content = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    topAppBarTitle.orEmpty(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {
                                if (topBarBackButtonState) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            actions = {
                                if (topBarShareButtonState) {
                                    IconButton(onClick = {  }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.icon_share),
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            scrollBehavior = scrollBehavior
                        )
                    }
                )
            }
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}