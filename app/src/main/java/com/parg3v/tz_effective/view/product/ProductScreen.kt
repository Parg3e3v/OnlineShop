package com.parg3v.tz_effective.view.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.parg3v.tz_effective.model.ProductState

@Composable
fun ProductScreen(productState: ProductState) {
    productState.data?.run {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = productState.data.title, modifier = Modifier.align(Alignment.Center))
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Error: ${productState.error}",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
