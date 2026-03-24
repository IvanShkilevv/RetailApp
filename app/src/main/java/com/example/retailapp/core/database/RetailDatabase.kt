package com.example.retailapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.retailapp.app.Constants.DATABASE_VERSION
import com.example.retailapp.feature.common.data.cache.FavouriteProductEntity
import com.example.retailapp.feature.common.data.cache.FavouriteProductsDAO

@Database(
    entities = [
        FavouriteProductEntity::class,
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class RetailDatabase : RoomDatabase() {
    abstract fun favouriteProductsDao(): FavouriteProductsDAO
}