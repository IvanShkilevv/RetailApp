package com.example.retailapp.feature.products.ui.paging

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.retailapp.R
import com.example.retailapp.core.base.BaseViewHolder
import com.example.retailapp.databinding.ItemProductBinding
import com.example.retailapp.feature.common.domain.Product
import com.example.retailapp.feature.common.ui.fetch

class ProductsPagingAdapter :
    PagingDataAdapter<Product, ProductsPagingAdapter.ProductsViewHolder>(DIFF) {

    lateinit var itemClickListener: OnItemClickedListener

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


    fun interface OnItemClickedListener {
        fun onClicked(product: Product)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        return ProductsViewHolder(
            BaseViewHolder.createView(
                parent,
                R.layout.item_product
            )
        )
    }

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ProductsViewHolder(view: View) :
        BaseViewHolder<Product>(view) {

        private val binding = ItemProductBinding.bind(view)

        init {
            view.setOnClickListener {
                data?.let(itemClickListener::onClicked)
            }
        }

        override fun bind(data: Product) {
            super.bind(data)

            binding.fetch(data)
        }
    }
}