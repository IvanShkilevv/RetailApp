package com.example.retailapp.feature.product_details.ui

sealed class ProductDetailsEvent {
    data class ShowError(val message: String) : ProductDetailsEvent()
}