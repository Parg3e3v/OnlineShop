package com.parg3v.domain.use_cases

import com.parg3v.domain.model.Product

class SortProductsByPopularityUseCase {
    operator fun invoke(products: List<Product>): List<Product> =
        products.sortedByDescending { it.feedback.rating }
}