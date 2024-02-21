package com.parg3v.tz_effective.view.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.components.AccountMenuButton
import com.parg3v.tz_effective.navigation.Screen
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.Orange
import com.parg3v.tz_effective.ui.theme.PinkDark

@Composable
fun AccountScreen(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AccountMenuButton(
            modifier = Modifier.padding(bottom = 16.dp),
            icon = painterResource(id = R.drawable.icon_account),
            title = "Name",
            content = "+7 658 658 65 65",
            buttonPainter = painterResource(id = R.drawable.icon_logout),
            onlyButtonClickable = true
        )

        AccountMenuButton(
            icon = painterResource(id = R.drawable.icon_favourite_outlined),
            iconTint = PinkDark,
            title = stringResource(R.string.favourites),
            content = "1 товар" // TODO: Show favourite products count
        ) {
            navController.navigate(Screen.FavouritesScreen.route)
        }

        AccountMenuButton(
            icon = painterResource(id = R.drawable.icon_shop),
            iconTint = PinkDark,
            title = stringResource(R.string.shops)
        )

        AccountMenuButton(
            icon = painterResource(id = R.drawable.icon_feedback),
            iconTint = Orange,
            title = stringResource(R.string.feedback)
        )

        AccountMenuButton(
            icon = painterResource(id = R.drawable.icon_offer),
            iconTint = Grey,
            title = stringResource(R.string.offer)
        )

        AccountMenuButton(
            icon = painterResource(id = R.drawable.icon_refund),
            iconTint = Grey,
            title = stringResource(R.string.refund)
        )
    }
}

