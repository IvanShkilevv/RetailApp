package com.example.retailapp.feature.product_details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.retailapp.R
import com.example.retailapp.core.base.BaseFragment
import com.example.retailapp.core.utils.addCurrencySign
import com.example.retailapp.core.utils.drawPhoto
import com.example.retailapp.core.utils.makeVisible
import com.example.retailapp.databinding.FragmentProductDetailsBinding
import com.example.retailapp.feature.common.domain.Product
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ProductDetailsFragment : BaseFragment() {
    private lateinit var viewModel: ProductDetailsViewModel

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productId: String

    companion object {
        private const val PRODUCT_ID_KEY = "PRODUCT_ID"

        fun newInstance(productId: String): ProductDetailsFragment {
            val fragment = ProductDetailsFragment()
            val args = Bundle().apply {
                putString(PRODUCT_ID_KEY, productId)
            }

            fragment.arguments = args
            return fragment
        }
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productId = arguments?.getString(PRODUCT_ID_KEY) ?: ""
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProductDetailsViewModel::class.java]
        viewModel.productId = productId

        setupUI()
        setObservers()
        setActionListeners()
        viewModel.loadProductDetails()
    }

    private fun setupUI() {
        binding.toolbar.title.setText(R.string.product_details)
        binding.toolbar.back.makeVisible()
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.screenState.collect(::renderUiState) }
                launch { viewModel.productData.collect(::fetchData) }
                launch { viewModel.events.collect(::handleEvents) }
            }
        }
    }

    private fun renderUiState(screenState: ProductDetailsScreenState) {
        when (screenState) {
            ProductDetailsScreenState.EMPTY -> {
                binding.swipeRefresh.isRefreshing = false
                binding.animatedProgress.showMessage(
                    getString(R.string.nothing_found),
                    getString(R.string.try_again_later),
                )
            }

            ProductDetailsScreenState.LOADING -> {
                binding.animatedProgress.showProgress(true)
            }

            ProductDetailsScreenState.DATA -> {
                binding.animatedProgress.hide()
                binding.favouriteIcon.makeVisible()
                binding.swipeRefresh.isRefreshing = false
            }

            ProductDetailsScreenState.ERROR -> {
                binding.swipeRefresh.isRefreshing = false
                binding.animatedProgress.showMessage(
                    getString(R.string.something_went_wrong),
                    getString(R.string.try_again_later),
                )
            }
        }
    }

    private fun fetchData(data: Product) {
        val iconRes = if (data.isFavourite) {
            getDrawable(R.drawable.ic_favorite_on)
        } else {
            getDrawable(R.drawable.ic_favorite_off)
        }

        with(binding) {
            photo.drawPhoto(
                photoUrl = data.photoUrl,
                context = context,
            )

            price.text = data.price.addCurrencySign()
            name.text = data.name
            descrption.text = data.description

            favouriteIcon.setImageDrawable(iconRes)
        }
    }

    private fun handleEvents(event: ProductDetailsEvent) {
        when (event) {
            is ProductDetailsEvent.ShowError -> {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setActionListeners() {
        binding.toolbar.back.setOnClickListener {
            back()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadProductDetails()
        }

        binding.favouriteIcon.setOnClickListener {
            viewModel.toggleFavourite()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}