package com.parg3v.tz_effective.view.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.components.CustomOutlinedTextField
import com.parg3v.tz_effective.components.PhoneNumberTextField
import com.parg3v.tz_effective.navigation.Screen
import com.parg3v.tz_effective.ui.theme.Grey

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {

    val name by viewModel.nameState.collectAsStateWithLifecycle()
    val surname by viewModel.surnameState.collectAsStateWithLifecycle()
    val phone by viewModel.phoneState.collectAsStateWithLifecycle()

    LoginScreenUI(
        controller = navController,
        name = name,
        nameInputChange = viewModel::validateName,
        surname = surname,
        surnameInputChange = viewModel::validateSurname,
        phone = phone,
        phoneInputChange = viewModel::validatePhone
    )
}

@Composable
fun LoginScreenUI(
    controller: NavController,
    name: String,
    nameInputChange: (String) -> Unit,
    surname: String,
    surnameInputChange: (String) -> Unit,
    phone: String,
    phoneInputChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_login))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = dimensionResource(id = R.dimen.padding_login_top)),

            ) {
            CustomOutlinedTextField(
                value = name,
                onValueChange = { nameInputChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_login)),
                placeholder = { Text(text = stringResource(R.string.name)) }
            )

            CustomOutlinedTextField(
                value = surname,
                onValueChange = { surnameInputChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_login)),
                placeholder = { Text(text = stringResource(R.string.surname)) }
            )
            PhoneNumberTextField(
                value = phone, onValueChange = phoneInputChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_login))
            )

            Button(
                onClick = { controller.navigate(Screen.HomeScreen.route) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(disabledContainerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(20)
            ) {
                Text(text = stringResource(R.string.login), color = Color.White)
            }
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            text = stringResource(R.string.message),
            textAlign = TextAlign.Center,
            color = Grey
        )
    }

}
