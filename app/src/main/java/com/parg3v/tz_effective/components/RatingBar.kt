package com.parg3v.tz_effective.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.ui.theme.Orange

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Orange
) {

    var isHalfStar = (rating % 1) != 0.0

    Row {
        for (index in 1..stars) {
            Icon(
                painter =
                if (index <= rating) {
                    painterResource(id = R.drawable.icon_star_full)
                } else {
                    if (isHalfStar) {
                        isHalfStar = false
                        painterResource(id = R.drawable.icon_star_half)
                    } else {
                        painterResource(id = R.drawable.icon_star_empty)
                    }
                },
                contentDescription = null,
                tint = starsColor,
                modifier = modifier
            )
        }
    }
}