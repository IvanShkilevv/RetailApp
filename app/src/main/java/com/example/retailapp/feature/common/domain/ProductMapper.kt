package com.example.retailapp.feature.common.domain

import com.example.retailapp.feature.common.data.ProductDto

fun ProductDto.toDomain() : Product =
    Product(
        id = id.toString(),
        name = name.orEmpty(),
        description = description.orEmpty(),
        price = price ?: 0F,
        photoUrl = photoUrl.orEmpty(),
        // Set to false, can be fetched later.
        // Still, single domain model (Product) is a better approach, then creating duplicate domain model (FavouriteProduct)
        isFavourite = false,
    )