package com.parg3v.tz_effective.components


import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.parg3v.tz_effective.model.BottomNavItem
import com.parg3v.tz_effective.ui.theme.SelectedItem

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                label = { Text(text = item.title) },
                modifier = modifier,
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = item.title
                    )
                },
                colors = androidx.compose.material3.NavigationBarItemDefaults
                    .colors(
                        selectedIconColor = SelectedItem,
                        indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            LocalAbsoluteTonalElevation.current
                        )
                    )
            )
        }
    }
}