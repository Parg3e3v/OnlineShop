package com.parg3v.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.parg3v.data.local.converters.Converter

@Entity(tableName = "product_table")
@TypeConverters(Converter::class)
data class ProductEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String,
    val price: PriceEntity,
    val feedback: FeedbackEntity,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<InfoEntity>,
    val ingredients: String
)

data class PriceEntity(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
)

data class FeedbackEntity(
    val count: Int,
    val rating: Double
)

data class InfoEntity(
    val title: String,
    val value: String
)