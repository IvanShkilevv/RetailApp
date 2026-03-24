package com.example.retailapp.feature.products.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.retailapp.R
import com.example.retailapp.core.base.BaseFragment
import com.example.retailapp.core.utils.makeGone
import com.example.retailapp.core.utils.setVerticalOffsets
import com.example.retailapp.databinding.FragmentProductsBinding
import com.example.retailapp.feature.common.navigation.Screens
import kotlinx.coroutines.flow.collectLatest
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
                    productsAdapter.loadStateFlow.collect { renderUiState(it.refresh) }
                }

                launch {
                    viewModel.productsData.collectLatest { productsAdapter.submitData(it) }
                }
            }
        }
    }

    private fun renderUiState(state: LoadState) {
        when (state) {
            is LoadState.Loading -> renderLoadingUiState()

            is LoadState.NotLoading -> {
                val noData = state.endOfPaginationReached && productsAdapter.itemCount == 0
                if (noData) renderEmptyUiState() else renderDataUiState()
            }

            is LoadState.Error -> renderErrorUiState()
        }
    }

    private fun renderLoadingUiState() {
        binding.animatedProgress.showProgress(true)
    }

    private fun renderEmptyUiState() {
        binding.swipeRefresh.isRefreshing = false
        binding.animatedProgress.showMessage(
            getString(R.string.nothing_found),
            getString(R.string.try_again_later),
        )
    }

    private fun renderDataUiState() {
        binding.swipeRefresh.isRefreshing = false
        binding.animatedProgress.hide()
    }

    private fun renderErrorUiState() {
        binding.swipeRefresh.isRefreshing = false
        binding.animatedProgress.showMessage(
            getString(R.string.something_went_wrong),
            getString(R.string.try_again_later),
        )
    }

    private fun setActionListeners() {
        binding.actionIcon.setOnClickListener {
            navigateTo(Screens.favouriteProducts())
        }

        binding.swipeRefresh.setOnRefreshListener {
            productsAdapter.refresh()
        }

        productsAdapter.itemClickListener = ProductsAdapter.OnItemClickedListener {
            navigateTo(Screens.productDetails(it.id))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
