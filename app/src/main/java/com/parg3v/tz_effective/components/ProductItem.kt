package com.parg3v.tz_effective.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.parg3v.domain.model.Feedback
import com.parg3v.domain.model.Info
import com.parg3v.domain.model.Price
import com.parg3v.domain.model.Product
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.LightGrey
import com.parg3v.tz_effective.ui.theme.Orange
import com.parg3v.tz_effective.ui.theme.PinkDark
import com.parg3v.tz_effective.ui.theme.Typography
import com.parg3v.tz_effective.ui.theme.Tz_effectiveTheme


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductItem(
    modifier: Modifier = Modifier, images: List<Painter>, product: Product, onClick: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0)
    Card(
        border = BorderStroke(1.dp, LightGrey),
        modifier = modifier
            .aspectRatio(0.6F)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(dimensionResource(id = R.dimen.padding_product_item))
        ) {

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ImageCarousel(
                    imageSlider = images,
                    pagerState = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.45F)
                )
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(dimensionResource(id = R.dimen.padding_pager_indicator)),
                    activeColor = PinkDark,
                    inactiveColor = Grey,
                    indicatorWidth = dimensionResource(id = R.dimen.pager_indicator_size),
                    indicatorHeight = dimensionResource(id = R.dimen.pager_indicator_size)
                )

                CustomStrikeTextView(
                    text = "${product.price.price} ${product.price.unit}",
                    style = Typography.titleSmall
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${product.price.priceWithDiscount} ${product.price.unit}",
                        style = Typography.labelLarge
                    )
                    Text(
                        text = "-${product.price.discount}%",
                        style = Typography.titleSmall,
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.padding_product_item_discount))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_radius)))
                            .background(PinkDark)
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.padding_discount_text_horizontal),
                                vertical = dimensionResource(id = R.dimen.padding_discount_text_vertical)
                            ),
                        color = Color.White
                    )
                }
                Text(text = product.title, style = Typography.titleMedium)
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = product.subtitle, style = Typography.displaySmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_star),
                        contentDescription = null,
                        tint = Orange
                    )
                    Text(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_product_item_rating)),
                        text = buildAnnotatedString {
                            append("${product.feedback.rating}")
                            withStyle(
                                SpanStyle(
                                    color = Grey
                                )
                            ) {
                                append("(${product.feedback.count})")
                            }
                        },
                        color = Orange,
                        style = Typography.titleSmall
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_product_item_add_button))
                        .clip(
                            RoundedCornerShape(
                                topStart = dimensionResource(id = R.dimen.rounded_corner_radius),
                                bottomEnd = dimensionResource(id = R.dimen.rounded_corner_radius)
                            )
                        )
                        .background(color = PinkDark)
                        .fillMaxWidth(0.2F)
                        .aspectRatio(1F)
                        .align(alignment = Alignment.End)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_plus),
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_favourite_outlined),
                    tint = PinkDark,
                    contentDescription = null
                )
            }


        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    Tz_effectiveTheme {
        ProductItem(
            images = listOf(painterResource(id = R.drawable.img_2251)), product = Product(
                id = "",
                title = "ESFOLIO",
                subtitle = "Пенка для умывания`A`PIEU` `DEEP CLEAN` 200 мл",
                price = Price("749", 35, "489", unit = "₽"),
                feedback = Feedback(1, 1.5),
                tags = emptyList(),
                available = 20,
                description = "",
                info = listOf(Info("", "")),
                ingredients = ""
            )
        ) {}
    }
}

