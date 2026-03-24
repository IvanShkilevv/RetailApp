package com.example.retailapp.feature.common.ui

import com.example.retailapp.core.utils.addCurrencySign
import com.example.retailapp.core.utils.drawPhoto
import com.example.retailapp.databinding.ItemProductBinding
import com.example.retailapp.feature.common.domain.Product

fun ItemProductBinding.fetch(data: Product) {
    with(this) {
        photo.drawPhoto(
            photoUrl = data.photoUrl,
            context = this.root.context,
        )

        price.text = data.price.addCurrencySign()
        name.text = data.name
    }
}