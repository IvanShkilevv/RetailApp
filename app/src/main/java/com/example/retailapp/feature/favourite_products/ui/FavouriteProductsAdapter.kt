package com.example.retailapp.feature.favourite_products.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retailapp.R
import com.example.retailapp.core.base.BaseViewHolder
import com.example.retailapp.databinding.ItemProductBinding
import com.example.retailapp.feature.common.domain.Product
import com.example.retailapp.feature.common.ui.fetch
import java.util.Collections.emptyList

class FavouriteProductsAdapter : RecyclerView.Adapter<FavouriteProductsAdapter.FavouriteProductsViewHolder>() {

    lateinit var itemClickListener: OnItemClickedListener

    fun interface OnItemClickedListener {
        fun onClicked(achievement: Product)
    }

    var items: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteProductsViewHolder {
        return FavouriteProductsViewHolder(
            BaseViewHolder.createView(
                parent,
                R.layout.item_product
            )
        )
    }

    override fun onBindViewHolder(holder: FavouriteProductsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class FavouriteProductsViewHolder(view: View) : BaseViewHolder<Product>(view) {
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