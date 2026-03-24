package com.example.retailapp.feature.common.data.cache

import com.example.retailapp.feature.common.domain.Product
import java.time.Instant

fun Product.toEntity(): FavouriteProductEntity {
    return FavouriteProductEntity().apply {
        id = this@toEntity.id
        name = this@toEntity.name
        price = this@toEntity.price
        photoUrl = this@toEntity.photoUrl
        createDate = Instant.now().toEpochMilli()
    }
}

fun FavouriteProductEntity.toDomain(
    description: String = ""
): Product {
    return Product(
        id = id,
        name = name.orEmpty(),
        description = description,
        price = price,
        photoUrl = photoUrl.orEmpty(),
        isFavourite = true
    )
}