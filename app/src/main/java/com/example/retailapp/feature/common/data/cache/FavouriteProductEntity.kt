package com.example.retailapp.feature.common.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
class FavouriteProductEntity {
    @PrimaryKey
    lateinit var id: String
    var name: String? = null
    var price: Float = 0F
    var photoUrl: String? = null

    var createDate: Long = 0
}