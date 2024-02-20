package com.parg3v.domain.use_cases

import com.parg3v.domain.model.Product

class ContainsTagUseCase {
    operator fun invoke(products: List<Product>, tag: String): List<Product> {
        return products.filter { it.tags.contains(tag) }
    }

}