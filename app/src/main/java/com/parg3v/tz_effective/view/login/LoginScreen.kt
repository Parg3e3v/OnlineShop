package com.parg3v.tz_effective.view.login

import android.content.Context
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.components.CustomOutlinedTextField
import com.parg3v.tz_effective.components.PhoneNumberTextField
import com.parg3v.tz_effective.navigation.Screen
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    controller: NavController,
    nameProvider: () -> String,
    nameInputChange: (String) -> Unit,
    validName: Boolean,
    surnameProvider: () -> String,
    surnameInputChange: (String) -> Unit,
    validSurname: Boolean,
    phoneProvider: () -> String,
    phoneInputChange: (String) -> Unit,
    validPhone: Boolean,
    saveData: (Context) -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = dimensionResource(id = R.dimen.padding_login_top))
        ) {
            CustomOutlinedTextField(value = nameProvider,
                onValueChange = nameInputChange,
                isError = if (nameProvider().isNotBlank()) !validName else false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_login)),
                placeholder = { Text(text = stringResource(R.string.name)) })

            CustomOutlinedTextField(value = surnameProvider,
                onValueChange = surnameInputChange,
                isError = if (surnameProvider().isNotBlank()) !validSurname else false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_login)),
                placeholder = { Text(text = stringResource(R.string.surname)) })

            PhoneNumberTextField(
                valueProvider = phoneProvider,
                onValueChange = phoneInputChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_login))
            )

            Button(onClick = { scope.launch { saveData(controller, saveData, context) } },
                enabled = listOf(validName, validSurname, validPhone).all { it },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.secondary
                ),
                shape = RoundedCornerShape(integerResource(id = R.integer.ui_round_percentage))
            ) {
                Text(text = stringResource(R.string.login), color = Color.White)
            }
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.login_message_padidng)),
            text = buildAnnotatedString {
                append(stringResource(R.string.message) + "\n")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append(stringResource(R.string.message_underlined))
                }
            },
            textAlign = TextAlign.Center,
            style = Typography.displaySmall.copy(color = Grey)
        )
    }
}

private suspend fun saveData(
    controller: NavController, update: suspend (Context) -> Unit, context: Context
) {
    controller.navigate(Screen.CatalogScreen.route) {
        popUpTo(0)
    }
    update(context)
}
