package com.parg3v.tz_effective.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.LightGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField(
    valueProvider: () -> String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val placeholder: String = if (isFocused) {
        stringResource(R.string.phone_mask)
    } else {
        stringResource(R.string.phone_number)
    }

    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                onValueChange("")
            },
        ) {
            Icon(
                painterResource(id = R.drawable.icon_clear),
                contentDescription = ""
            )
        }
    }

    OutlinedTextField(
        value = valueProvider(),
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .aspectRatio(6.8F)
            .onFocusChanged { isFocused = it.isFocused },
        singleLine = true,
        shape = RoundedCornerShape(percent = integerResource(id = R.integer.ui_round_percentage)),
        placeholder = { Text(placeholder) },
        visualTransformation = {
            if (it.isBlank() && isFocused) {
                phoneNumberInputFormatter(it)
            } else if (it.isBlank()) {
                TransformedText(it, OffsetMapping.Identity)
            } else {
                phoneNumberInputFormatter(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            placeholderColor = Grey,
            containerColor = LightGrey,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        trailingIcon = if (valueProvider().isNotBlank()) trailingIconView else null
    )
}

private fun phoneNumberInputFormatter(text: AnnotatedString): TransformedText {
    val mask = "+7 xxx-xxx-xx-xx"

    val trimmed = if (text.text.length >= 10) text.text.substring(0..9) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        append("+7 ")
        for (i in trimmed.indices) {
            append(trimmed[i])
            if (i == 2 || i == 5 || i == 7) {
                append("-")
            }
        }
        pushStyle(SpanStyle(color = Grey))
        append(mask.takeLast(mask.length - length))
        toAnnotatedString()
    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset == 0 -> return 3
                offset <= 3 -> offset + 3
                offset <= 6 -> offset + 4
                offset <= 8 -> offset + 5
                else -> offset + 6
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return text.length
        }
    }
    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
}

@Preview
@Composable
fun PhoneFieldPreview() {
    PhoneNumberTextField(valueProvider = { "1234567890" }, onValueChange = {})
}