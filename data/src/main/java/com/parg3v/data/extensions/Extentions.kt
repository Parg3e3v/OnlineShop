package com.parg3v.data.extensions

import com.parg3v.data.model.FeedbackModel
import com.parg3v.data.model.InfoModel
import com.parg3v.data.model.PriceModel
import com.parg3v.data.model.ProductModel
import com.parg3v.domain.model.Feedback
import com.parg3v.domain.model.Info
import com.parg3v.domain.model.Price
import com.parg3v.domain.model.Product

fun ProductModel.toProduct(): Product = Product(
    id = id,
    title = title,
    subtitle = subtitle,
    price = priceModel.toPrice(),
    feedback = feedbackModel.toFeedback(),
    tags = tags,
    available = available,
    description = description,
    info = infoModel.toInfo(),
    ingredients = ingredients
)

fun PriceModel?.toPrice(): Price? =
    this?.let {
        return Price(
            price = price,
            discount = discount,
            priceWithDiscount = priceWithDiscount,
            unit = unit
        )
    }


fun FeedbackModel?.toFeedback(): Feedback? =
    this?.let {
        Feedback(count = count, rating = rating)
    }

fun List<InfoModel>?.toInfo(): List<Info>? =
    this?.let { list -> list.map { Info(title = it.title, value = it.value) } }
