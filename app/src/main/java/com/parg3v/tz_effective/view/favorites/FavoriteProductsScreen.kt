package com.parg3v.tz_effective.view.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.components.ProductItem
import com.parg3v.tz_effective.components.ProductItemPlaceholder
import com.parg3v.tz_effective.components.Shimmer
import com.parg3v.tz_effective.config.Config
import com.parg3v.tz_effective.model.ProductsListState
import com.parg3v.tz_effective.navigation.Screen

@Composable
fun FavoriteProductsScreen(
    controller: NavController,
    itemsListState: ProductsListState
) {

    val listState = rememberLazyGridState()
    if (itemsListState.error.isEmpty()) {
        Shimmer(isLoading = itemsListState.isLoading, contentAfterLoading = {
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_catalog_items_top)
                    ),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.arrangement_catalog_items)
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.arrangement_catalog_items)
                )
            ) {
                items(itemsListState.data) { product ->
                    ProductItem(images = Config.IMAGES_BY_ID[product.id]!!.map {
                        painterResource(
                            id = it
                        )
                    },
                        product = product,
                        onClick = { controller.navigate(Screen.ProductScreen.withArgs(product.id)) })
                }
            }
        }, loadingComposable = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_catalog_items_top)
                    ), verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.arrangement_catalog_items)
                ), horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.arrangement_catalog_items)
                )
            ) {
                items(8) {
                    ProductItemPlaceholder()
                }
            }
        })
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Error: ${itemsListState.error}",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}