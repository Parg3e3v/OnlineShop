package com.parg3v.tz_effective.view.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.parg3v.domain.model.Product
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.components.ProductItem
import com.parg3v.tz_effective.components.ProductItemPlaceholder
import com.parg3v.tz_effective.components.Shimmer
import com.parg3v.tz_effective.components.SortingDropdown
import com.parg3v.tz_effective.components.TagSelectionButtons
import com.parg3v.tz_effective.config.Config
import com.parg3v.tz_effective.model.ProductsListState
import com.parg3v.tz_effective.model.SortType
import com.parg3v.tz_effective.navigation.Screen
import com.parg3v.tz_effective.ui.theme.Typography
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    controller: NavController,
    itemsListStateFlow: StateFlow<ProductsListState>,
    sortingMethod: (SortType) -> Unit,
    containsTag: (String) -> Unit,
    selectedOption: String,
    filteredItemsListState: ProductsListState,
    sortingType: MutableState<Int>,
    addToFavourites: (Product) -> Unit,
    removeFromFavourites: (Product) -> Unit
) {
    val listState = rememberLazyGridState()
    val itemsListState by itemsListStateFlow.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {

        if (itemsListState.error.isEmpty()) {
            Row {
                SortingDropdown(sortingMethod, listState, !itemsListState.isLoading, sortingType)
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_catalog_menu_between)),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_filters),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.filters),
                        style = Typography.displayMedium
                    )
                }
            }
            TagSelectionButtons(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_top_tag_menu)),
                tags = Config.TAGS.values.toList(),
                selectedOption = Config.TAGS[selectedOption]!!,
                onClick = containsTag,
                sortingType = sortingType,
                sortingMethod = sortingMethod,
                listState = listState,
                clickable = !itemsListState.isLoading
            )
            Shimmer(isLoading = itemsListState.isLoading, contentAfterLoading = {
                if (itemsListState.data.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(R.string.isempty),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
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
                        items(
                            (if (selectedOption == "all") itemsListState else filteredItemsListState).data
                        ) { product ->
                            ProductItem(
                                images = Config.IMAGES_BY_ID[product.id]!!.map {
                                    painterResource(
                                        id = it
                                    )
                                },
                                product = product,
                                onClick = {
                                    controller.navigate(
                                        Screen.ProductScreen.withArgs(
                                            product.id
                                        )
                                    )
                                },
                                addToFavorites = addToFavourites,
                                removeFromFavorites = removeFromFavourites
                            )
                        }
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
}
//
//@SuppressLint("UnrememberedMutableState")
//@Preview
//@Composable
//fun CatalogScreenUIPreview() {
//    Tz_effectiveTheme {
//        Box(modifier = Modifier.background(Color.White)) {
//            CatalogScreen(
//                controller = NavController(LocalContext.current),
//                itemsListStateFlow = ProductsListState(data = List(8) {
//                    Product(
//                        id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
//                        title = "ESFOLIO",
//                        subtitle = "Пенка для умывания`A`PIEU` `DEEP CLEAN` 200 мл",
//                        price = Price("749", 35, "489", unit = "₽"),
//                        feedback = Feedback(1, 1.5),
//                        tags = emptyList(),
//                        available = 20,
//                        description = "",
//                        info = listOf(Info("", "")),
//                        ingredients = ""
//                    )
//                }),
//                sortingMethod = {},
//                containsTag = {},
//                selectedOption = "all",
//                filteredItemsListState = ProductsListState(),
//                sortingType = mutableStateOf(1),
//                addToFavourites = {},
//                removeFromFavourites = {}
//            )
//        }
//    }
//}
