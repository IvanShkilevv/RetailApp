package com.example.retailapp.feature.common.domain

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Float,
    val photoUrl: String,
)