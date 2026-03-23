package com.example.retailapp.feature.products.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.retailapp.core.base.BaseFragment
import com.example.retailapp.feature.common.data.ProductDto
import com.example.retailapp.feature.common.navigation.Screens

// TODO: 1) model, api, domain
// TODO: 2) init Room
// TODO: 3) db, favourites
// TODO: 4) products
class ProductsFragment : BaseFragment() {

    private lateinit var viewModel: ProductsViewModel

    companion object {
        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[ProductsViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                UsersScreen(viewModel = viewModel, ::navigateToProductDetails)
            }
        }
    }

    private fun navigateToProductDetails(product: ProductDto) {
        navigateTo(Screens.productDetails(product.login))
    }
}

