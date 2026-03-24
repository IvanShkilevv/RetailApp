package com.example.retailapp.feature.common.navigation

import com.example.retailapp.feature.product_details.ui.ProductDetailsFragment
import com.example.retailapp.feature.products.ui.ProductsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun products() = FragmentScreen {
        ProductsFragment.newInstance()
    }


    fun productDetails(id: String) = FragmentScreen {
        ProductDetailsFragment.newInstance(id)
    }

    // TODO: uncomment
//    fun favouriteProducts() = FragmentScreen {
//        FavouriteProductsFragment.newInstance()
//    }

}