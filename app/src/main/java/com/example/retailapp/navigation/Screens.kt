package com.example.retailapp.navigation

import com.example.retailapp.ui.user_details.UserDetailsFragment
import com.example.retailapp.ui.users.UsersFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun products() = FragmentScreen {
        UsersFragment.newInstance()
    }

    fun productDetails(name: String?) = FragmentScreen {
        UserDetailsFragment.newInstance(name)
    }

    // TODO: uncomment
//    fun favouriteProducts() = FragmentScreen {
//        FavouriteProductsFragment.newInstance()
//    }

}