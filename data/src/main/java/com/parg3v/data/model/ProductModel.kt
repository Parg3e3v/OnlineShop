package com.parg3v.data.model

data class ProductModel (
    val id: String,
    val title: String,
    val subtitle: String,
    val priceModel: PriceModel,
    val feedbackModel: FeedbackModel,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val infoModel: List<InfoModel>,
    val ingredients: String
)

data class PriceModel(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
)

data class FeedbackModel(
    val count: Int,
    val rating: Double
)

data class InfoModel(
    val title: String,
    val value: String
)
