package com.example.retailapp.feature.products.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retailapp.R
import com.example.retailapp.core.base.BaseViewHolder
import com.example.retailapp.core.utils.addCurrencySign
import com.example.retailapp.core.utils.drawPhoto
import com.example.retailapp.databinding.ItemProductBinding
import com.example.retailapp.feature.common.domain.Product
import java.util.Collections.emptyList

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    lateinit var itemClickListener: OnItemClickedListener

    fun interface OnItemClickedListener {
        fun onClicked(achievement: Product)
    }

    var items: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            BaseViewHolder.createView(
                parent,
                R.layout.item_product
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int) = items.getOrNull(position)

    inner class ProductsViewHolder(view: View) : BaseViewHolder<Product>(view) {
        private val binding = ItemProductBinding.bind(view)

        init {
            view.setOnClickListener {
                data?.let(itemClickListener::onClicked)
            }
        }

        override fun bind(data: Product) {
            super.bind(data)

            with(binding) {
                photo.drawPhoto(
                    photoUrl = data.photoUrl,
                    context = context,
                )

                price.text = data.price.addCurrencySign()
                name.text = data.name
            }
        }
    }

}