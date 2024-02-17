package com.parg3v.tz_effective.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parg3v.tz_effective.ui.theme.Grey

@OptIn(ExperimentalTextApi::class)
@Composable
fun CustomStrikeTextView(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = TextStyle.Default,
    color: Color = Grey
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = remember(text) {
        textMeasurer.measure(text, style)
    }

    Canvas(
        modifier = modifier.size(
            (textLayoutResult.size.width / 2).dp,
            (textLayoutResult.size.height / 2).dp
        )
    ) {
        val rect = Rect(Offset.Zero, size)

        val textOffset = Offset(
            x = center.x - textLayoutResult.size.width / 2,
            y = center.y - textLayoutResult.size.height / 2
        )

        drawText(
            textMeasurer = textMeasurer,
            text = text,
            style = style.copy(color = color),
            topLeft = textOffset
        )

        val lineStart = Offset(
            x = textOffset.x + textLayoutResult.size.width,
            y = textOffset.y
        )
        val lineEnd = Offset(
            x = textOffset.x,
            y = textOffset.y + textLayoutResult.size.height
        )
        rotate(degrees = 20f, rect.center) {

            drawLine(
                color = color,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 7f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Preview
@Composable
fun CustomStrikeTextViewPreview() {
    CustomStrikeTextView(text = "749 ₽")
}