package com.parg3v.tz_effective.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
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
    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val topAppBarTitle = navBackStackEntry?.arguments?.getString("topAppBarTitle")

    bottomBarState = when (navBackStackEntry?.destination?.route) {
        Screen.LoginScreen.route -> {
            false
        }

        else -> {
            true
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
            // TODO: top app bar
//            topBar = {
//                AnimatedVisibility(
//                    visible = !bottomBarState,
//                    enter = slideInVertically(initialOffsetY = { -it }),
//                    exit = slideOutVertically(targetOffsetY = { -it }),
//                    content = {
//                        CenterAlignedTopAppBar(
//                            title = {
//                                Text(
//                                    topAppBarTitle.orEmpty(),
//                                    maxLines = 1,
//                                    overflow = TextOverflow.Ellipsis
//                                )
//                            },
//                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
//                            navigationIcon = {
//                                IconButton(onClick = { navController.popBackStack() }) {
//                                    Icon(
//                                        imageVector = Icons.Filled.ArrowBack,
//                                        contentDescription = stringResource(id = R.string.back)
//                                    )
//                                }
//                            },
//                            scrollBehavior = scrollBehavior
//                        )
//                    }
//                )
//            }
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}