package com.parg3v.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.parg3v.data.local.entity.FeedbackEntity
import com.parg3v.data.local.entity.InfoEntity
import com.parg3v.data.local.entity.PriceEntity

class Converter {

    // Converters for PriceEntity
    @TypeConverter
    fun fromPriceEntity(value: PriceEntity): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toPriceEntity(value: String): PriceEntity {
        val type = object : TypeToken<PriceEntity>() {}.type
        return Gson().fromJson(value, type)
    }

    // Converters for FeedbackEntity
    @TypeConverter
    fun fromFeedbackEntity(value: FeedbackEntity): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toFeedbackEntity(value: String): FeedbackEntity {
        val type = object : TypeToken<FeedbackEntity>() {}.type
        return Gson().fromJson(value, type)
    }

    // Converters for InfoEntity
    @TypeConverter
    fun fromInfoEntityList(value: List<InfoEntity>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toInfoEntityList(value: String): List<InfoEntity> {
        val type = object : TypeToken<List<InfoEntity>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }
}