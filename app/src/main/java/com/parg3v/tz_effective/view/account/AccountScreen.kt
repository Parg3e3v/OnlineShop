package com.parg3v.tz_effective.view.account

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.parg3v.domain.model.LoginInfo
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.components.AccountMenuButton
import com.parg3v.tz_effective.navigation.Screen
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.LightGrey
import com.parg3v.tz_effective.ui.theme.Orange
import com.parg3v.tz_effective.ui.theme.PinkDark
import com.parg3v.tz_effective.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    navController: NavController,
    loginInfo: LoginInfo,
    clearData: suspend (Context) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AccountMenuButton(
            modifier = Modifier.padding(bottom = 16.dp),
            icon = painterResource(id = R.drawable.icon_account),
            title = "${loginInfo.name} ${loginInfo.surname}",
            content = formatPhoneNumber(loginInfo.phone.toString()),
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
        Spacer(modifier = Modifier.weight(1F))
        Button(
            onClick = {
                logout(
                    clearData = clearData,
                    context = context,
                    navController = navController,
                    scope = scope
                )
            },
            shape = RoundedCornerShape(integerResource(id = R.integer.ui_round_percentage)),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4F)
                .padding(bottom = dimensionResource(id = R.dimen.padding_exit_button)),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGrey,
                contentColor = Color.Black
            )
        ) {
            Text(text = stringResource(R.string.logout), style = Typography.titleMedium)
        }
    }
}

fun logout(
    clearData: suspend (Context) -> Unit,
    context: Context,
    navController: NavController,
    scope: CoroutineScope
) {
    scope.launch {
        clearData(context)
    }
    navController.navigate(Screen.LoginScreen.route)
}

fun formatPhoneNumber(phoneNumber: String): String {
    return "+7 ${phoneNumber.substring(0, 3)}" +
            " ${phoneNumber.substring(3, 6)}" +
            " ${phoneNumber.substring(6, 8)}" +
            " ${phoneNumber.substring(8)}"
}