package com.example.retailapp.feature.common.data

import com.google.gson.annotations.SerializedName

// TODO: add recommendations for README: 1) id as String 2) price as Int
data class ProductDto(
    val id: Int,
    @SerializedName("title")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("price")
    val price: Float?,
    @SerializedName("thumbnail")
    val photoUrl: String?,
)
