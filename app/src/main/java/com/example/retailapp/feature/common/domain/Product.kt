package com.example.retailapp.feature.common.domain

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Float,
    val photoUrl: String,
    val isFavourite: Boolean,
)