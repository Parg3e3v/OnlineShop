package com.parg3v.tz_effective.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parg3v.domain.model.Price
import com.parg3v.domain.model.Product
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.ui.theme.LightGrey
import com.parg3v.tz_effective.ui.theme.PinkDark

@Composable
fun ProductItem(
    modifier: Modifier = Modifier, painter: Painter, product: Product
) {
    Card(
        border = BorderStroke(1.dp, LightGrey),
        modifier = modifier
            .aspectRatio(0.6F)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(dimensionResource(id = R.dimen.padding_product_item))
        ) {

            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5F)
                )

                CustomStrikeTextView(
                    text = "${product.price?.price} ${product.price?.unit}",
                    style = MaterialTheme.typography.titleSmall
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${product.price?.priceWithDiscount} ${product.price?.unit}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "-${product.price?.discount}%",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20))
                            .background(PinkDark)
                            .padding(horizontal = 6.dp, vertical = 3.dp),
                        color = Color.White
                    )
                }
                Text(text = product.title, style = MaterialTheme.typography.titleMedium)
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
    ProductItem(
        painter = painterResource(id = R.drawable.img_54a876a5_2205_48ba_9498_cfecff4baa6e_1),
        product = Product(
            id = "",
            title = "ESFOLIO",
            subtitle = "",
            price = Price("749", 35, "489", unit = "₽"),
            feedback = null,
            tags = emptyList(),
            available = 20,
            description = "",
            info = null,
            ingredients = ""
        )
    )
}