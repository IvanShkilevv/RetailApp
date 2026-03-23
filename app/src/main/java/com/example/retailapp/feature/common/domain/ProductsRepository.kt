package com.example.retailapp.feature.common.domain

import com.example.retailapp.app.Constants.PAGE_SIZE
import com.example.retailapp.feature.common.data.ProductDto
import com.example.retailapp.feature.common.data.ProductsService
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsService: ProductsService
) {

    suspend fun getProductsPage(skip: Int): List<Product> =
        productsService
            .getProducts(skip = skip, limit = PAGE_SIZE)
            .map(ProductDto::toDomain)

    suspend fun getProductDetails(id: Int): Product =
        productsService
            .getProductDetails(id = id)
            .toDomain()

}