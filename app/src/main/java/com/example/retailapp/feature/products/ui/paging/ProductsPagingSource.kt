package com.example.retailapp.feature.products.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retailapp.feature.common.data.ProductDto
import com.example.retailapp.feature.common.data.ProductsService
import com.example.retailapp.feature.common.domain.Product
import com.example.retailapp.feature.common.domain.toDomain

class ProductsPagingSource(
    private val productsService: ProductsService
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val skip = params.key ?: 0

        return try {
            val response = productsService.getProducts(
                skip = skip,
                limit = params.loadSize
            )

            val items = response.products.map(ProductDto::toDomain)

            LoadResult.Page(
                data = items,
                prevKey = if (skip == 0) null else skip - params.loadSize,
                nextKey = if (items.isEmpty()) null else skip + items.size
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }
}