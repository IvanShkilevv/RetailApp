package com.example.retailapp.feature.product_details.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.retailapp.app.Constants.API_TEST_DELAY_MILLIS
import com.example.retailapp.core.base.BaseViewModel
import com.example.retailapp.core.utils.runSuspendCatching
import com.example.retailapp.feature.common.domain.Product
import com.example.retailapp.feature.common.domain.ProductsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    lateinit var productId: String

    private val _productData = MutableStateFlow(createEmptyProduct())
    val productData: StateFlow<Product> = _productData

    private val _events = MutableSharedFlow<ProductDetailsEvent>()
    val events = _events.asSharedFlow()

    private val _screenState = MutableStateFlow(ProductDetailsScreenState.EMPTY)
    val screenState: StateFlow<ProductDetailsScreenState> = _screenState

    companion object {
        private fun createEmptyProduct(): Product =
            Product(
                id = "",
                name = "",
                description = "",
                price = 0f,
                photoUrl = "",
                isFavourite = false
            )
    }

    fun loadProductDetails() {
        viewModelScope.launch {
            _screenState.value = ProductDetailsScreenState.LOADING
            /* Delay for a better testing of SwipeRefresh and Progress.
            In a real project better to use if(BuildConfig.DEBUG = true) {delay()}*/
            delay(API_TEST_DELAY_MILLIS)

            runSuspendCatching {
                productsRepository.getProductDetails(productId.toInt())
            }.onSuccess { data ->
                _productData.value = data
                _screenState.value = ProductDetailsScreenState.DATA
            }.onFailure { error ->
                Log.e("ProductDetails", "loadProduct:", error)
                _screenState.value = ProductDetailsScreenState.ERROR
            }
        }
    }

    fun toggleFavourite() {
        val data = _productData.value
        val isFavouriteInitial = _productData.value.isFavourite

        viewModelScope.launch {
            runSuspendCatching {
                productsRepository.toggleFavourite(data)
            }.onSuccess {
                _productData.value = _productData.value.copy(
                    isFavourite = !isFavouriteInitial
                )
            }.onFailure { error ->
                Log.e("ProductDetails", "toggleFavourite:", error)

                _events.emit(ProductDetailsEvent.ShowError(error.message ?: "Unknown error"))
            }
        }
    }
}
