package com.parg3v.domain.use_cases

import com.parg3v.domain.model.Product

class SortProductsByPriceToMinUseCase {
    operator fun invoke(products: List<Product>): List<Product> =
        products.sortedByDescending { it.price.priceWithDiscount.toInt() }
}