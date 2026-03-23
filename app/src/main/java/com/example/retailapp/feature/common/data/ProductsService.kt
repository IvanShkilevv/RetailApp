package com.example.retailapp.feature.common.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsService {

    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductDetails(
        @Path("id") id: Int,
    ): ProductDto

}