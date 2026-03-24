package com.example.retailapp.feature.products.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.retailapp.app.Constants.API_TEST_DELAY_MILLIS
import com.example.retailapp.feature.common.domain.ProductsRepository
import com.example.retailapp.core.base.BaseViewModel
import com.example.retailapp.core.utils.runSuspendCatching
import com.example.retailapp.feature.common.domain.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    private val _productsData = MutableStateFlow<List<Product>>(emptyList())
    val productsData: StateFlow<List<Product>> = _productsData

    private val _screenState = MutableStateFlow(ProductsScreenState.EMPTY)
    val screenState: StateFlow<ProductsScreenState> = _screenState

    // TODO: remove
    private var skip = 0

    fun loadProducts() {
        viewModelScope.launch {
            _screenState.value = ProductsScreenState.LOADING
            /* Delay for a better testing of SwipeRefresh and Progress.
            In a real project better to use if(BuildConfig.DEBUG = true) {delay()}*/
            delay(API_TEST_DELAY_MILLIS)

            runSuspendCatching {
                productsRepository.getProductsPage(skip)
            }.onSuccess { page ->

                if (page.isEmpty() && _productsData.value.isEmpty()) {
                    _screenState.value = ProductsScreenState.EMPTY
                } else {
                    _productsData.value = _productsData.value + page
                    skip += page.size

                    _screenState.value = ProductsScreenState.DATA
                }

            }.onFailure { error ->
                Log.e("ProductsScreen", "loadProducts:", error)
                _screenState.value = ProductsScreenState.ERROR
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            skip = 0
            _productsData.value = emptyList()
            _screenState.value = ProductsScreenState.LOADING

            /* Delay for a better testing of SwipeRefresh and Progress.
            In a real project better to use if(BuildConfig.DEBUG = true) {delay()}*/
            delay(API_TEST_DELAY_MILLIS)

            runSuspendCatching {
                productsRepository.getProductsPage(skip)
            }.onSuccess { page ->
                if (page.isEmpty()) {
                    _screenState.value = ProductsScreenState.EMPTY
                } else {
                    _productsData.value = page
                    skip = page.size

                    _screenState.value = ProductsScreenState.DATA
                }

            }.onFailure { error ->
                Log.e("ProductsScreen", "loadProducts:", error)
                _screenState.value = ProductsScreenState.ERROR
            }
        }
    }
}