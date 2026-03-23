package com.example.retailapp.feature.common.navigation

import com.example.retailapp.feature.products.ui.ProductsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun products() = FragmentScreen {
        ProductsFragment.newInstance()
    }


//        // TODO: uncomment
//    fun productDetails(name: String?) = FragmentScreen {
//        UserDetailsFragment.newInstance(name)
//    }

    // TODO: uncomment
//    fun favouriteProducts() = FragmentScreen {
//        FavouriteProductsFragment.newInstance()
//    }

}