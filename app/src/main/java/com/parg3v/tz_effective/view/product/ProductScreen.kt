package com.parg3v.tz_effective.view.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.parg3v.tz_effective.components.CustomStrikeTextView
import com.parg3v.tz_effective.components.ImageCarousel
import com.parg3v.tz_effective.components.RatingBar
import com.parg3v.tz_effective.config.Config
import com.parg3v.tz_effective.model.ProductState
import com.parg3v.tz_effective.ui.theme.DarkGrey
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.LightGrey
import com.parg3v.tz_effective.ui.theme.PinkDark
import com.parg3v.tz_effective.ui.theme.PinkLight
import com.parg3v.tz_effective.ui.theme.Typography
import com.parg3v.tz_effective.ui.theme.Tz_effectiveTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductScreen(productState: ProductState) {

    val pagerState = rememberPagerState(initialPage = 0)
    val scrollState = rememberScrollState()

    var descriptionVisibility by remember {
        mutableStateOf(true)
    }

    var ingredientsNeedToBeExpended by remember {
        mutableStateOf(false)
    }
    var ingredientsExpended by remember {
        mutableStateOf(false)
    }

    val animatedMaxLines by animateIntAsState(
        if (ingredientsExpended) Int.MAX_VALUE else 2, tween(durationMillis = 300), label = ""
    )

    productState.data?.let { product ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Box(modifier = Modifier.aspectRatio(1F)) {
                    ImageCarousel(
                        imageSlider = Config.IMAGES_BY_ID[product.id]!!.map {
                            painterResource(id = it)
                        }, pagerState = pagerState
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_question),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    IconButton(
                        onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_favourite_outlined),
                            tint = PinkDark,
                            contentDescription = null
                        )
                    }
                }
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

                Text(
                    text = product.title,
                    style = Typography.headlineMedium.copy(color = Grey),
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_product_screen_top_title))
                )
                Text(
                    text = product.subtitle,
                    style = Typography.headlineLarge,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_product_screen_top_availibility))
                )
                Text(
                    text = pluralStringResource(
                        id = R.plurals.available_count, count = product.available, product.available
                    ),
                    style = Typography.headlineSmall.copy(color = Grey),
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_product_screen_top_small_elements))
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(LightGrey)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.product_page_rating_padding)),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_product_screen_top_small_elements))
                ) {
                    RatingBar(rating = product.feedback.rating)
                    Text(
                        text = "${product.feedback.rating} · " + pluralStringResource(
                            id = R.plurals.feedback_count,
                            count = product.feedback.count,
                            product.feedback.count
                        ), style = Typography.headlineSmall.copy(color = Grey)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.product_page_price_padding)
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_product_screen_top_price))
                ) {
                    Text(
                        text = "${product.price.priceWithDiscount} ${product.price.unit}",
                        style = Typography.titleLarge
                    )

                    CustomStrikeTextView(
                        text = "${product.price.price} ${product.price.unit}",
                        style = Typography.headlineSmall
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
                Text(
                    text = stringResource(R.string.description),
                    style = Typography.headlineMedium,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_product_screen_top_description_title))
                )
                AnimatedVisibility(
                    visible = descriptionVisibility,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_product_screen_top_description))
                ) {
                    Column(
                        modifier = Modifier.scrollable(
                            scrollState, orientation = Orientation.Vertical
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_radius)))
                                .background(LightGrey)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = product.title,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(
                                        horizontal = dimensionResource(id = R.dimen.padding_brand_horizontal),
                                        vertical = dimensionResource(id = R.dimen.padding_brand_vertical)
                                    ),
                                style = Typography.titleMedium
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.icon_next),
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(
                                        horizontal = dimensionResource(id = R.dimen.padding_brand_horizontal),
                                        vertical = dimensionResource(id = R.dimen.padding_brand_vertical)
                                    )
                            )
                        }
                        Text(
                            text = product.description,
                            style = Typography.headlineSmall,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_product_screen_top_description_text))
                        )
                    }
                }

                Text(text = stringResource(if (descriptionVisibility) R.string.hide else R.string.more),
                    style = Typography.displayLarge.copy(color = Grey),
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(), indication = null
                        ) { descriptionVisibility = !descriptionVisibility }
                        .padding(top = dimensionResource(id = R.dimen.padding_product_screen_top_small_elements)))

                Text(
                    text = stringResource(R.string.characteristics),
                    style = Typography.headlineMedium,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_product_screen_top_common_title),
                        bottom = dimensionResource(id = R.dimen.padding_product_screen_top_common_description)
                    )
                )
                product.info.forEach { info ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            style = Typography.headlineSmall.copy(color = DarkGrey),
                            text = info.title,
                            modifier = Modifier.padding(
                                top = dimensionResource(id = R.dimen.padding_info_top),
                                bottom = dimensionResource(id = R.dimen.padding_info_bottom),
                            )
                        )
                        Spacer(modifier = Modifier.weight(1F))
                        Text(
                            style = Typography.headlineSmall.copy(color = DarkGrey),
                            text = info.value,
                            modifier = Modifier.padding(
                                top = dimensionResource(id = R.dimen.padding_info_top),
                                bottom = dimensionResource(id = R.dimen.padding_info_bottom),
                            )
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(LightGrey)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_product_screen_top_common_title),
                        bottom = dimensionResource(id = R.dimen.padding_product_screen_top_common_description)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.ingredients),
                        style = Typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    Icon(
                        painter = painterResource(id = R.drawable.icon_copy),
                        contentDescription = null,
                        tint = Grey
                    )
                }

                Text(
                    text = product.ingredients,
                    style = Typography.headlineSmall,
                    maxLines = animatedMaxLines,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult ->
                        ingredientsNeedToBeExpended = textLayoutResult.hasVisualOverflow
                    },
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_product_screen_top_small_elements))
                )
                if (ingredientsNeedToBeExpended && !ingredientsExpended || ingredientsExpended) {
                    Text(text = stringResource(if (ingredientsExpended) R.string.hide else R.string.more),
                        style = Typography.displayLarge.copy(color = Grey),
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(), indication = null
                        ) {
                            ingredientsExpended = !ingredientsExpended
                        })
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp) // some fixed space
                )

            }

            Button(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_radius)),
                onClick = { },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PinkDark),
            ) {
                Text(
                    text = "${product.price.priceWithDiscount} ${product.price.unit}",
                    style = Typography.titleMedium
                )
                CustomStrikeTextView(
                    text = "${product.price.price} ${product.price.unit}",
                    style = Typography.displaySmall,
                    color = PinkLight
                )
                Spacer(modifier = Modifier.weight(1F))
                Text(text = "Добавить корзину", style = Typography.titleMedium)
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = productState.error, modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun ProductScreenPreview() {
    Tz_effectiveTheme {
        ProductScreen(
            productState = ProductState(
                data = Product(
                    id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
                    title = "ESFOLIO",
                    subtitle = "Пенка для умывания`A`PIEU` `DEEP CLEAN` 200 мл",
                    price = Price("749", 35, "489", unit = "₽"),
                    feedback = Feedback(20, 1.5),
                    tags = emptyList(),
                    available = 20,
                    description = "Лосьон для тела `ESFOLIO` COENZYME Q10 Увлажняющий содержит " + "минеральную воду и соду, способствует глубокому очищению пор от " + "различных загрязнений, контроллирует работу сальных желез, сужает " + "поры. Обладает мягким антиептическим действием, не пересушивает кожу." + " Минеральная вода тонизирует и смягчает кожу.",
                    info = listOf(Info("", "")),
                    ingredients = ""
                )
            )
        )
    }
}
