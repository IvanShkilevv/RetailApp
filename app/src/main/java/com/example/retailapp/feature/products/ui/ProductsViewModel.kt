package com.example.retailapp.feature.products.ui

import com.example.retailapp.feature.common.domain.ProductsRepository
import com.example.retailapp.core.base.BaseViewModel
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {
    // TODO:  delay  for a better testing PullToRefresh and ProgressIndicator
    //            delay(1000)


}