package com.example.retailapp.feature.common.domain

import com.example.retailapp.app.Constants.PAGE_SIZE
import com.example.retailapp.feature.common.data.ProductDto
import com.example.retailapp.feature.common.data.ProductsService
import com.example.retailapp.feature.common.data.cache.FavouriteProductEntity
import com.example.retailapp.feature.common.data.cache.FavouriteProductsDAO
import com.example.retailapp.feature.common.data.cache.toDomain
import com.example.retailapp.feature.common.data.cache.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsService: ProductsService,
    private val favouriteProductsDAO: FavouriteProductsDAO
) {

    suspend fun getProductsPage(skip: Int): List<Product> =
            productsService
                .getProducts(skip = skip, limit = PAGE_SIZE)
                .products
                .map(ProductDto::toDomain)


    suspend fun getProductDetails(id: Int): Product {
        val product = productsService.getProductDetails(id = id).toDomain()
        val isFavourite = favouriteProductsDAO.isFavourite(product.id)

        return product.copy(isFavourite = isFavourite)
    }

    fun getFavouriteProducts(): Flow<List<Product>> =
        favouriteProductsDAO.getAll().map {
            list -> list.map(FavouriteProductEntity::toDomain)
        }

    suspend fun toggleFavourite(product: Product) {
        val favourite = favouriteProductsDAO.isFavourite(product.id)

        if (favourite) {
            favouriteProductsDAO.deleteItem(id = product.id)
        }
        else {
            favouriteProductsDAO.insertItem(product.toEntity())
        }
    }
}