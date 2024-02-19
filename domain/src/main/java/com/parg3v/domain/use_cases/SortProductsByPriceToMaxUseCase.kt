package com.parg3v.domain.use_cases

import com.parg3v.domain.model.Product

class SortProductsByPriceToMaxUseCase {
    operator fun invoke(products: List<Product>): List<Product> =
        products.sortedBy { it.price.priceWithDiscount.toInt() }
}