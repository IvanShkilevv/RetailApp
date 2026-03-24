package com.example.retailapp.feature.products.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.retailapp.feature.common.domain.ProductsRepository
import com.example.retailapp.core.base.BaseViewModel
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    productsRepository: ProductsRepository
) : BaseViewModel() {

    val productsData =
        productsRepository
            .getProductsPager()
            .flow
            .cachedIn(viewModelScope)

}