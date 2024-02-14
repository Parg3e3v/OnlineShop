package com.parg3v.tz_effective.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.parg3v.tz_effective.ui.theme.LightGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable() (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier.aspectRatio(6.8F),
        singleLine = true,
        shape = RoundedCornerShape(percent = 20),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = LightGrey,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        placeholder = placeholder,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation
    )
}

@Preview
@Composable
fun Preview() {
    CustomOutlinedTextField(value = "", onValueChange = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier,
        singleLine = true,
        placeholder = { Text("+7 xxx-xxx-xx-xx") },
        visualTransformation = {
            if (it.isBlank()) {
                TransformedText(it, OffsetMapping.Identity)
            } else {
                phoneNumberInputFormatter(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    )
}

private fun phoneNumberInputFormatter(text: AnnotatedString): TransformedText {

    val mask = "+7 xxx-xxx-xx-xx"

    val trimmed = if (text.text.length >= 10) text.text.substring(0..9) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            if (i == 0) {
                append("+7 ")
            }
            append(trimmed[i])
            if (i == 2 || i == 5 || i == 7) {
                append("-")
            }
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(mask.takeLast(mask.length - length))
        toAnnotatedString()
    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset == 0 -> return 0
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