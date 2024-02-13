package com.parg3v.tz_effective.view.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.parg3v.tz_effective.model.ProductsListState

@Composable
fun CatalogScreen(viewModel: CatalogViewModel = hiltViewModel(), navController: NavController) {

    val itemsList by viewModel.productsState.collectAsStateWithLifecycle()

    CatalogScreenUI(
        controller = navController,
        itemsList = itemsList
    )
}

@Composable
fun CatalogScreenUI(controller: NavController, itemsList: ProductsListState) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Catalog", modifier = Modifier.align(Alignment.Center))
    }
}
