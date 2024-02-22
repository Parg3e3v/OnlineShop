package com.parg3v.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.parg3v.data.local.entity.ProductEntity

@Dao
interface ProductsDao {
    @Query("SELECT * FROM product_table")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT COUNT(*) FROM product_table WHERE id = :productId")
    suspend fun isFavorite(productId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)
}