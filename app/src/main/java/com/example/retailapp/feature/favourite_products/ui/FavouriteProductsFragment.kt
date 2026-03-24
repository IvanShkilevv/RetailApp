package com.example.retailapp.feature.favourite_products.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.retailapp.R
import com.example.retailapp.core.base.BaseFragment
import com.example.retailapp.core.utils.makeVisible
import com.example.retailapp.core.utils.setVerticalOffsets
import com.example.retailapp.databinding.FragmentFavouriteProductsBinding
import com.example.retailapp.feature.common.navigation.Screens
import kotlinx.coroutines.launch

class FavouriteProductsFragment : BaseFragment() {

    private lateinit var viewModel: FavouriteProductsViewModel

    private var _binding: FragmentFavouriteProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productsAdapter: FavouriteProductsAdapter

    companion object {
        fun newInstance(): FavouriteProductsFragment {
            return FavouriteProductsFragment()
        }
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentFavouriteProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productsAdapter = FavouriteProductsAdapter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[FavouriteProductsViewModel::class.java]
        setupUI()
        setObservers()
        setActionListeners()

        viewModel.observeFavouriteProducts()
    }

    private fun setupUI() {
        binding.toolbar.title.setText(R.string.favourites_title)
        binding.toolbar.back.makeVisible()

        binding.recycler.apply {
            adapter = productsAdapter

            setVerticalOffsets(
                topMarginInPx = getDimen(R.dimen.default_margin),
                middleMarginInPx = getDimen(R.dimen.default_margin_quarter),
                bottomMarginInPx = getDimen(R.dimen.default_margin)
            )
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.screenState.collect { renderUiState(it) }
                }

                launch {
                    viewModel.favouriteProducts.collect { list -> productsAdapter.items = list }
                }
            }
        }
    }

    private fun renderUiState(screenState: FavouriteProductsScreenState) {
        when (screenState) {
            FavouriteProductsScreenState.EMPTY -> {
                binding.animatedProgress.showMessage(
                    getString(R.string.empty_favourites),
                    getString(R.string.add_favourites),
                )
            }

            FavouriteProductsScreenState.LOADING -> {
                binding.animatedProgress.showProgress(true)
            }

            FavouriteProductsScreenState.DATA -> {
                binding.animatedProgress.hide()
            }

            FavouriteProductsScreenState.ERROR -> {
                binding.animatedProgress.showMessage(
                    getString(R.string.something_went_wrong),
                    getString(R.string.try_again_later),
                )
            }
        }
    }

    private fun setActionListeners() {
        binding.toolbar.back.setOnClickListener {
            back()
        }

        productsAdapter.itemClickListener = FavouriteProductsAdapter.OnItemClickedListener {
            navigateTo(Screens.productDetails(it.id))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}