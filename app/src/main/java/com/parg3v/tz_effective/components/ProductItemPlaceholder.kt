package com.parg3v.tz_effective.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.ShimmingBackground
import com.parg3v.tz_effective.ui.theme.ShimmingForeground

@Composable
fun ProductItemPlaceholder(
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(1.dp, Grey), modifier = modifier.aspectRatio(0.6F)
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .fillMaxHeight(0.45F)
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .width(30.dp)
                        .aspectRatio(8F)
                        .shimmerEffect()
                )
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .aspectRatio(8F)
                            .shimmerEffect()
                    )
                }

                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .aspectRatio(5F)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.weight(1F))

                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .aspectRatio(5F)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_product_item_add_button))
                        .clip(
                            RoundedCornerShape(
                                topStart = dimensionResource(id = R.dimen.rounded_corner_radius),
                                bottomEnd = dimensionResource(id = R.dimen.rounded_corner_radius)
                            )
                        )
                        .align(alignment = Alignment.End)
                        .fillMaxWidth(0.2F)
                        .aspectRatio(1F)
                        .shimmerEffect()
                )
            }
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = stringResource(id = R.string.text_loading))
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ),
        label = stringResource(id = R.string.text_loading)
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                ShimmingBackground, ShimmingForeground, ShimmingBackground
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

@Preview
@Composable
fun PreviewShimmer() {
    ProductItemPlaceholder()
}

@Composable
fun Shimmer(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    loadingComposable: @Composable () -> Unit
) {
    if (isLoading) {
        loadingComposable()
    } else {
        contentAfterLoading()
    }
}