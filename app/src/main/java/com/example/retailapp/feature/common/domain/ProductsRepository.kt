package com.example.retailapp.feature.common.domain

import com.example.retailapp.app.Constants.PAGE_SIZE
import com.example.retailapp.feature.common.data.ProductDto
import com.example.retailapp.feature.common.data.ProductsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsService: ProductsService
) {

    suspend fun getProductsPage(skip: Int): List<Product> =
        withContext(Dispatchers.IO) {
            productsService
                .getProducts(skip = skip, limit = PAGE_SIZE)
                .map(ProductDto::toDomain)
        }


    suspend fun getProductDetails(id: Int): Product =
        withContext(Dispatchers.IO) {
            productsService
                .getProductDetails(id = id)
                .toDomain()
        }

//    suspend fun toggleFavourite(product: Product) {
//        if (product.isFavourite) clearFavourite(product)
//        else cacheFavourite(product)
//    }
//
//    private suspend fun cacheFavourite(product: Product) {
//        withContext(Dispatchers.IO) {
//            // TODO: impl
//        }
//    }
//
//    private suspend fun clearFavourite(product: Product) {
//        withContext(Dispatchers.IO) {
//            // TODO: impl
//        }
//    }
//
//    suspend fun getFavouriteProducts(): List<Product> {
//        withContext(Dispatchers.IO) {
//            // TODO: impl
//        }
//    }
//
//    suspend fun isFavourite(productId: Int): Boolean {
//        withContext(Dispatchers.IO) {
//            // TODO: impl
//        }
//    }

}