package com.parg3v.data.mappers

import com.parg3v.data.local.entity.FeedbackEntity
import com.parg3v.data.local.entity.InfoEntity
import com.parg3v.data.local.entity.PriceEntity
import com.parg3v.data.local.entity.ProductEntity
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

fun ProductEntity.toProduct(): Product = Product(
    id = id,
    title = title,
    subtitle = subtitle,
    price = price.toPriceFromEntity(),
    feedback = feedback.toFeedbackFromEntity(),
    tags = tags,
    available = available,
    description = description,
    info = info.toInfoFromEntity(),
    ingredients = ingredients
)

fun PriceEntity.toPriceFromEntity(): Price = Price(
    price = price,
    discount = discount,
    priceWithDiscount = priceWithDiscount,
    unit = unit
)


fun FeedbackEntity.toFeedbackFromEntity(): Feedback = Feedback(count = count, rating = rating)

fun List<InfoEntity>.toInfoFromEntity(): List<Info> = this.map { Info(title = it.title, value = it.value) }

fun Product.toProductEntity(): ProductEntity = ProductEntity(
    id = id,
    title = title,
    subtitle = subtitle,
    price = price.toPriceEntity(),
    feedback = feedback.toFeedbackEntity(),
    tags = tags,
    available = available,
    description = description,
    info = info.toInfoEntityList(),
    ingredients = ingredients
)

fun Price.toPriceEntity(): PriceEntity = PriceEntity(
    price = price,
    discount = discount,
    priceWithDiscount = priceWithDiscount,
    unit = unit
)

fun Feedback.toFeedbackEntity(): FeedbackEntity = FeedbackEntity(count = count, rating = rating)

fun List<Info>.toInfoEntityList(): List<InfoEntity> = this.map { InfoEntity(title = it.title, value = it.value) }

