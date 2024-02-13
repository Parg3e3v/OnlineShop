package com.parg3v.data.remote

import com.parg3v.data.config.NetworkConfig
import com.parg3v.data.model.ProductListResponse
import retrofit2.http.GET

interface Api {

    @GET(NetworkConfig.PATH_PRODUCTS)
    suspend fun getAllProducts(): ProductListResponse
}