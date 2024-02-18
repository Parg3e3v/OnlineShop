package com.parg3v.tz_effective.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.LightGrey
import com.parg3v.tz_effective.ui.theme.Typography

@Composable
fun AccountMenuButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    iconTint: Color = Color.Black,
    title: String,
    content: String? = null,
    buttonPainter: Painter = painterResource(id = R.drawable.icon_next),
    onlyButtonClickable: Boolean = false,
    onCLick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(integerResource(id = R.integer.ui_round_percentage)))
            .background(color = LightGrey)
            .aspectRatio(7F)
            .then(if (!onlyButtonClickable) Modifier.clickable { onCLick() } else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon, contentDescription = null, tint = iconTint,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_account_button))
        )
        Column(
            modifier = Modifier
                .weight(1F)
                .padding(start = dimensionResource(id = R.dimen.padding_account_button_title_horizontal))
        ) {
            Text(text = title, style = Typography.titleMedium)
            content?.let {
                Text(text = content, style = Typography.bodySmall, color = Grey)
            }
        }
        IconButton(
            onClick = onCLick,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_account_button))
        ) {
            Icon(painter = buttonPainter, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun AccountMenuButtonPreview() {
    AccountMenuButton(
        icon = painterResource(id = R.drawable.icon_discount), title = "title", content = ""
    )
}