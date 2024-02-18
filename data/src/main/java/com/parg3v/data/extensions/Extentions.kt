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
    price = price.toPrice(),
    feedback = feedback.toFeedback(),
    tags = tags,
    available = available,
    description = description,
    info = info.toInfo(),
    ingredients = ingredients
)

fun PriceModel.toPrice(): Price = Price(
    price = price,
    discount = discount,
    priceWithDiscount = priceWithDiscount,
    unit = unit
)


fun FeedbackModel.toFeedback(): Feedback = Feedback(count = count, rating = rating)

fun List<InfoModel>.toInfo(): List<Info> = this.map { Info(title = it.title, value = it.value) }
