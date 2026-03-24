package com.example.retailapp.feature.products.ui

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
import com.example.retailapp.core.utils.makeGone
import com.example.retailapp.core.utils.setVerticalOffsets
import com.example.retailapp.databinding.FragmentProductsBinding
import com.example.retailapp.feature.common.navigation.Screens
import kotlinx.coroutines.launch

class ProductsFragment : BaseFragment() {

    private lateinit var viewModel: ProductsViewModel

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productsAdapter: ProductsAdapter

    companion object {
        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productsAdapter = ProductsAdapter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProductsViewModel::class.java]
        setupUI()
        setObservers()
        setActionListeners()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadProducts()
    }

    private fun setupUI() {
        binding.toolbar.title.setText(R.string.products_title)
        binding.toolbar.back.makeGone()

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
                    viewModel.productsData.collect { list -> productsAdapter.items = list }
                }
            }
        }
    }

    private fun renderUiState(screenState: ProductsScreenState) {
        when (screenState) {
            ProductsScreenState.EMPTY -> {
                binding.swipeRefresh.isRefreshing = false
                binding.animatedProgress.showMessage(
                    getString(R.string.nothing_found),
                    getString(R.string.try_again_later),
                )
            }

            ProductsScreenState.LOADING -> {
                binding.animatedProgress.showProgress(true)
            }

            ProductsScreenState.DATA -> {
                binding.animatedProgress.hide()
                binding.swipeRefresh.isRefreshing = false
            }

            ProductsScreenState.ERROR -> {
                binding.swipeRefresh.isRefreshing = false
                binding.animatedProgress.showMessage(
                    getString(R.string.something_went_wrong),
                    getString(R.string.try_again_later),
                )
            }
        }
    }

    private fun setActionListeners() {
        productsAdapter.itemClickListener = ProductsAdapter.OnItemClickedListener {
            navigateTo(Screens.productDetails(it.id))
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.actionIcon.setOnClickListener {
            // TODO: navigate to Favourites
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
