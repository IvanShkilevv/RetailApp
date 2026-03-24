package com.example.retailapp.feature.favourite_products.ui

import androidx.lifecycle.viewModelScope
import com.example.retailapp.core.base.BaseViewModel
import com.example.retailapp.feature.common.domain.Product
import com.example.retailapp.feature.common.domain.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    private val _screenState = MutableStateFlow(FavouriteProductsScreenState.EMPTY)
    val screenState: StateFlow<FavouriteProductsScreenState> = _screenState

    private val _favouriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favouriteProducts: StateFlow<List<Product>> = _favouriteProducts

    fun observeFavouriteProducts() {
        _screenState.value = FavouriteProductsScreenState.LOADING

        viewModelScope.launch {
            productsRepository.getFavouriteProducts()
                .collect { products ->
                    _favouriteProducts.value = products
                    _screenState.value = FavouriteProductsScreenState.DATA
                }
        }
    }
}