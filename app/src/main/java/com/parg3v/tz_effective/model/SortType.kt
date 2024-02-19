package com.parg3v.tz_effective.model

import com.parg3v.tz_effective.R

sealed class SortType(val stringResourceId: Int) {
    object POPULARITY : SortType(R.string.by_popularity)
    object PRICE_TO_MIN: SortType (R.string.by_price_to_min)
    object PRICE_TO_MAX: SortType (R.string.by_price_to_max)
}