package com.parg3v.tz_effective.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.LightGrey
import com.parg3v.tz_effective.ui.theme.Tz_effectiveTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: () -> String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false
) {

    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                onValueChange("")
            },
        ) {
            Icon(
                painterResource(id = R.drawable.icon_clear), contentDescription = ""
            )
        }
    }

    OutlinedTextField(
        value = value(),
        onValueChange = { onValueChange(it) },
        modifier = modifier.aspectRatio(6.8F),
        singleLine = true,
        shape = RoundedCornerShape(percent = integerResource(id = R.integer.ui_round_percentage)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            placeholderColor = Grey,
            containerColor = LightGrey,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        placeholder = placeholder,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = if (value().isNotBlank()) trailingIconView else null,
        isError = isError
    )
}

@Preview
@Composable
fun CustomOutlinedTextFieldPreview() {
    Tz_effectiveTheme { CustomOutlinedTextField(value = { "" }, onValueChange = {}) }
}

