package com.parg3v.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parg3v.data.local.dao.ProductsDao
import com.parg3v.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ProductsDatabase: RoomDatabase() {
    abstract val productsDao: ProductsDao
}